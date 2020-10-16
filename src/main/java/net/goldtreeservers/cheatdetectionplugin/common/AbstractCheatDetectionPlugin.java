package net.goldtreeservers.cheatdetectionplugin.common;

import java.io.File;
import java.sql.SQLException;

import lombok.Getter;
import net.goldtreeservers.cheatdetectionplugin.common.ban.BansManager;
import net.goldtreeservers.cheatdetectionplugin.common.database.DatabaseManager;
import net.goldtreeservers.cheatdetectionplugin.common.net.NetworkManager;
import net.goldtreeservers.cheatdetectionplugin.common.user.GameUserManager;

public abstract class AbstractCheatDetectionPlugin
{
	@Getter private DatabaseManager databaseManager;
	
	@Getter private GameUserManager gameUserManager;
	@Getter private BansManager bansManager;
	
	@Getter private NetworkManager networkManager;
	
	public AbstractCheatDetectionPlugin()
	{
		this.databaseManager = new DatabaseManager(this);
		
		this.gameUserManager = new GameUserManager(this);
		this.bansManager = new BansManager(this);
		
		this.networkManager = new NetworkManager(this);
	}
	
	public void onLoad()
	{
		try
		{
			this.databaseManager.loadDatabase();
		}
		catch (SQLException | ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		
		this.networkManager.start();
		
		this.setupPlatform();
	}
	
	public abstract void setupPlatform();
	
	public abstract void shutdownPlatform();
	
	public abstract File sqliteLocation();
	
	public void onDisable()
	{
		this.shutdownPlatform();
		
		this.networkManager.stop();
	
		try
		{
			this.databaseManager.closeDatabase();
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
	}
}
