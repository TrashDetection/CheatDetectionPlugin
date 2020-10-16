package net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.OutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ByteBufUtils;

@RequiredArgsConstructor
public class LoginOutgoingPacket implements OutgoingPacket
{
	@Getter private final String username;
	@Getter private final String password;
	
	@Override
	public void write(ByteBuf out)
	{
		ByteBufUtils.writeString(out, this.username);
		ByteBufUtils.writeString(out, this.password);
	}
}
