package net.goldtreeservers.cheatdetectionplugin.common.user.violations.player.combat;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.goldtreeservers.cheatdetectionplugin.common.user.data.Location;
import net.goldtreeservers.cheatdetectionplugin.common.user.violations.UserViolation;

public class NotFacingAttackingEntityViolation extends UserViolation
{
	@Getter private final int entityId;
	
	@Getter private final Location playerLocation;
	@Getter private final Location targetLocation;

	public NotFacingAttackingEntityViolation(int entityId, Location playerLocation, Location targetLocation)
	{
		this.entityId = entityId;
		
		this.playerLocation = playerLocation;
		this.targetLocation = targetLocation;
	}
	
	@Override
	public String toString()
	{
		return String.format("Not facing attacking entity | Entity Id: %d | Player location: %s | Target location: %s", this.entityId, this.playerLocation.toString(), this.targetLocation.toString());
	}

	public static NotFacingAttackingEntityViolation serialize(ByteBuf out)
	{
		int entityId = out.readInt();
		
		Location playerLocation = Location.serialize(out);
		Location targetLocation = Location.serialize(out);
		
		return new NotFacingAttackingEntityViolation(entityId, playerLocation, targetLocation);
	}
}
