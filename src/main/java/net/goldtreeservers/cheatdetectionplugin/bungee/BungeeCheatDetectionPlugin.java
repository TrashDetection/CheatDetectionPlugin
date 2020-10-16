package net.goldtreeservers.cheatdetectionplugin.bungee;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Getter;
import net.goldtreeservers.cheatdetectionplugin.bungee.net.CheatDetectionProxyPacketDecoder;
import net.goldtreeservers.cheatdetectionplugin.bungee.net.CheatDetectionProxyPacketEncoder;
import net.goldtreeservers.cheatdetectionplugin.bungee.net.RawPacketEncoder;
import net.goldtreeservers.cheatdetectionplugin.common.AbstractCheatDetectionPlugin;
import net.goldtreeservers.cheatdetectionplugin.common.runnable.SendUserDataRunnable;
import net.goldtreeservers.cheatdetectionplugin.common.user.User;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ReflectionUtils;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.netty.PipelineUtils;
import net.md_5.bungee.protocol.MinecraftDecoder;
import net.md_5.bungee.protocol.MinecraftEncoder;

public class BungeeCheatDetectionPlugin extends AbstractCheatDetectionPlugin
{
	@Getter private final BungeeCheatDetectionPluginLoader loader;
	
	private ChannelInitializer<Channel> originalChannelInitializer;
	private ChannelInitializer<Channel> cheatDetectionChannelInitializer;
	
	private ChannelGroup channels;
	
	public BungeeCheatDetectionPlugin(BungeeCheatDetectionPluginLoader loader)
	{
		this.loader = loader;
	}

	@Override
	public void setupPlatform()
	{
		this.originalChannelInitializer = PipelineUtils.SERVER_CHILD;

		if (this.originalChannelInitializer.getClass().getEnclosingClass() != PipelineUtils.class)
		{
			this.originalChannelInitializer = null;
			
			throw new UnsupportedOperationException("We will and only inject to the default BungeeCord channel initializer, nothing else is supported and never will be, I don't want to deal with the issues!");
		}
		
		this.channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
		
		try
		{
			Field serverChildField = PipelineUtils.class.getDeclaredField("SERVER_CHILD");
			serverChildField.setAccessible(true);
			
			this.cheatDetectionChannelInitializer = new ChannelInitializer<Channel>()
			{
				private final Method initChannelMethod;
				
				{
					this.initChannelMethod = BungeeCheatDetectionPlugin.this.originalChannelInitializer.getClass().getDeclaredMethod("initChannel", Channel.class);
					this.initChannelMethod.setAccessible(true);
				}
				
		        @Override
		        protected void initChannel(Channel channel) throws Exception
		        {
		        	Class<?> handledBy = channel.pipeline().last().getClass();
					
		        	this.initChannelMethod.invoke(BungeeCheatDetectionPlugin.this.originalChannelInitializer, channel);
					
					//They disconencted
					if (!channel.isOpen())
					{
						return;
					}
		        	
		        	if (handledBy != this.getClass())
		        	{
		        		throw new UnsupportedOperationException("We will and only support the default BungeeCord channel initializer, looks like some other plugin replaced it with their own, this is not supported and never will be, I don't want to deal with the issues! The handler class was: " + handledBy);
		        	}

		        	MinecraftDecoder decoder = (MinecraftDecoder)channel.pipeline().get(PipelineUtils.PACKET_DECODER);
		        	if (decoder.getClass() != MinecraftDecoder.class)
		        	{
		        		throw new UnsupportedOperationException("We will and only inject to the default BungeeCord packet decoder, nothing else is supported and never will be, I don't want to deal with the issues!");
		        	}
		        	
		        	MinecraftEncoder encoder = (MinecraftEncoder)channel.pipeline().get(PipelineUtils.PACKET_ENCODER);
		        	if (encoder.getClass() != MinecraftEncoder.class)
		        	{
		        		throw new UnsupportedOperationException("We will and only inject to the default BungeeCord packet encoder, nothing else is supported and never will be, I don't want to deal with the issues!");
		        	}
		        	
		        	BungeeCheatDetectionPlugin.this.channels.add(channel);

		        	User user = new User(BungeeCheatDetectionPlugin.this);
		        	
		        	//Replace them, like, literally
		        	channel.pipeline().replace(PipelineUtils.PACKET_DECODER, PipelineUtils.PACKET_DECODER, new CheatDetectionProxyPacketDecoder(decoder, user));
		        	channel.pipeline().replace(PipelineUtils.PACKET_ENCODER, PipelineUtils.PACKET_ENCODER, new CheatDetectionProxyPacketEncoder(encoder, user));
		        
		        	channel.pipeline().addBefore(PipelineUtils.PACKET_ENCODER, "raw-packet-encoder", new RawPacketEncoder(user));
		        }
			};
			
			ReflectionUtils.setFinalField(serverChildField, this.cheatDetectionChannelInitializer);
			
			this.setListenersTo(this.cheatDetectionChannelInitializer, this.originalChannelInitializer);
			
			this.loader.getProxy().getScheduler().schedule(this.loader, new SendUserDataRunnable(this.getGameUserManager()), 1, 1, TimeUnit.MILLISECONDS);
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setListenersTo(ChannelInitializer<Channel> intializer, ChannelInitializer<Channel> replace) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Field listenersField = BungeeCord.class.getDeclaredField("listeners");
		listenersField.setAccessible(true);
		
		Collection<Channel> listeners = (Collection<Channel>)listenersField.get(BungeeCord.getInstance());
		
		for(Channel channel : listeners)
		{
			Iterator<Entry<String, ChannelHandler>> iterator = channel.pipeline().iterator();
			while (iterator.hasNext())
			{
				ChannelHandler handler = iterator.next().getValue();
				if (handler.getClass().getEnclosingClass() == ServerBootstrap.class)
				{
					Field childHandlerField = handler.getClass().getDeclaredField("childHandler");
					childHandlerField.setAccessible(true);

					Object childHandler = childHandlerField.get(handler);
					if (childHandler == replace)
					{
						childHandlerField.set(handler, intializer);
					}
				}
			}
		}
	}

	@Override
	public void shutdownPlatform()
	{
		if (this.originalChannelInitializer == null)
		{
			return;
		}
		
		try
		{
			Field serverChildField = PipelineUtils.class.getDeclaredField("SERVER_CHILD");
			serverChildField.setAccessible(true);

			ReflectionUtils.setFinalField(serverChildField, this.originalChannelInitializer); //Return to the default
			
			this.setListenersTo(this.originalChannelInitializer, this.cheatDetectionChannelInitializer);
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		
		Iterator<Channel> channels = this.channels.iterator();
		while (channels.hasNext())
		{
			Channel channel = channels.next();
			ChannelPipeline pipeline = channel.pipeline();
			
			ChannelHandler handler = pipeline.get("raw-packet-encoder");
			if (handler instanceof RawPacketEncoder)
			{
				((RawPacketEncoder)handler).getUser().abortSilence();
			}
			
			channels.remove();
		}
	}

	@Override
	public File sqliteLocation()
	{
		return new File(this.loader.getDataFolder(), "database.db");
	}
}
