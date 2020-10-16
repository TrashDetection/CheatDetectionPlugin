package net.goldtreeservers.cheatdetectionplugin.common.net.handler;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.PacketManager;

public class PacketDecoderHandler extends ByteToMessageDecoder
{
	private PacketManager packetManager;
	
	public PacketDecoderHandler(PacketManager packetManager)
	{
		this.packetManager = packetManager;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
	{
		out.add(this.packetManager.readIncomingPacket(in));
	}
}
