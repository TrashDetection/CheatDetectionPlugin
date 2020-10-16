package net.goldtreeservers.cheatdetectionplugin.common.user.data;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ChunkedByteArray;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ChunkedByteArrayUtils;

public class UserLoginData
{
	private ChunkedByteArray loginBytesIncoming;
	private ChunkedByteArray loginBytesOutgoing;
	
	public UserLoginData()
	{
		this.loginBytesIncoming = new ChunkedByteArray();
		this.loginBytesOutgoing = new ChunkedByteArray();
	}
	
	public void readIncomingBytes(ChannelHandlerContext ctx, ByteBuf in)
	{
		ChunkedByteArrayUtils.writeVarInt(this.loginBytesIncoming, in.readableBytes());
		
		this.loginBytesIncoming.write(in);
	}
	
	public void readOutgoingBytes(ChannelHandlerContext ctx, ByteBuf out)
	{
		ChunkedByteArrayUtils.writeVarInt(this.loginBytesOutgoing, out.readableBytes());
		
		this.loginBytesOutgoing.write(out);
	}
}
