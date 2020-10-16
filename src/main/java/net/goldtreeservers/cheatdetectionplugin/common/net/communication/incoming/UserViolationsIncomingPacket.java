package net.goldtreeservers.cheatdetectionplugin.common.net.communication.incoming;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.IncomingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.handler.ClientConnectionHandler;
import net.goldtreeservers.cheatdetectionplugin.common.user.violations.UserViolation;
import net.goldtreeservers.cheatdetectionplugin.common.user.violations.player.combat.NotFacingAttackingEntityViolation;
import net.goldtreeservers.cheatdetectionplugin.common.user.violations.player.combat.ReachViolation;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ByteBufUtils;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class UserViolationsIncomingPacket implements IncomingPacket
{
	private static Map<Integer, Class<? extends UserViolation>> violationIds = new HashMap<>();
	
	private static Map<Integer, Set<UUID>> requestedUsers = new HashMap<>();
	
	static
	{
		UserViolationsIncomingPacket.violationIds.put(0, ReachViolation.class);
		UserViolationsIncomingPacket.violationIds.put(1, NotFacingAttackingEntityViolation.class);
	}

	private int sessionId;
	private List<UserViolation> violations;
	
	@Override
	public void read(ByteBuf in)
	{
		this.sessionId = in.readInt();
		
		int amount = in.readInt();
		
		this.violations = new ArrayList<>(amount);
		
		for(int i = 0; i < amount; i++)
		{
			int id = ByteBufUtils.readVarInt(in);
			
			Class<? extends UserViolation> classViolation = UserViolationsIncomingPacket.violationIds.get(id);
			
			try
			{
				Method method = classViolation.getMethod("serialize", ByteBuf.class);
				
				UserViolation violation = (UserViolation)method.invoke(null, in);
				
				this.violations.add(violation);
			}
			catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void handle(ClientConnectionHandler handler, ChannelHandlerContext ctx)
	{
		Set<UUID> requesters;
		
		synchronized(UserViolationsIncomingPacket.requestedUsers)
		{
			requesters = UserViolationsIncomingPacket.requestedUsers.remove(this.sessionId);
			if (requesters == null)
			{
				return;
			}
		}
		
		if (violations.size() == 0)
		{
			UserViolationsIncomingPacket.sendTo(requesters, TextComponent.fromLegacyText("§4§lTrash Detection > §7The user is kind and peaceful warrior on this block game, the kind you expect ur kids to be"));
		}
		else
		{
			UserViolationsIncomingPacket.sendTo(requesters, TextComponent.fromLegacyText("§4§lTrash Detection > §7Angery >:("));

			List<ReachViolation> reachViolations = this.violations.stream().filter((v) -> v instanceof ReachViolation).map((v) -> (ReachViolation)v).collect(Collectors.toList());
			if (reachViolations.size() > 0)
			{
				Collections.reverse(reachViolations);
				
				ComponentBuilder reachHoverBuilder = new ComponentBuilder("");
				for(int i = 0; i < Math.min(15, reachViolations.size()); i++)
				{
					double reach = reachViolations.get(i).getReach();
					
					reachHoverBuilder.append("Reach: " + reach + "\n").color(reach > 3.5D ? ChatColor.RED : ChatColor.YELLOW);
				}
				
				reachHoverBuilder.append("\n");
				reachHoverBuilder.append("Total: " + reachViolations.size() + "\n");
				reachHoverBuilder.append("Avg: " + reachViolations.stream().mapToDouble((v) -> v.getReach()).average().getAsDouble() + "\n");
				reachHoverBuilder.append("Max: " + reachViolations.stream().mapToDouble((v) -> v.getReach()).max().getAsDouble() + "\n");
				
				ComponentBuilder reachBuilder = new ComponentBuilder("Reach Violations: " + reachViolations.size()).color(ChatColor.RED);
				reachBuilder.event(new HoverEvent(Action.SHOW_TEXT, reachHoverBuilder.create()));
				
				UserViolationsIncomingPacket.sendTo(requesters, reachBuilder.create());
			}
			
			List<NotFacingAttackingEntityViolation> notFacingViolations = this.violations.stream().filter((v) -> v instanceof NotFacingAttackingEntityViolation).map((v) -> (NotFacingAttackingEntityViolation)v).collect(Collectors.toList());
			if (notFacingViolations.size() > 0)
			{
				Collections.reverse(notFacingViolations);
				
				ComponentBuilder notFacingHoverBuilder = new ComponentBuilder("TODO: More fancy data?\n");
				
				notFacingHoverBuilder.append("\n");
				notFacingHoverBuilder.append("Different Entities: " + notFacingViolations.stream().mapToInt((v) -> v.getEntityId()).distinct().count() + "\n");
				
				ComponentBuilder notFacingBuilder = new ComponentBuilder("Not Facing Violations: " + notFacingViolations.size()).color(ChatColor.RED);
				notFacingBuilder.event(new HoverEvent(Action.SHOW_TEXT, notFacingHoverBuilder.create()));
				
				UserViolationsIncomingPacket.sendTo(requesters, notFacingBuilder.create());
			}
		}
	}
	
	public static boolean addRequest(int sessionId, UUID requester)
	{
		boolean wasNew = false;
		
		synchronized(UserViolationsIncomingPacket.requestedUsers)
		{
			Set<UUID> requesters = UserViolationsIncomingPacket.requestedUsers.get(sessionId);
			if (requesters == null)
			{
				UserViolationsIncomingPacket.requestedUsers.put(sessionId, requesters = new HashSet<>());
				
				wasNew = true;
			}
			
			requesters.add(requester);
		}
		
		return wasNew;
	}
	
	public static void networkConnectionReconnected()
	{
		synchronized(UserViolationsIncomingPacket.requestedUsers)
		{
			if (UserViolationsIncomingPacket.requestedUsers.size() <= 0)
			{
				return;
			}
			
			BaseComponent[] msg = TextComponent.fromLegacyText("§4§lTrash Detection > §7Disconnected while making request");
			for(Set<UUID> receivers : UserViolationsIncomingPacket.requestedUsers.values())
			{
				UserViolationsIncomingPacket.sendTo(receivers, msg);
			}
			
			UserViolationsIncomingPacket.requestedUsers.clear();
		}
	}
	
	private static void sendTo(Set<UUID> receivers, BaseComponent[] msg)
	{
		for(UUID uuid : receivers)
		{
			if (uuid == null)
			{
				BungeeCord.getInstance().getConsole().sendMessage(msg);
			}
			else
			{
				ProxyServer.getInstance().getPlayer(uuid).sendMessage(msg);
			}
		}
	}
}
