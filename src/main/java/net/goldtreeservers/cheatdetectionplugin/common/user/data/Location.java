package net.goldtreeservers.cheatdetectionplugin.common.user.data;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Location
{
	@Getter private final double x;
	@Getter private final double y;
	@Getter private final double z;
	
	@Getter private final float yaw;
	@Getter private final float pitch;
	
	@Override
	public String toString()
	{
		return String.format("Location[X: %f, Y: %f, Z: %f, Yaw: %f, Pitch: %f]", this.x, this.y, this.z, this.yaw, this.pitch);
	}
	
	public static Location serialize(ByteBuf out)
	{
		double x = out.readDouble();
		double y = out.readDouble();
		double z = out.readDouble();
		
		float yaw = out.readFloat();
		float pitch = out.readFloat();
		
		return new Location(x, y, z, yaw, pitch);
	}
}