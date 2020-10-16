package net.goldtreeservers.cheatdetectionplugin.common.runnable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.UserDataOutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.user.GameUserManager;
import net.goldtreeservers.cheatdetectionplugin.common.user.data.GameUserSplit;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ByteBufUtils;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;

public class SendUserDataRunnable implements Runnable
{
	private static final LZ4Factory lz4Factory = LZ4Factory.fastestInstance();
	
	private GameUserManager gameUserManager;
	
	private ByteBuf writerBuffer;
	
	public SendUserDataRunnable(GameUserManager gameUserManager)
	{
		this.gameUserManager = gameUserManager;
		
		//Start with 1Mb
		this.writerBuffer = Unpooled.buffer(1024 * 1024);
	}
	
	@Override
	public void run()
	{
		try
		{
			GameUserSplit split;
			while ((split = this.gameUserManager.getNextSplit()) != null)
			{
				ByteBuf buffer = this.writerBuffer.clear();

				ByteBufUtils.writeVarInt(this.writerBuffer, split.getGameUser().getSessionId());
				ByteBufUtils.writeVarInt(this.writerBuffer, split.getGameUser().getUserId());
				ByteBufUtils.writeVarInt(this.writerBuffer, split.getGameUser().getUser().getProtocolVersion());
				
				//Extra data
				ByteBufUtils.writeVarInt(this.writerBuffer, split.getVersion());
				
				this.writerBuffer.writeBoolean(split.isIncomplete());
				this.writerBuffer.writeLong(split.getStartedOn());
				this.writerBuffer.writeLong(split.getEndedOn());
				
				long baseSeed = (long)split.getGameUser().getUserId() | (long)split.getGameUser().getSessionId() << 32;
				
				//Last write all the heavy stuff
				long incomingHash = ByteBufUtils.writeChunkedByteArray(this.writerBuffer, split.getIncomingBytes(), baseSeed);
				long outgoingHash = ByteBufUtils.writeChunkedByteArray(this.writerBuffer, split.getOutgoingBytes(), baseSeed);
				
				LZ4Compressor compressor = SendUserDataRunnable.getCompressor();

				int originalSize = buffer.readableBytes();
				
				byte[] compressedBuffer = new byte[compressor.maxCompressedLength(originalSize)];
				int size = compressor.compress(buffer.array(), buffer.arrayOffset(), originalSize, compressedBuffer, 0);

				this.gameUserManager.getPlugin().getNetworkManager().sendPacket(new UserDataOutgoingPacket(incomingHash, outgoingHash, originalSize, Unpooled.wrappedBuffer(compressedBuffer, 0, size)));
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}
	
	private static LZ4Compressor getCompressor()
	{
		return SendUserDataRunnable.lz4Factory.fastCompressor();
	}
}
