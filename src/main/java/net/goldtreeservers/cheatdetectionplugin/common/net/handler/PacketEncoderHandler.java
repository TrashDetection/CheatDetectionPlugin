package net.goldtreeservers.cheatdetectionplugin.common.net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.OutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.PacketManager;

public class PacketEncoderHandler extends MessageToByteEncoder<OutgoingPacket>
{
	private PacketManager packetManager;
	
	public PacketEncoderHandler(PacketManager packetManager)
	{
		this.packetManager = packetManager;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, OutgoingPacket msg, ByteBuf out) throws Exception
	{
		this.packetManager.writeOutgoingPacket(msg, out);
	}
}
