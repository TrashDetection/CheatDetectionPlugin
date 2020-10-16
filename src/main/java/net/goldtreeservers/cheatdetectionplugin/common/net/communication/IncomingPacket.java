package net.goldtreeservers.cheatdetectionplugin.common.net.communication;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.goldtreeservers.cheatdetectionplugin.common.net.handler.ClientConnectionHandler;

public interface IncomingPacket
{
	public void read(ByteBuf in);
	public void handle(ClientConnectionHandler handler, ChannelHandlerContext ctx);
}
