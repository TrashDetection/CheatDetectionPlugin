package net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing;

import io.netty.buffer.ByteBuf;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.OutgoingPacket;

public class PongOutgoingPacket implements OutgoingPacket
{
	@Override
	public void write(ByteBuf out)
	{
	}
}
