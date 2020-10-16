package net.goldtreeservers.cheatdetectionplugin.bungee.commands;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import net.goldtreeservers.cheatdetectionplugin.bungee.BungeeCheatDetectionPlugin;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.incoming.UserViolationsIncomingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.RequestUserViolationsOutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.user.GameUser;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

public class CheatDetectionCommand extends Command
{
	private final BungeeCheatDetectionPlugin plugin;
	
	public CheatDetectionCommand(BungeeCheatDetectionPlugin plugin)
	{
		super("trashdetection", null, "td", "ez", "rekt", "scrub", "nub", "skid", "goplayfortnite");
		
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if (args.length == 0 || !sender.hasPermission("trashdetection.command.use"))
		{
			sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7Take the §d§lL§7, HAHA! You are way too ez"));
			
			return;
		}
		
		if (!(sender instanceof ProxiedPlayer) && !(sender instanceof ConsoleCommandSender))
		{
			sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7Who are you?"));
			
			return;
		}
		
		switch(args[0])
		{
			case "violations":
			{
				if (!sender.hasPermission("trashdetection.command.violations.use"))
				{
					sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7You are lacking permissions my dude :("));
					
					return;
				}
				
				if (args.length == 2)
				{
					UUID uuid;
					
					try
					{
						uuid = UUID.fromString(args[1]);
					}
					catch(IllegalArgumentException ex)
					{
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
						if (target != null)
						{
							uuid = target.getUniqueId();
						}
						else
						{
							sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7Player not found"));
							
							return;
						}
					}
					
					GameUser gameUser = this.plugin.getGameUserManager().getUser(uuid);
					if (gameUser != null)
					{
						boolean result;
						if (sender instanceof ProxiedPlayer)
						{
							result = UserViolationsIncomingPacket.addRequest(gameUser.getSessionId(), ((ProxiedPlayer)sender).getUniqueId());
						}
						else if (sender instanceof ConsoleCommandSender)
						{
							result = UserViolationsIncomingPacket.addRequest(gameUser.getSessionId(), null);
						}
						else
						{
							return;
						}
						
						if (result)
						{
							sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7Sending request.."));
							
							this.plugin.getNetworkManager().sendPacket(new RequestUserViolationsOutgoingPacket(gameUser.getSessionId()));
						}
						else
						{
							sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7Request is being processed.."));
						}
					}
					else
					{
						sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7Player not found"));
					}
				}
				else
				{
					sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7Missing username or uuid"));
				}
			}
			break;
			case "restart-plugin":
			{
				if (!sender.hasPermission("trashdetection.command.restart-plugin.use"))
				{
					sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7You are lacking permissions my dude :("));
					
					return;
				}
				
				if (args.length == 2 && args[1].equals("YESIAM"))
				{
					try
					{
						this.plugin.getLoader().unloadAndDisable();
						this.plugin.getLoader().loadPlugin();
					}
					catch (Throwable e)
					{
						this.criticalExceptionRestoreCommands(sender, e);
					}
				}
				else
				{
					sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7ARE YOU SURE ABOUT THIS!?"));
				}
			}
			break;
			case "update":
			{
				if (!sender.hasPermission("trashdetection.command.update.use"))
				{
					sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7You are lacking permissions my dude :("));
					
					return;
				}
				
				if (args.length != 2)
				{
					sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7File name thanks!"));
					
					return;
				}
				
				String path = BungeeCheatDetectionPlugin.class.getProtectionDomain().getCodeSource().getLocation().getPath();

				try
				{
					String decodedPath = URLDecoder.decode(path, "UTF-8");
					
					InputStream in = new URL("https://trashdetection.com/files/" + args[1]).openStream();
					
					this.plugin.getLoader().unloadAndDisable();
					
					Files.copy(in, new File(decodedPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
					
					this.plugin.getLoader().loadPlugin();
				}
				catch (Throwable e)
				{
					this.criticalExceptionRestoreCommands(sender, e);
				}
			}
			break;
			case "toid":
			{
				if (!sender.hasPermission("trashdetection.command.violations.use")) //Use same permission
				{
					sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7You are lacking permissions my dude :("));
					
					return;
				}
				
				if (args.length == 2)
				{
					UUID uuid;
					
					try
					{
						uuid = UUID.fromString(args[1]);
					}
					catch(IllegalArgumentException ex)
					{
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
						if (target != null)
						{
							uuid = target.getUniqueId();
						}
						else
						{
							sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7Player not found"));
							
							return;
						}
					}
					
					int userId = this.plugin.getGameUserManager().getUserId(uuid);
					
					TextComponent userIdMessage = new TextComponent(Integer.toString(userId));
					userIdMessage.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, Integer.toString(userId)));
					userIdMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to copy to the chat!").create()));
					
					ComponentBuilder message = new ComponentBuilder("");
					message.append(TextComponent.fromLegacyText("§4§lTrash Detection > §7The user id is: §e"));
					message.append(userIdMessage);
					
					sender.sendMessage(message.create());
				}
				else
				{
					sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7Missing id"));
				}
			}
			break;
			case "fromid":
			{
				if (!sender.hasPermission("trashdetection.command.violations.use")) //Use same permission
				{
					sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7You are lacking permissions my dude :("));
					
					return;
				}
				
				if (args.length == 2)
				{
					int userId;
					
					try
					{
						userId = Integer.parseInt(args[1]);
					}
					catch(NumberFormatException ex)
					{
						sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7Bruh, that aint id"));
						
						return;
					}
					
					UUID uuid = this.plugin.getGameUserManager().getUniqueId(userId);
					
					TextComponent userUuidMessage = new TextComponent(uuid.toString());
					userUuidMessage.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, uuid.toString()));
					userUuidMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to copy to the chat!").create()));
					
					ComponentBuilder message = new ComponentBuilder("");
					message.append(TextComponent.fromLegacyText("§4§lTrash Detection > §7The user uuid is: §e"));
					message.append(userUuidMessage);
					
					sender.sendMessage(message.create());
				}
				else
				{
					sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7Missing id"));
				}
			}
			break;
			default:
			{
				sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7No fancy yet"));
			}
			break;
		}
	}
	
	private void criticalExceptionRestoreCommands(CommandSender sender, Throwable e)
	{
		Throwable commandException = null;
		
		try
		{
			//Re-register commands if possible to allow reverting this in-proper state!
			this.plugin.getLoader().registerCommands(this.plugin);
			
			sender.sendMessage(TextComponent.fromLegacyText("§4§lTrash Detection > §7Commands were restored to allow safe restore from this state!"));
		}
		catch (Throwable ex)
		{
			commandException = ex;
		}
		
		RuntimeException ex = new RuntimeException(e);
		if (commandException != null)
		{
			ex.addSuppressed(commandException);
		}
		
		throw ex;
	}
}
