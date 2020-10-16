package net.goldtreeservers.cheatdetectionplugin.common.net.communication.incoming;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.IncomingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.RegisterServerOutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.handler.ClientConnectionHandler;

public class SessionRestoreFailedIncomingPacket implements IncomingPacket
{
	@Override
	public void read(ByteBuf in)
	{
	}

	@Override
	public void handle(ClientConnectionHandler handler, ChannelHandlerContext ctx)
	{
		handler.setSessionId(null);
		
		//Ok, that failed, just do server register then
		handler.getChannel().writeAndFlush(new RegisterServerOutgoingPacket());
	}
}
