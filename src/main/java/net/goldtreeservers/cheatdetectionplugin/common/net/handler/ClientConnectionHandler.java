package net.goldtreeservers.cheatdetectionplugin.common.net.handler;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import lombok.Setter;
import net.goldtreeservers.cheatdetectionplugin.common.AbstractCheatDetectionPlugin;
import net.goldtreeservers.cheatdetectionplugin.common.net.NetworkManager;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.IncomingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.LoginOutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.RegisterServerOutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.RestoreServerOutgoingPacket;

public class ClientConnectionHandler extends SimpleChannelInboundHandler<IncomingPacket>
{
	@Getter private final AbstractCheatDetectionPlugin plugin;
	@Getter private final NetworkManager networkManager;
	
	@Getter private Channel channel;
	
	@Getter @Setter private UUID sessionId;
	
	public ClientConnectionHandler(AbstractCheatDetectionPlugin plugin, NetworkManager networkManager, UUID sessionId)
	{
		this.plugin = plugin;
		this.networkManager = networkManager;
		
		this.sessionId = sessionId;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx)
	{
		this.channel = ctx.channel();
		this.channel.writeAndFlush(new LoginOutgoingPacket("isokissa3", "bestanticheatyeyewowowoomgisthisevenapasswordwtfisgoingon"));
		
		if (this.sessionId == null)
		{
			this.channel.writeAndFlush(new RegisterServerOutgoingPacket());
		}
		else
		{
			this.channel.writeAndFlush(new RestoreServerOutgoingPacket(this.sessionId));
		}

		ctx.fireChannelActive();
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, IncomingPacket msg) throws Exception
	{
		msg.handle(this, ctx);
	}
	
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) 
	{
		ctx.channel().eventLoop().schedule(() -> this.networkManager.start(this.sessionId), 3, TimeUnit.SECONDS);
		
		ctx.fireChannelUnregistered();
	}
}
