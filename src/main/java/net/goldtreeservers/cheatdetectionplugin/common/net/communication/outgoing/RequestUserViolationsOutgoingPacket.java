package net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing;

import io.netty.buffer.ByteBuf;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.OutgoingPacket;

public class RequestUserViolationsOutgoingPacket implements OutgoingPacket
{
	private int sessionId;
	
	public RequestUserViolationsOutgoingPacket(int sessionId)
	{
		this.sessionId = sessionId;
	}
	
	@Override
	public void write(ByteBuf out)
	{
		out.writeInt(this.sessionId);
	}
}
