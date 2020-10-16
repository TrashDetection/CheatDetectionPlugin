package net.goldtreeservers.cheatdetectionplugin.common.user;

import java.nio.ByteBuffer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import net.goldtreeservers.cheatdetectionplugin.common.AbstractCheatDetectionPlugin;
import net.goldtreeservers.cheatdetectionplugin.common.user.data.GameUserSplit;

public class GameUserManager
{
	@Getter private final AbstractCheatDetectionPlugin plugin;
	
	private ConcurrentMap<Integer, GameUser> sessions;
	
	private AtomicInteger nextSessionId;

	private ConcurrentMap<UUID, Integer> userIdsByUuid;
	private ConcurrentMap<Integer, UUID> uuidsByUserId;
	
	private ConcurrentLinkedQueue<GameUserSplit> splitsToHandles;
	
	public GameUserManager(AbstractCheatDetectionPlugin plugin)
	{
		this.plugin = plugin;
		
		this.sessions = new ConcurrentHashMap<>();
		
		this.nextSessionId = new AtomicInteger(0);
		
		this.userIdsByUuid = new ConcurrentHashMap<>();
		this.uuidsByUserId = new ConcurrentHashMap<>();
		
		this.splitsToHandles = new ConcurrentLinkedQueue<>();
	}

	public GameUser createUser(User user, UUID uniqueId)
	{
		GameUser gameUser = new GameUser(this, user, this.getNextSessionId(), this.getUserId(uniqueId));
		
		this.sessions.put(gameUser.getSessionId(), gameUser);

		return gameUser;
	}
	
	public void addSplit(GameUserSplit split)
	{
		if (!this.splitsToHandles.offer(split))
		{
			throw new RuntimeException("Failed to add to splits");
		}
	}
	
	public void disconnected(GameUser gameUser)
	{
		gameUser = this.sessions.remove(gameUser.getSessionId());
		if (gameUser != null)
		{
			gameUser.disconnected();
		}
	}
	
	private int getNextSessionId()
	{
		return this.nextSessionId.getAndIncrement();
	}
	
	public int getUserId(UUID uniqueId)
	{
		return this.userIdsByUuid.computeIfAbsent(uniqueId, (k) ->
		{
			ByteBuffer buf = ByteBuffer.allocate(16);
			buf.putLong(k.getMostSignificantBits());
			buf.putLong(k.getLeastSignificantBits());

			try(PreparedStatement preparedStatement = this.plugin.getDatabaseManager().getConnection().prepareStatement("INSERT INTO users(uuid) VALUES(?)"))
			{
				preparedStatement.setBytes(1, buf.array());
				preparedStatement.execute();
			}
			catch (SQLException e)
			{
				throw new RuntimeException(e);
			}
			
			try(PreparedStatement preparedStatement = this.plugin.getDatabaseManager().getConnection().prepareStatement("SELECT id FROM users WHERE uuid = ?"))
			{
				preparedStatement.setBytes(1, buf.array());
				
				try(ResultSet resultSet = preparedStatement.executeQuery())
				{
					if (resultSet.next())
					{
						int id = resultSet.getInt("id");

						this.uuidsByUserId.putIfAbsent(id, k);
						
						return id;
					}
				}
			}
			catch (SQLException e)
			{
				throw new RuntimeException(e);
			}
			
			throw new RuntimeException("Okay, something went totally wrong");
		});
	}
	
	public UUID getUniqueId(int userId)
	{
		return this.uuidsByUserId.computeIfAbsent(userId, (k) ->
		{
			try(PreparedStatement preparedStatement = this.plugin.getDatabaseManager().getConnection().prepareStatement("SELECT uuid FROM users WHERE id = ?"))
			{
				preparedStatement.setInt(1, k);
				
				try(ResultSet resultSet = preparedStatement.executeQuery())
				{
					if (resultSet.next())
					{
						ByteBuffer buffer = ByteBuffer.wrap(resultSet.getBytes("uuid"));
						
						UUID uniqueId = new UUID(buffer.getLong(), buffer.getLong());

						this.userIdsByUuid.putIfAbsent(uniqueId, k);
						
						return uniqueId;
					}
				}
			}
			catch (SQLException e)
			{
				throw new RuntimeException(e);
			}
			
			return null;
		});
	}
	
	public GameUser getUser(UUID uniqueId)
	{
		int userId = this.getUserId(uniqueId);
		
		return this.sessions.values().stream().filter((u) -> u.getUserId() == userId).findFirst().orElse(null);
	}
	
	public GameUserSplit getNextSplit()
	{
		return this.splitsToHandles.poll();
	}
}
