package net.goldtreeservers.cheatdetectionplugin.bungee.net;

import io.netty.buffer.ByteBuf;
import lombok.Getter;

public class RawPacket
{
	@Getter private final ByteBuf buffer;
	
	public RawPacket(ByteBuf buffer)
	{
		this.buffer = buffer;
	}
}
