package net.goldtreeservers.cheatdetectionplugin.common.net.communication.incoming;

import java.sql.SQLException;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.IncomingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.UserBannedOutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.handler.ClientConnectionHandler;
import net.md_5.bungee.BungeeCord;

public class BanUserIncomingPacket implements IncomingPacket
{
	private int banId;
	private int userId;
	
	@Override
	public void read(ByteBuf in) 
	{
		this.banId = in.readInt();
		this.userId = in.readInt();
	}

	@Override
	public void handle(ClientConnectionHandler handler, ChannelHandlerContext ctx)
	{
		UUID uuid = handler.getPlugin().getGameUserManager().getUniqueId(this.userId);
		if (uuid == null)
		{
			return; //??? Did they fuck up
		}
		
		try
		{
			if (handler.getPlugin().getBansManager().tryBan(this.banId, this.userId))
			{
				BungeeCord.getInstance().getPluginManager().dispatchCommand(BungeeCord.getInstance().getConsole(), "ban -N 4mo " + uuid + " Kielletyn modatun clientin käyttö [Ban Id: " + this.banId + "] --sender=RoskaAntiCheat");
			}
			
			handler.getChannel().writeAndFlush(new UserBannedOutgoingPacket(this.banId));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
