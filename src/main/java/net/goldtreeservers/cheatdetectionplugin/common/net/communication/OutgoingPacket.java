package net.goldtreeservers.cheatdetectionplugin.common.net.communication;

import io.netty.buffer.ByteBuf;

public interface OutgoingPacket
{
	public void write(ByteBuf out);
}
