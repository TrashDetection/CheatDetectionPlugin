package net.goldtreeservers.cheatdetectionplugin.common.user.violations.player.combat;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.goldtreeservers.cheatdetectionplugin.common.user.data.Location;
import net.goldtreeservers.cheatdetectionplugin.common.user.violations.UserViolation;

public class ReachViolation extends UserViolation
{
	@Getter private final int entityId;
	
	@Getter private final Location playerLocation;
	@Getter private final Location targetLocation;
	
	@Getter private final double reach;

	public ReachViolation(int entityId, Location playerLocation, Location targetLocation, double reach)
	{
		this.entityId = entityId;
		
		this.playerLocation = playerLocation;
		this.targetLocation = targetLocation;
		
		this.reach = reach;
	}
	
	@Override
	public String toString()
	{
		return String.format("Entity Id: %d | Player location: %s | Target location: %s | Reach: %f", this.entityId, this.playerLocation.toString(), this.targetLocation.toString(), this.reach);
	}

	public static ReachViolation serialize(ByteBuf out)
	{
		int entityId = out.readInt();
		
		Location playerLocation = Location.serialize(out);
		Location targetLocation = Location.serialize(out);
		
		double reach = out.readDouble();
		
		return new ReachViolation(entityId, playerLocation, targetLocation, reach);
	}
}
