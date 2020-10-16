package net.goldtreeservers.cheatdetectionplugin.bungee.net;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import lombok.Getter;
import net.goldtreeservers.cheatdetectionplugin.common.protocol.ProtocolState;
import net.goldtreeservers.cheatdetectionplugin.common.user.User;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.protocol.MinecraftDecoder;
import net.md_5.bungee.protocol.Protocol;

public class CheatDetectionProxyPacketDecoder extends MinecraftDecoder implements ChannelInboundHandler
{
	@Getter private final User user;
	
	@Getter private final MinecraftDecoder decoder;
	
	@SuppressWarnings("deprecation")
	public CheatDetectionProxyPacketDecoder(MinecraftDecoder decoder, User user)
	{
		super(Protocol.HANDSHAKE, true, ProxyServer.getInstance().getProtocolVersion());
		
		this.user = user;
		
		this.decoder = decoder;
	}
	
	@Override
	public void setProtocol(Protocol protocol)
	{
		if (this.user.getProtocolState() != ProtocolState.NOT_SUPPORTED)
		{
			if (this.user.getProtocolState() == ProtocolState.LOGIN && protocol != Protocol.LOGIN)
			{
				throw new IllegalStateException("Illagel login protocol state");
			}
			else if (this.user.getProtocolState() == ProtocolState.GAME && protocol != Protocol.GAME)
			{
				throw new IllegalStateException("Illagel game protocol state");
			}
		}
		
		this.decoder.setProtocol(protocol);
	}
	
	@Override
	public void setProtocolVersion(int protocolVersion)
	{
		if (this.user.getProtocolState() == ProtocolState.NOT_SUPPORTED)
		{
			this.decoder.setProtocolVersion(protocolVersion);
			
			return;
		}
		
		if (this.user.getProtocolState() == ProtocolState.GAME)
		{
			throw new IllegalStateException("You may not change protocol version while on the game protocol state");
		}
		
		//Don't allow anyone to mess up this
		if (this.user.hasProtocolVersion())
		{
			this.decoder.setProtocolVersion(this.user.getProtocolVersion());
		}
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception
	{
		if (this.user.getProtocolState() != ProtocolState.NOT_SUPPORTED)
		{
			int reader = msg.readerIndex();
			
			try
			{
				this.user.readIncomingBytes(ctx, msg);
			}
			catch (CancelPacketTransmitException e)
			{
				msg.clear();
				
				return;
			}
			catch(Throwable e)
			{
				this.user.abort(e);
			}
			finally
			{
				msg.readerIndex(reader);
			}
		}

		this.decoder.channelRead(ctx, msg.retain());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception
	{
		this.user.disconnected();
		
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
	{
		super.channelRead(ctx, msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
	{
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception
	{
		super.channelRegistered(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception
	{
		super.channelUnregistered(ctx);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception
	{
		super.channelWritabilityChanged(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
	{
		super.userEventTriggered(ctx, evt);
	}
}
