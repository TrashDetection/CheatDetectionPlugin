package net.goldtreeservers.cheatdetectionplugin.common;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import manifold.ext.api.Jailbreak;
import net.goldtreeservers.cheatdetectionplugin.bungee.net.CancelPacketTransmitException;
import net.goldtreeservers.cheatdetectionplugin.bungee.net.RawPacket;
import net.goldtreeservers.cheatdetectionplugin.bungee.net.RawPacketEncoder;
import net.goldtreeservers.cheatdetectionplugin.common.protocol.ProtocolState;
import net.goldtreeservers.cheatdetectionplugin.common.user.GameUser;
import net.goldtreeservers.cheatdetectionplugin.common.user.User;
import net.goldtreeservers.cheatdetectionplugin.common.user.data.GameUserSplit;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ByteBufUtils;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ChunkedByteArray;

public class GameUserTest
{
	private EmbeddedChannel channel;
	
	private FakePlugin plugin;
	
	private User user;
	
	private ByteBuf incomings;
	private ByteBuf outgoings;
	
	@Before
	public void create()
	{
		this.plugin = new FakePlugin();
		this.plugin.onLoad();
		
		this.user = new User(this.plugin);
		
		this.channel = new EmbeddedChannel(new DummyHandlerIncoming(this.user), new RawPacketEncoder(this.user), new DummyHandlerOutgoing(this.user));
		
		this.channel.writeInbound(Unpooled.copiedBuffer(new byte[] { 0, 47, 0, 0, 0, 2})); //Change to handshake, 0 packet id, 47 protocol version, 0 hostname length, 00 for port and 2 to start login
		
		byte[] uuid = "8c0d9cc3-c4fc-4b2b-b945-3726f6a6b3fb".getBytes(Charsets.UTF_8);
		
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeByte(2); //Packet id
		buffer.writeByte(uuid.length);
		buffer.writeBytes(uuid);
		
		this.channel.writeOutbound(buffer);
		
		this.incomings = Unpooled.buffer();
		this.outgoings = Unpooled.buffer();
	}
	
	@Test
	public void testWeInCorrectState()
	{
		Assert.assertEquals(ProtocolState.GAME, this.user.getProtocolState());
	}
	
	private void writeForge(@Jailbreak GameUser user)
	{
		this.writeOutboundNoChannel(user.protocol.getForgeChannelsClientbound());
		
		if (false)
		{
			this.writeOutboundNoChannel(user.protocol.getForgeHelloClientbound());
			this.writeOutboundNoChannel(user.protocol.getForgeModListClientbound());
			this.writeOutboundNoChannel(user.protocol.getForgeRegistryPotionsClientbound());
			this.writeOutboundNoChannel(user.protocol.getForgeRegistryVillagerProfessionsClientbound());
			this.writeOutboundNoChannel(user.protocol.getForgeRegistryBlocksClientbound());
			this.writeOutboundNoChannel(user.protocol.getForgeRegistryItemsClientbound());
			this.writeOutboundNoChannel(user.protocol.getForgeResetClientbound());
		}
	}
	
	private void writeChannels(@Jailbreak GameUser user)
	{
		this.writeOutboundNoChannel(user.protocol.getChannelsClientbound());
	}
	
	@Test
	public void testBasicSplit()
	{
		@Jailbreak GameUser user = this.user.getGameUser();
		user.startedOn = System.nanoTime() - TimeUnit.SECONDS.toNanos(30);
		
		Assert.assertEquals(0, user.waitingConfirmations);
		Assert.assertNull(user.split);
		
		this.writeForge(user);
		
		this.writeOutboundWaitConfirmationJoin(new byte[] { 0x01 }); //Join, requires confirmation
		
		this.writeChannels(user);
		
		Assert.assertEquals(1, user.waitingConfirmations);
		Assert.assertNull(user.split);

		this.writeOutboundWaitConfirmation(new byte[] { 0x07 }); //Respawn, requires confirmation
		
		Assert.assertEquals(2, user.waitingConfirmations);
		Assert.assertNull(user.split);
		
		this.writeConfirmationIncoming();
		
		Assert.assertEquals(1, user.waitingConfirmations);
		Assert.assertNull(user.split);

		this.writeOutboundWaitConfirmation(new byte[] { 0x00 }); //Keep alive, no confirmation normally
		
		Assert.assertEquals(2, user.waitingConfirmations);
		Assert.assertNotNull(user.split);
		
		this.writeConfirmationIncoming();
		this.writeConfirmationIncoming();
		
		Assert.assertEquals(0, user.waitingConfirmations);
		Assert.assertNull(user.split);
		
		this.testChunkFull(this.incomings, this.outgoings);
	}
	
	@Test
	public void testSplitSplit()
	{
		@Jailbreak GameUser user = this.user.getGameUser();
		user.startedOn = System.nanoTime() - TimeUnit.SECONDS.toNanos(30);

		this.writeForge(user);
		
		this.writeOutboundWaitConfirmationJoin(new byte[] { 0x01 }); //Join, requires confirmation
		
		this.writeChannels(user);
		
		this.writeOutboundWaitConfirmation(new byte[] { 0x00 }); //Keep alive, no confirmation normally
		
		Assert.assertEquals(2, user.waitingConfirmations);
		Assert.assertEquals(0, user.waitingConfirmationsNext);
		Assert.assertNotNull(user.split);
		
		ByteBuf splitOutgoing = this.swapOutgoing();

		this.writeOutboundWaitConfirmation(new byte[] { 0x07 }); //Respawn, requires confirmation
		
		Assert.assertEquals(2, user.waitingConfirmations);
		Assert.assertEquals(1, user.waitingConfirmationsNext);
		Assert.assertNotNull(user.split);
		
		this.writeConfirmationIncoming();
		
		Assert.assertEquals(1, user.waitingConfirmations);
		Assert.assertEquals(1, user.waitingConfirmationsNext);
		Assert.assertNotNull(user.split);
		
		this.writeConfirmationIncoming();
		
		ByteBuf splitIncoming = this.swapIncoming();
		
		Assert.assertEquals(1, user.waitingConfirmations);
		Assert.assertEquals(0, user.waitingConfirmationsNext);
		Assert.assertNull(user.split);
		
		this.testChunkFull(splitIncoming, splitOutgoing);
		
		this.writeConfirmationIncoming();

		this.writeOutbound(new byte[] { 0x00 }); //Keep alive, no confirmation
		
		user.startedOn = System.nanoTime() - TimeUnit.SECONDS.toNanos(30);

		this.writeOutboundWaitConfirmation(new byte[] { 0x00 }); //Keep alive, no confirmation normally

		splitOutgoing = this.swapOutgoing();
		
		this.writeOutbound(new byte[] { 0x00 }); //Keep alive, no confirmation

		Assert.assertEquals(1, user.waitingConfirmations);
		Assert.assertNotNull(user.split);
		
		this.writeConfirmationIncoming();
		
		this.testChunkFull(this.incomings, splitOutgoing);
	}
	
	@Test
	public void testFoceSplitNoExtra()
	{
		@Jailbreak GameUser user = this.user.getGameUser();
		
		this.writeOutbound(new byte[] { 0x00 }); //Keep alive, no confirmation normally
		
		Assert.assertNull(user.split);
		
		user.forceSplit();
		
		Assert.assertNull(user.split);
		
		this.testChunkFull(this.incomings, this.outgoings);
	}
	
	@Test
	public void testFoceSplitContinue()
	{
		@Jailbreak GameUser user = this.user.getGameUser();
		
		user.startedOn = System.nanoTime() - TimeUnit.SECONDS.toNanos(30);

		this.writeForge(user);

		this.writeOutboundWaitConfirmationJoin(new byte[] { 0x01 }); //Join, requires confirmation
		
		this.writeChannels(user);
		
		this.writeOutboundWaitConfirmation(new byte[] { 0x00 }); //Keep alive, no confirmation normally

		Assert.assertEquals(2, user.waitingConfirmations);
		Assert.assertNotNull(user.split);
		
		this.writeConfirmationIncoming();
		
		user.forceSplit();
		
		Assert.assertEquals(1, user.waitingConfirmations);
		Assert.assertNotNull(user.split);

		ByteBuf splitIncoming = this.swapIncoming();
		ByteBuf splitOutgoing = this.swapOutgoing();
		
		GameUserSplit split = this.plugin.getGameUserManager().getNextSplit();
		
		this.testChunkFull(split, splitIncoming, splitOutgoing);
		
		this.writeConfirmationIncoming();
		
		Assert.assertEquals(0, user.waitingConfirmations);
		Assert.assertNull(user.split);
		
		this.writeOutbound(new byte[] { 0x00 }); //Keep alive, no confirmation
		this.writeOutbound(new byte[] { 0x00 }); //Keep alive, no confirmation
		
		GameUserSplit split2 = this.plugin.getGameUserManager().getNextSplit();
		
		this.testChunkFull(split2, this.incomings, this.outgoings);
		
		ByteBuf allIncoming = this.writeToOne(splitIncoming, this.incomings);
		ByteBuf allOutgoing = this.writeToOne(splitOutgoing, this.outgoings);
		
		ByteBuf allTestIncoming = this.writeToOne(split.getIncomingBytes(), split2.getIncomingBytes());
		ByteBuf allTestOutgoing = this.writeToOne(split.getOutgoingBytes(), split2.getOutgoingBytes());
		
		this.testBufs(allIncoming, allTestIncoming);
		this.testBufs(allOutgoing, allTestOutgoing);
	}
	
	@Test
	public void testConfirmationWithCondition()
	{
		@Jailbreak GameUser user = this.user.getGameUser();

		this.writeForge(user);

		this.writeOutboundWaitConfirmationJoin(new byte[] { 0x01 }); //Join, requires confirmation
		
		this.writeChannels(user);
		
		this.writeOutbound(new byte[] { 0x2B, 0 }); //Change game state, no confirmation normally
		this.writeOutboundWaitConfirmation(new byte[] { 0x2B, 3 }); //Change game state, confirmation on change gammeode
		
		Assert.assertEquals(2, user.waitingConfirmations);
		
		user.startedOn = System.nanoTime() - TimeUnit.SECONDS.toNanos(30);
		
		this.writeOutboundWaitConfirmation(new byte[] { 0x00 }); //Keep alive, no confirmation normally
		
		Assert.assertEquals(3, user.waitingConfirmations);
		
		this.writeConfirmationIncoming();
		this.writeConfirmationIncoming();
		this.writeConfirmationIncoming();

		this.testChunkFull(this.incomings, this.outgoings);
	}
		
	private ByteBuf writeToOne(ByteBuf... bufs)
	{
		ByteBuf buf = Unpooled.buffer();
		
		for(ByteBuf temp : bufs)
		{
			buf.writeBytes(temp);
			
			temp.resetReaderIndex();
		}
		
		return buf;
	}
	
	private ByteBuf writeToOne(ChunkedByteArray... arrays)
	{
		ByteBuf buf = Unpooled.buffer();
		
		for(ChunkedByteArray array : arrays)
		{
			ByteBufUtils.chunkedByteArrayToByteBuf(buf, array);
		}
		
		return buf;
	}
	
	private ByteBuf swapIncoming()
	{
		try
		{
			return this.incomings;
		}
		finally
		{
			this.incomings = Unpooled.buffer();
		}
	}
	
	private ByteBuf swapOutgoing()
	{
		try
		{
			return this.outgoings;
		}
		finally
		{
			this.outgoings = Unpooled.buffer();
		}
	}

	private void testBufs(ByteBuf test, ByteBuf test2)
	{
		Assert.assertEquals(test.readableBytes(), test2.readableBytes());
		
		while (test.isReadable())
		{
			Assert.assertEquals(test.readableBytes() + " | " + test2.readableBytes(), test.readByte(), test2.readByte());
		}
	}
	
	private void testChunkFull(ByteBuf testIncoming, ByteBuf testOutgoing)
	{
		GameUserSplit split = this.plugin.getGameUserManager().getNextSplit();
		
		this.testChunkFull(split, testIncoming, testOutgoing);
	}
	
	private void testChunkFull(GameUserSplit split, ByteBuf testIncoming, ByteBuf testOutgoing)
	{
		ByteBuf incomings = Unpooled.buffer();
		ByteBuf outgoings = Unpooled.buffer();
		
		Assert.assertEquals(testIncoming.readableBytes(), split.getIncomingBytes().size64());
		Assert.assertEquals(testOutgoing.readableBytes(), split.getOutgoingBytes().size64());
		
		ByteBufUtils.chunkedByteArrayToByteBuf(incomings, split.getIncomingBytes());
		ByteBufUtils.chunkedByteArrayToByteBuf(outgoings, split.getOutgoingBytes());
		
		Assert.assertEquals(testIncoming.readableBytes(), incomings.readableBytes());
		Assert.assertEquals(testOutgoing.readableBytes(), outgoings.readableBytes());
		
		while (testIncoming.isReadable())
		{
			Assert.assertEquals(testIncoming.readableBytes() + " | " + incomings.readableBytes(), testIncoming.readByte(), incomings.readByte());
		}
		
		while (testOutgoing.isReadable())
		{
			Assert.assertEquals(testOutgoing.readableBytes() + " | " + outgoings.readableBytes(), testOutgoing.readByte(), outgoings.readByte());
		}
		
		testIncoming.resetReaderIndex();
		testOutgoing.resetReaderIndex();
		
		incomings.resetReaderIndex();
		outgoings.resetReaderIndex();
	}
	
	private void writeOutboundWaitConfirmationJoin(byte[] bytes)
	{
		this.writeOutbound(bytes);

		ByteBufUtils.writeVarInt(this.outgoings, 5); //Length
		ByteBufUtils.writeVarInt(this.outgoings, 0x32); //Packet id
		this.outgoings.writeByte(0); //User inventory
		this.outgoings.writeShort(-3); //Action number
		this.outgoings.writeBoolean(false); //Accepted
	}
	
	private void writeOutboundWaitConfirmation(byte[] bytes)
	{
		ByteBufUtils.writeVarInt(this.outgoings, 5); //Length
		ByteBufUtils.writeVarInt(this.outgoings, 0x32); //Packet id
		this.outgoings.writeByte(0); //User inventory
		this.outgoings.writeShort(-5); //Action number
		this.outgoings.writeBoolean(false); //Accepted
		
		this.writeOutboundWaitConfirmationJoin(bytes);
	}
	
	private void writeOutbound(byte[] bytes)
	{
		this.writeOutbound(Unpooled.copiedBuffer(bytes));
	}

	private void writeOutbound(ByteBuf bytes)
	{
		this.writeOutboundNoChannel(bytes);
		
		bytes.resetReaderIndex();
		
		this.channel.writeOutbound(bytes);
	}
	
	private void writeOutboundNoChannel(ByteBuf bytes)
	{
		ByteBufUtils.writeVarInt(this.outgoings, bytes.readableBytes());
		
		this.outgoings.writeBytes(bytes);
	}
	
	private void writeConfirmationIncoming()
	{
		ByteBuf buf = Unpooled.buffer(4);
		buf.writeByte(0x0F); //Packet id
		buf.writeByte(0); //Inventory
		buf.writeShort(-3); //Id
		
		this.writeInbound(buf); //Confirmation
	}
	
	private void writeInbound(ByteBuf bytes)
	{
		ByteBufUtils.writeVarInt(this.incomings, bytes.readableBytes());
		
		this.incomings.writeBytes(bytes);
		
		bytes.resetReaderIndex();
		
		this.channel.writeInbound(bytes);
	}
	
	private static class FakePlugin extends AbstractCheatDetectionPlugin
	{
		@Override
		public void onLoad()
		{
			try
			{
				this.getDatabaseManager().loadDatabase("jdbc:sqlite::memory:");
			}
			catch (ClassNotFoundException | SQLException e)
			{
				throw new RuntimeException(e);
			}
		}
		
		@Override
		public void setupPlatform()
		{
		}

		@Override
		public void shutdownPlatform()
		{
		}

		@Override
		public File sqliteLocation()
		{
			return null;
		}
	}
	
	private static class DummyHandlerIncoming extends MessageToMessageDecoder<ByteBuf>
	{
		private User user;
		
		public DummyHandlerIncoming(User user)
		{
			this.user = user;
		}
		
		@Override
		protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception
		{
			try
			{
				this.user.readIncomingBytes(ctx, msg);
			}
			catch (CancelPacketTransmitException e)
			{
				msg.clear();
			}
			catch(Throwable e)
			{
				this.user.abort(e);
			}
		}
	}
	
	private static class DummyHandlerOutgoing extends MessageToByteEncoder<ByteBuf>
	{
		private User user;

		public DummyHandlerOutgoing(User user)
		{
			this.user = user;
		}
		
		@Override
		protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception
		{
			try
			{
				this.user.readOutgoingBytes(ctx, msg);
			}
			catch (CancelPacketTransmitException e)
			{
				msg.clear();
			}
			catch(Throwable e)
			{
				this.user.abort(e);
			}
		}
	}
}
