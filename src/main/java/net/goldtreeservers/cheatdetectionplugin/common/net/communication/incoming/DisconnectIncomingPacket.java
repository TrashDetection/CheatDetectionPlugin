package net.goldtreeservers.cheatdetectionplugin.common.net.communication.incoming;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.IncomingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.handler.ClientConnectionHandler;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ByteBufUtils;

public class DisconnectIncomingPacket implements IncomingPacket
{
	@Getter private String reason;
	
	@Override
	public void read(ByteBuf in)
	{
		this.reason = ByteBufUtils.readString(in);
	}

	@Override
	public void handle(ClientConnectionHandler handler, ChannelHandlerContext ctx)
	{
		System.out.println("Cheat Detection was disconencted from the server for reason: " + this.reason);
		
		ctx.close();
	}
}
