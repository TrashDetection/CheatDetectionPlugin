package net.goldtreeservers.cheatdetectionplugin.bungee.net;

import java.lang.reflect.Method;
import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import lombok.Getter;
import net.goldtreeservers.cheatdetectionplugin.common.protocol.ProtocolState;
import net.goldtreeservers.cheatdetectionplugin.common.user.User;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.protocol.DefinedPacket;
import net.md_5.bungee.protocol.MinecraftEncoder;
import net.md_5.bungee.protocol.Protocol;

public class CheatDetectionProxyPacketEncoder extends MinecraftEncoder implements ChannelOutboundHandler
{
	private static Method ENCODER_ENCODE_METHOD = null;
	
	static
	{
		try
		{
			CheatDetectionProxyPacketEncoder.ENCODER_ENCODE_METHOD = MinecraftEncoder.class.getDeclaredMethod("encode", ChannelHandlerContext.class, DefinedPacket.class, ByteBuf.class);
			CheatDetectionProxyPacketEncoder.ENCODER_ENCODE_METHOD.setAccessible(true);
		}
		catch (NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
	}
	
	@Getter private final User user;
	
	@Getter private final MinecraftEncoder encoder;
	
	@SuppressWarnings("deprecation")
	public CheatDetectionProxyPacketEncoder(MinecraftEncoder encoder, User user)
	{
		super(Protocol.HANDSHAKE, true, ProxyServer.getInstance().getProtocolVersion());
		
		this.user = user;
		
		this.encoder = encoder;
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
		
		this.encoder.setProtocol(protocol);
	}
	
	@Override
	public void setProtocolVersion(int protocolVersion)
	{
		if (this.user.getProtocolState() == ProtocolState.NOT_SUPPORTED)
		{
			this.encoder.setProtocolVersion(protocolVersion);
			
			return;
		}
		
		if (this.user.getProtocolState() == ProtocolState.GAME)
		{
			throw new IllegalStateException("You may not change protocol version while on the game protocol state");
		}
		
		//Don't allow anyone to mess up this
		if (this.user.hasProtocolVersion())
		{
			this.encoder.setProtocolVersion(this.user.getProtocolVersion());
		}
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, DefinedPacket packet, ByteBuf msg) throws Exception
	{
		CheatDetectionProxyPacketEncoder.ENCODER_ENCODE_METHOD.invoke(this.encoder, ctx, packet, msg); //Turn the packet to bytes

		if (this.user.getProtocolState() != ProtocolState.NOT_SUPPORTED)
		{
			int reader = msg.readerIndex();
			
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
			finally
			{
				msg.readerIndex(reader);
			}
		}
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception
	{
		super.handlerAdded(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
	{
		super.handlerRemoved(ctx);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception
	{
		super.bind(ctx, localAddress, promise);
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception
	{
		super.connect(ctx, remoteAddress, localAddress, promise);
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception
	{
		super.disconnect(ctx, promise);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception
	{
		super.close(ctx, promise);
	}

	@Override
	public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception
	{
		super.deregister(ctx, promise);
	}

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception
	{
		super.read(ctx);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
	{
		if (this.user.getProtocolState() != ProtocolState.NOT_SUPPORTED)
		{
			try
			{
				if (!super.acceptOutboundMessage(msg))
				{
					if (msg instanceof ByteBuf)
					{
						ByteBuf buf = (ByteBuf)msg;
	
						int reader = buf.readerIndex();
						
						try
						{
							this.user.readOutgoingBytes(ctx, buf);
						}
						catch (CancelPacketTransmitException e)
						{
							buf.clear();
							buf.release();
							
							return;
						}
						finally
						{
							buf.readerIndex(reader);
						}
					}
					else if (msg instanceof String)
					{
						//Used for legacy kick msg
					}
					else
					{
						this.user.abort("Unable to handle the outgoing packet type: " + msg.getClass());
					}
				}
			}
			catch (Throwable e)
			{
				this.user.abort(e);
			}
		}

		super.write(ctx, msg, promise);
	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception
	{
		super.flush(ctx);
	}
}
