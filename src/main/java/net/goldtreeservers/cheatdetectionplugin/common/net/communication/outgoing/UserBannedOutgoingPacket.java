package net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.OutgoingPacket;

@RequiredArgsConstructor
public class UserBannedOutgoingPacket implements OutgoingPacket
{
	@Getter private final int banId;
	
	@Override
	public void write(ByteBuf out)
	{
		out.writeInt(this.banId);
	}
}
