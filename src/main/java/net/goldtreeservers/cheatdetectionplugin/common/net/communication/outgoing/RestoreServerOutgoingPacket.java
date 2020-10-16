package net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.OutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ByteBufUtils;

@RequiredArgsConstructor
public class RestoreServerOutgoingPacket implements OutgoingPacket
{
	@Getter private final UUID uniqueId;
	
	@Override
	public void write(ByteBuf out)
	{
		ByteBufUtils.writeUniqueId(out, this.uniqueId);
	}
}
