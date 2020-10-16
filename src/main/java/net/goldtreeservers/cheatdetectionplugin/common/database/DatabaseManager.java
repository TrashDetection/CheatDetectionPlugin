package net.goldtreeservers.cheatdetectionplugin.common.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.Getter;
import net.goldtreeservers.cheatdetectionplugin.common.AbstractCheatDetectionPlugin;

public class DatabaseManager
{
	@Getter private final AbstractCheatDetectionPlugin plugin;
	
	@Getter private Connection connection;
	
	public DatabaseManager(AbstractCheatDetectionPlugin plugin)
	{
		this.plugin = plugin;
	}

	public void loadDatabase() throws SQLException, ClassNotFoundException
	{
		if (this.plugin.sqliteLocation() == null)
		{
			return;
		}
		
		if (this.plugin.sqliteLocation().getParentFile() != null)
		{
			this.plugin.sqliteLocation().getParentFile().mkdirs();
		}
		
		this.loadDatabase("jdbc:sqlite:" + plugin.sqliteLocation().getAbsolutePath());
	}
	
	public void loadDatabase(String connectionString) throws SQLException, ClassNotFoundException
	{
		Class.forName("org.sqlite.JDBC");
		
		this.connection = DriverManager.getConnection(connectionString);
		try (Statement statement = this.connection.createStatement())
		{
			statement.execute("CREATE TABLE IF NOT EXISTS `users` (`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `uuid` BLOB NOT NULL, CONSTRAINT `uuid` UNIQUE (`uuid` ASC) ON CONFLICT IGNORE)");
			statement.execute("CREATE TABLE IF NOT EXISTS `bans` (`id` INTEGER NOT NULL, `user_id` INTEGER NOT NULL, PRIMARY KEY (`id`) ON CONFLICT IGNORE)");
		}
	}
	
	public void closeDatabase() throws SQLException
	{
		if (this.connection != null)
		{
			this.connection.close();
		}
	}
}
