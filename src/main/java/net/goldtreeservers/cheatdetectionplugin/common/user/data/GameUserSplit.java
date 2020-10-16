package net.goldtreeservers.cheatdetectionplugin.common.user.data;

import lombok.Getter;
import lombok.Setter;
import net.goldtreeservers.cheatdetectionplugin.common.user.GameUser;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ChunkedByteArray;

public class GameUserSplit
{
	@Getter private final GameUser gameUser;

	@Getter private final int version;
	
	@Getter private long startedOn;
	@Getter private long endedOn;
	
	@Getter private ChunkedByteArray incomingBytes;
	@Getter private ChunkedByteArray outgoingBytes;
	
	@Getter @Setter private boolean incomplete;
	
	public GameUserSplit(GameUser gameUser, int version, long startedOn, ChunkedByteArray outgoingBytes)
	{
		this.gameUser = gameUser;
		
		this.version = version;
		
		this.startedOn = startedOn;
		
		this.outgoingBytes = outgoingBytes;
	}
	
	public void done(long endedOn, ChunkedByteArray incomingBytes)
	{
		this.endedOn = endedOn;
		
		this.incomingBytes = incomingBytes;
	}
}
