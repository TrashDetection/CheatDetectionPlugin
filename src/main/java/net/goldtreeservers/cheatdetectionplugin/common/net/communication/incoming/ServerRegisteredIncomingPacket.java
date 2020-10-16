package net.goldtreeservers.cheatdetectionplugin.common.net.communication.incoming;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.IncomingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.handler.ClientConnectionHandler;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ByteBufUtils;

public class ServerRegisteredIncomingPacket implements IncomingPacket
{
	@Getter private UUID uniqueId;
	
	@Override
	public void read(ByteBuf in)
	{
		this.uniqueId = ByteBufUtils.readUniqueId(in);
	}

	@Override
	public void handle(ClientConnectionHandler handler, ChannelHandlerContext ctx)
	{
		handler.setSessionId(this.uniqueId);
		
		System.out.println("Cheat Detection has been conencted to the server");
	}
}
