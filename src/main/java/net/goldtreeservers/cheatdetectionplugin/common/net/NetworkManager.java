package net.goldtreeservers.cheatdetectionplugin.common.net;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.Getter;
import net.goldtreeservers.cheatdetectionplugin.common.AbstractCheatDetectionPlugin;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.OutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.PacketManager;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.incoming.UserViolationsIncomingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.DisconnectOutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.handler.ClientConnectionHandler;
import net.goldtreeservers.cheatdetectionplugin.common.net.handler.PacketDecoderHandler;
import net.goldtreeservers.cheatdetectionplugin.common.net.handler.PacketEncoderHandler;
import net.goldtreeservers.cheatdetectionplugin.common.utils.NettyUtils;

public class NetworkManager
{
	@Getter private final AbstractCheatDetectionPlugin plugin;
	
	@Getter private PacketManager packetManager;
	
	private EventLoopGroup bossGroup;
	
	private Channel channel;
	
	public NetworkManager(AbstractCheatDetectionPlugin plugin)
	{
		this.plugin = plugin;
		
		this.packetManager = new PacketManager();
		
		this.bossGroup = NettyUtils.createEventLoopGroup(1);
	}
	
	public void start()
	{
		this.start(null);
	}
	
	public void start(UUID uniqueId)
	{
		Bootstrap boostrap = new Bootstrap();
		boostrap.group(this.bossGroup)
			.channel(NettyUtils.getSocketChannel())
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>()
			{
				@Override
				protected void initChannel(SocketChannel channel) throws Exception
				{
					UserViolationsIncomingPacket.networkConnectionReconnected();
					
					NetworkManager.this.channel = channel;
					
					ChannelPipeline pipeline = channel.pipeline();
					
					pipeline.addLast(new ReadTimeoutHandler(15, TimeUnit.SECONDS));
					
					pipeline.addLast(new LengthFieldBasedFrameDecoder(8388607, 0, 3, 0, 3));
					pipeline.addLast(new PacketDecoderHandler(NetworkManager.this.packetManager));
					pipeline.addLast(new ClientConnectionHandler(NetworkManager.this.plugin, NetworkManager.this, uniqueId));

					pipeline.addLast(new LengthFieldPrepender(3));
					pipeline.addLast(new PacketEncoderHandler(NetworkManager.this.packetManager));
				}
			});

		boostrap.connect("151.80.42.85", 5555);
		//boostrap.connect("localhost", 5555);
	}
	
	public void stop()
	{
		if (this.channel != null)
		{
			this.channel.writeAndFlush(new DisconnectOutgoingPacket());
		}
		
		this.bossGroup.shutdownGracefully();
	}

	public void sendPacket(OutgoingPacket packet) 
	{
		this.channel.writeAndFlush(packet);
	}
}
