package net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.OutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ByteBufUtils;

@RequiredArgsConstructor
public class UserDataOutgoingPacket implements OutgoingPacket
{
	@Getter private final long incomingHash;
	@Getter private final long outgoingHash;
	
	@Getter private final int originalSize;
	@Getter private final ByteBuf compressedData;
	
	@Override
	public void write(ByteBuf out)
	{
		out.writeLong(this.incomingHash);
		out.writeLong(this.outgoingHash);
		
		ByteBufUtils.writeVarInt(out, this.originalSize);
		ByteBufUtils.writeVarInt(out, this.compressedData.readableBytes());
		
		out.writeBytes(this.compressedData);
	}
}
