package net.goldtreeservers.cheatdetectionplugin.common.net.communication.incoming;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.IncomingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.PongOutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.handler.ClientConnectionHandler;

public class PingIncomingPacket implements IncomingPacket
{
	@Override
	public void read(ByteBuf in) 
	{
	}

	@Override
	public void handle(ClientConnectionHandler handler, ChannelHandlerContext ctx)
	{
		handler.getChannel().writeAndFlush(new PongOutgoingPacket());
	}
}
