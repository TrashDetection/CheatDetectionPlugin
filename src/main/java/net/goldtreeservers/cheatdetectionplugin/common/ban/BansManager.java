package net.goldtreeservers.cheatdetectionplugin.common.ban;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import lombok.Getter;
import net.goldtreeservers.cheatdetectionplugin.common.AbstractCheatDetectionPlugin;

public class BansManager
{
	@Getter private final AbstractCheatDetectionPlugin plugin;
	
	public BansManager(AbstractCheatDetectionPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean tryBan(int banId, int userId) throws SQLException
	{
		try(PreparedStatement preparedStatement = this.plugin.getDatabaseManager().getConnection().prepareStatement("INSERT INTO bans(id, user_id) VALUES(?, ?)"))
		{
			preparedStatement.setInt(1, banId);
			preparedStatement.setInt(2, userId);
			
			return preparedStatement.executeUpdate() > 0;
		}
	}
}
