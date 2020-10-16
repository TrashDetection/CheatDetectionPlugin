package net.goldtreeservers.cheatdetectionplugin.bungee.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.Getter;
import net.goldtreeservers.cheatdetectionplugin.common.user.User;
import net.md_5.bungee.compress.PacketCompressor;

public class RawPacketEncoder extends MessageToByteEncoder<RawPacket>
{
	@Getter private final User user;
	
	public RawPacketEncoder(User user)
	{
		this.user = user;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, RawPacket msg, ByteBuf out) throws Exception
	{
		ByteBuf buf = msg.getBuffer();
		
		if (this.user.getGameUser() != null)
		{
			int readerIndex = buf.readerIndex();

			this.user.getGameUser().addOutgoingBytes(buf);
			
			buf.readerIndex(readerIndex);
		}
		
		PacketCompressor compressor = ctx.pipeline().get(PacketCompressor.class);
		if (compressor != null)
		{
			compressor.write(ctx, buf.retain(), ctx.voidPromise());
		}
		else
		{
			out.writeBytes(buf);
		}
	}
}
