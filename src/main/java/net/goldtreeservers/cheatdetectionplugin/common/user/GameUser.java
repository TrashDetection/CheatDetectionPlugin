package net.goldtreeservers.cheatdetectionplugin.common.user;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import net.goldtreeservers.cheatdetectionplugin.bungee.net.CancelPacketTransmitException;
import net.goldtreeservers.cheatdetectionplugin.bungee.net.RawPacket;
import net.goldtreeservers.cheatdetectionplugin.common.user.data.ChunkSection;
import net.goldtreeservers.cheatdetectionplugin.common.user.data.GameUserSplit;
import net.goldtreeservers.cheatdetectionplugin.common.user.data.ProtocolData;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ByteBufUtils;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ChunkedByteArray;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ChunkedByteArrayUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.chat.ComponentSerializer;
import net.md_5.bungee.protocol.packet.Kick;

public class GameUser
{
	private static final Kick LUNAR_KICK_MESSAGE = GameUser.getLunarKickMessage();
	
	@Getter private final GameUserManager manager;
	
	@Getter private final User user;
	@Getter private final int sessionId;
	@Getter private final int userId;
	
	private final ProtocolData protocol;
	
	@Getter private long startedOn;
	
	private ChunkedByteArray incomingBytes;
	private ChunkedByteArray outgoingBytes;
	
	private boolean joinPacketSent;
	
	private int waitingConfirmations;
	private int waitingConfirmationsNext;
	
	private GameUserSplit split;
	private int splitVersion;
	
	public GameUser(GameUserManager manager, User user, int sessionId, int userId)
	{
		this.manager = manager;
		
		this.user = user;
		this.sessionId = sessionId;
		this.userId = userId;
		
		this.protocol = ProtocolData.getProtocolData(user.getProtocolVersion());
		
		this.startedOn = System.nanoTime();
		
		this.incomingBytes = new ChunkedByteArray();
		this.outgoingBytes = new ChunkedByteArray();
	}
	
	public void sendForge(ChannelHandlerContext ctx)
	{
		ctx.write(new RawPacket(this.protocol.getForgeChannelsClientbound()));
		
		if (false && this.protocol.isHasForgeSupport())
		{
			ctx.write(new RawPacket(this.protocol.getForgeHelloClientbound()));
			ctx.write(new RawPacket(this.protocol.getForgeModListClientbound()));
			ctx.write(new RawPacket(this.protocol.getForgeRegistryPotionsClientbound()));
			ctx.write(new RawPacket(this.protocol.getForgeRegistryVillagerProfessionsClientbound()));
			ctx.write(new RawPacket(this.protocol.getForgeRegistryBlocksClientbound()));
			ctx.write(new RawPacket(this.protocol.getForgeRegistryItemsClientbound()));
			ctx.write(new RawPacket(this.protocol.getForgeResetClientbound()));
		}
		
		ctx.flush();
	}
	
	public void sendChannels(ChannelHandlerContext ctx)
	{
		ctx.write(new RawPacket(this.protocol.getChannelsClientbound()));
		ctx.flush();
	}
	
	public void readIncomingBytes(ChannelHandlerContext ctx, ByteBuf msg)
	{
		int readerIndex = msg.readerIndex();
		
		int packetId = ByteBufUtils.readVarInt(msg);
		if (this.protocol.shouldIgnoreServerbound(packetId))
		{
			if (packetId == this.protocol.getPluginMessageServerbound())
			{
				String channel = ByteBufUtils.readString(msg);
				if (channel.equals("td:debug"))
				{
					msg.readerIndex(readerIndex);
					
					this.addIncomingBytes(msg);
					
					throw CancelPacketTransmitException.INSTANCE; //Don't let this flow thru
				}
				else if (channel.equals("minecraft:brand") || channel.equals("MC|Brand"))
				{
					//Minecraft client name, Log it
				}
				else if (channel.equals("minecraft:register") || channel.equals("REGISTER"))
				{
					//Plugin message channel register, Log it
					byte[] bytes = new byte[msg.readableBytes()];
					msg.readBytes(bytes);
					
					String registerChannels = new String(bytes, Charsets.UTF_8);
					for(String registerChannel : registerChannels.split("\0"))
					{
						if (registerChannel.equals("Lunar-Client"))
						{
							ctx.channel().writeAndFlush(GameUser.LUNAR_KICK_MESSAGE).addListener(ChannelFutureListener.CLOSE);
							break;
						}
					}
				}
				else if (channel.equals("FML|HS"))
				{
					//Forge handshake, Log it
				}
				else if (channel.equals("LMC") || channel.equals("LABYMOD"))
				{
					//Labymod, Log it
				}
				else if (channel.equals("Lunar-Client"))
				{
					//Lunar, Log it
				}
				else
				{
					return;
				}

				msg.readerIndex(readerIndex);
				
				this.addIncomingBytes(msg);
			}
			
			return;
		}

		int dataReaderIndex = msg.readerIndex();
		
		msg.readerIndex(readerIndex); //Go to the start with packet id
		
		this.addIncomingBytes(msg);
		
		//Manipulate
		if (this.protocol.getConfirmTransactionServerbound() == packetId)
		{
			msg.readerIndex(dataReaderIndex); //Don't re-read packet id, skip to data
			
			int windowId = msg.readUnsignedByte();
			if(windowId == 0) //Inventory
			{
				short action = msg.readShort();
				if (action == (short)-3)
				{
					if (--this.waitingConfirmations == 0 && this.split != null)
					{
						this.split.done(System.nanoTime(), this.incomingBytes);
						
						this.manager.addSplit(this.split);

						this.startedOn = System.nanoTime();

						this.incomingBytes = new ChunkedByteArray();	
						
						this.waitingConfirmations = this.waitingConfirmationsNext;
						this.waitingConfirmationsNext = 0;
						
						this.split = null;
					}
					
					throw CancelPacketTransmitException.INSTANCE;
				}
				else if (action == (short)-5)
				{
					throw CancelPacketTransmitException.INSTANCE;
				}
			}
		}
		
		//3Mb is critical size and we should force split it
		if (this.incomingBytes.size64() + this.outgoingBytes.size64() > 1024 * 1024 * 3)
		{
			this.forceSplit();
		}
	}
	
	public void readOutgoingBytes(ChannelHandlerContext ctx, ByteBuf msg)
	{
		int readerIndex = msg.readerIndex();
		
		int packetId = ByteBufUtils.readVarInt(msg);
		if (this.protocol.shouldIgnoreClientbound(packetId))
		{
			if (packetId == this.protocol.getPluginMessageClientbound())
			{
				String channel = ByteBufUtils.readString(msg);
				if (channel.equals("minecraft:brand") || channel.equals("MC|Brand"))
				{
					//Minecraft client name, Log it
				}
				else if (channel.equals("minecraft:register") || channel.equals("REGISTER"))
				{
					//Plugin message channel register, Log it
				}
				else if (channel.equals("FML|HS"))
				{
					//Forge handshake, Log it
				}
				else if (channel.equals("FORGE"))
				{
					//Runtime codec?
				}
				else if (channel.equals("FML"))
				{
					//Forge custom packets
				}
				else if (channel.equals("Lunar-Client"))
				{
					//Lunar, Log it
				}
				else
				{
					return;
				}
				
				msg.readerIndex(readerIndex);
				
				this.addOutgoingBytes(msg);
			}
			
			return;
		}
		
		if (!this.joinPacketSent)
		{
			msg.readerIndex(readerIndex);
			
			if (this.protocol.getJoinPacketClientbound() == packetId)
			{
				this.joinPacketSent = true;

				this.sendForge(ctx);
				
				try
				{
					this.confirmPacketWithoutPrev(ctx, msg);
				}
				finally //Confirm packet throws exception so use finally block to execute left over code
				{
					this.sendChannels(ctx);
				}
			}
			else
			{
				this.addOutgoingBytes(msg);
			}
		}
		else
		{
			if (this.wantConfirmation(packetId, msg)) //Want confirmation might read data left on the packet
			{
				msg.readerIndex(readerIndex);
				
				this.confirmPacket(ctx, msg);
			}
			else
			{
				msg.readerIndex(readerIndex);
				
				if (this.split == null)
				{
					long now = System.nanoTime();
					if (now - this.startedOn >= TimeUnit.SECONDS.toNanos(15) || this.incomingBytes.size64() + this.outgoingBytes.size64() > 1024 * 1024) //1MB
					{
						try
						{
							this.confirmPacket(ctx, msg);
						}
						finally //Confirm packet throws exception so use finally block to execute left over code to start split
						{
							this.split = new GameUserSplit(this, this.splitVersion++, this.startedOn, this.outgoingBytes);
							
							this.outgoingBytes = new ChunkedByteArray();
						}

						throw new RuntimeException("What did just happen?");
					}
				}

				this.addOutgoingBytes(msg);
			}
		}

		//3Mb is critical size and we should force split it
		if (this.incomingBytes.size64() + this.outgoingBytes.size64() > 1024 * 1024 * 3)
		{
			this.forceSplit();
		}
	}
	
	private void forceSplit()
	{
		boolean createNew;
		if (this.split == null) //No split? New one!
		{
			this.split = new GameUserSplit(this, this.splitVersion++, this.startedOn, this.outgoingBytes);
			
			createNew = false;
		}
		else
		{
			createNew = true;
		}
		
		long time = System.nanoTime();
		
		this.split.setIncomplete(true);
		this.split.done(time, this.incomingBytes);

		this.manager.addSplit(this.split);

		this.startedOn = time;

		this.incomingBytes = new ChunkedByteArray();	
		this.outgoingBytes = new ChunkedByteArray();
		
		if (createNew) //Start new split, if we were already trying to write one
		{
			this.split = new GameUserSplit(this, this.splitVersion++, this.startedOn, this.outgoingBytes);
		}
		else
		{
			this.split = null;
		}
	}
	
	private void confirmPacket(ChannelHandlerContext ctx, ByteBuf msg)
	{
		ctx.write(new RawPacket(this.protocol.getConfirmTransactionPrevClientbound()));

		this.confirmPacketWithoutPrev(ctx, msg);
	}
	
	private void confirmPacketWithoutPrev(ChannelHandlerContext ctx, ByteBuf msg)
	{
		//If split is null add to the current confirmation counts
		//If not, then the next confirmations count are added back to
		//Confirmation counts after split has done its job
		if (this.split == null)
		{
			this.waitingConfirmations++;
		}
		else
		{
			this.waitingConfirmationsNext++;
		}
		
		ctx.write(new RawPacket(msg)); //First send the actual packet
		ctx.write(new RawPacket(this.protocol.getConfirmTransactionClientbound())); //Then send the confirm transaction to track when the packet was received
		ctx.flush(); //We want to "couple" them together so they are handled in the same tick

		throw CancelPacketTransmitException.INSTANCE; //After all is done we don't want to have the packet to be send twice, so throw exception and clear
	}
	
	private boolean wantConfirmation(int packetId, ByteBuf msg)
	{
		if (this.protocol.shouldConfirm(packetId))
		{
			return true;
		}

		if (this.protocol.getChangeGameStateClientbound() == packetId)
		{
			int reason = msg.readByte();
			if (reason == 3) //Gamemode change
			{
				return true;
			}
		}
		
		if (this.protocol.getAnimationClientbound() != null && this.protocol.getAnimationClientbound().equals(packetId))
		{
			ByteBufUtils.readVarInt(msg); //Entity id
			
			byte animation = msg.readByte();
			if (animation == 2) //Leave bed
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void addOutgoingBytes(ByteBuf buf)
	{
		int readerIndex = buf.readerIndex();
		
		int packetId = ByteBufUtils.readVarInt(buf);
		if (packetId == this.protocol.getChunkDataClientbound())
		{
			if (this.getUser().getProtocolVersion() <= 47)
			{
				//Write the whole packet by ourself
				ByteBuf newChunkBuf = Unpooled.buffer();
				
				ByteBufUtils.writeVarInt(newChunkBuf, packetId);
				
				ByteBufUtils.writeVarInt(newChunkBuf, buf.readInt()); //Chunk X
				ByteBufUtils.writeVarInt(newChunkBuf, buf.readInt()); //Chunk Z
				
				newChunkBuf.writeBoolean(buf.readBoolean()); //Continues

				convert1Point8Chunk(buf, newChunkBuf);
				
				buf = newChunkBuf;
			}
		}
		else if (this.protocol.getChunkDataBulkClientbound() != null && this.protocol.getChunkDataBulkClientbound().equals(packetId))
		{
			buf.readBoolean(); //Has sky light
			
			int count = ByteBufUtils.readVarInt(buf);
			
			//Write the whole packet by ourself
			ByteBuf newChunkBuf = Unpooled.buffer();
			
			ByteBufUtils.writeVarInt(newChunkBuf, packetId);
			ByteBufUtils.writeVarInt(newChunkBuf, count);
			
			for(int i = 0; i < count; i ++)
			{
				ByteBufUtils.writeVarInt(newChunkBuf, buf.readInt()); //Chunk X
				ByteBufUtils.writeVarInt(newChunkBuf, buf.readInt()); //Chunk Z
				
				ByteBufUtils.writeVarInt(newChunkBuf, buf.readShort()); //Primary Mask
			}
			
			buf = newChunkBuf;
		}
		else
		{
			buf.readerIndex(readerIndex);
		}
		
		ChunkedByteArrayUtils.writeVarInt(this.outgoingBytes, buf.readableBytes());
		
		this.outgoingBytes.write(buf);
	}
	
	private void convert1Point8Chunk(ByteBuf buf, ByteBuf newChunkBuf)
	{
		int primaryMask = buf.readShort();

		ByteBufUtils.writeVarInt(newChunkBuf, primaryMask);
		
		if (true)
		{
			return; //Don't do more handling
		}

		ByteBufUtils.readVarInt(buf); //Size

		for(int i = 0; i < ChunkSection.COUNT; i++)
		{
			if ((primaryMask & (1 << i)) != 0)
			{
				ChunkSection section = new ChunkSection();
				
				for(int j = 0; j < ChunkSection.SIZE; j++)
				{
					short mask = buf.readShortLE();
					if (mask != 0) //Skip over air
					{
						section.setBlock(j, mask);
					}
				}
				
				int bitsPerBlock = 4;
		        while (section.getPaletteSize() > 1 << bitsPerBlock)
		        {
		            bitsPerBlock += 1;
		        }
		        
		        long maxEntryValue = (1L << bitsPerBlock) - 1;
		        
		        newChunkBuf.writeByte(bitsPerBlock);
		        
		        ByteBufUtils.writeVarInt(newChunkBuf, section.getPaletteSize());
		        for(Short paletta : section.getPaletta())
		        {
			        ByteBufUtils.writeVarInt(newChunkBuf, paletta);
		        }
		        
		        int length = (int) Math.ceil(ChunkSection.SIZE * bitsPerBlock / 64.0);
		        
		        ByteBufUtils.writeVarInt(newChunkBuf, length);
		        
		        long[] data = new long[length];
				for(int j = 0; j < ChunkSection.SIZE; j++)
				{
		            short value = section.getBlock(j);
		            int bitIndex = j * bitsPerBlock;
		            int startIndex = bitIndex / 64;
		            int endIndex = ((j + 1) * bitsPerBlock - 1) / 64;
		            int startBitSubIndex = bitIndex % 64;
		            
		            data[startIndex] = data[startIndex] & ~(maxEntryValue << startBitSubIndex) | ((long) value & maxEntryValue) << startBitSubIndex;
		            
		            if (startIndex != endIndex)
		            {
		                int endBitSubIndex = 64 - startBitSubIndex;
		                
		                data[endIndex] = data[endIndex] >>> endBitSubIndex << endBitSubIndex | ((long) value & maxEntryValue) >> endBitSubIndex;
		            }
				}
				
				for(long value : data)
				{
					newChunkBuf.writeLong(value);
				}
			}
		}
	}
	
    protected static int getOnePointEightChunkByteCount(int p_180737_0_, boolean p_180737_1_, boolean p_180737_2_)
    {
        int i = p_180737_0_ * 2 * 16 * 16 * 16;
        int j = p_180737_0_ * 16 * 16 * 16 / 2;
        int k = p_180737_1_ ? p_180737_0_ * 16 * 16 * 16 / 2 : 0;
        int l = p_180737_2_ ? 256 : 0;
        return i + j + k + l;
    }
	
	private void addIncomingBytes(ByteBuf buf)
	{
		int readerIndex = buf.readerIndex();
		
		int packetId = ByteBufUtils.readVarInt(buf);
		if (this.protocol.getClickWindowServerbound() == packetId)
		{
			buf.skipBytes(1 + 2 + 1 + 2); //Window id (byte), Slot (short), Button (byte), Action number (short)
			
			if (this.user.getProtocolVersion() >= 83)
			{
				ByteBufUtils.readVarInt(buf); //Mode
			}
			else
			{
				buf.skipBytes(1); //Mode (byte)
			}
			
			this.readItem(buf);

			byte[] bytes = new byte[buf.readerIndex() - readerIndex + 1]; //+ 1 for NBT no data
			buf.readerIndex(readerIndex);
			buf.readBytes(bytes, 0, bytes.length - 1);
			
			readerIndex = 0;
			
			buf = Unpooled.wrappedBuffer(bytes);
		}
		
		if (this.user.getProtocolVersion() < 49)
		{
			if (packetId == this.protocol.getBlockPlacementServerbound())
			{
				buf.skipBytes(8 + 1); //Location (long), Face (byte)
				
				this.readItem(buf);

				byte[] bytes = new byte[buf.readerIndex() - readerIndex + 3 + 1]; //+ 3 for cursor pos and + 1 for NBT 0
				buf.readerIndex(readerIndex);
				buf.readBytes(bytes, 0, bytes.length - 4);
				
				//Write the facing
				buf.skipBytes(buf.readableBytes() - 3);
				buf.readBytes(bytes, bytes.length - 3, 3);
				
				readerIndex = 0;
				
				buf = Unpooled.wrappedBuffer(bytes);
			}
		}
		
		buf.readerIndex(readerIndex);

		ChunkedByteArrayUtils.writeVarInt(this.incomingBytes, buf.readableBytes());
		
		this.incomingBytes.write(buf);
	}
	
	private void readItem(ByteBuf buf)
	{
		if (this.user.getProtocolVersion() >= 402)
		{
			if (buf.readBoolean()) //Present
			{
				ByteBufUtils.readVarInt(buf); //Item id
				
				buf.skipBytes(1); //Stack size (byte)
			}
		}
		else
		{
			short itemId = buf.readShort();
			if (itemId != -1)
			{
				buf.skipBytes(1 + 2); //Stack size (byte), Metadata (short)
			}
		}
	}

	public void disconnected()
	{
		if (this.split == null)
		{
			this.split = new GameUserSplit(this, this.splitVersion++, this.startedOn, this.outgoingBytes);
		}

		this.split.setIncomplete(true);
		this.split.done(System.nanoTime(), this.incomingBytes);

		this.manager.addSplit(this.split);
		
		this.split = null;
		
		this.incomingBytes = null;
		this.outgoingBytes = null;
	}
	
	private static Kick getLunarKickMessage()
	{
		final BaseComponent[] reason = new ComponentBuilder("Onneksi Lunar on estetty tällä palvelimella :)").color(ChatColor.GREEN).create();
		
		return new Kick(ComponentSerializer.toString(reason));
	}
}
