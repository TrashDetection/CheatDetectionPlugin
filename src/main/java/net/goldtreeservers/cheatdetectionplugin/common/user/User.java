package net.goldtreeservers.cheatdetectionplugin.common.user;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import net.goldtreeservers.cheatdetectionplugin.common.AbstractCheatDetectionPlugin;
import net.goldtreeservers.cheatdetectionplugin.common.protocol.Packet;
import net.goldtreeservers.cheatdetectionplugin.common.protocol.ProtocolState;
import net.goldtreeservers.cheatdetectionplugin.common.user.data.UserLoginData;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ByteBufUtils;
import net.goldtreeservers.cheatdetectionplugin.common.utils.Settings;

public class User
{
	private final AbstractCheatDetectionPlugin plugin;
	
	@Getter private ProtocolState protocolState;
	private Integer protocolVersion;
	
	private UserLoginData userLoginData;
	
	@Getter private GameUser gameUser;
	
	public User(AbstractCheatDetectionPlugin plugin)
	{
		this.plugin = plugin;
		
		this.protocolState = ProtocolState.HANDSHAKE;
		this.protocolVersion = null;
	}
	
	public void readIncomingBytes(ChannelHandlerContext ctx, ByteBuf msg)
	{
		switch(this.protocolState.ordinal()) //ProGuard is dump
		{
			case 3: //NOT_SUPPORTED
				return;
			case 0: //HANDSHAKE
			{
				int readerIndex = msg.readerIndex();
				
				int packetId = ByteBufUtils.readVarInt(msg);
				if (packetId == 0x00) //Handshake
				{
					this.protocolVersion = ByteBufUtils.readVarInt(msg);
					if (Packet.JOIN_PACKET.getClientboundId(this.protocolVersion) == null) //UNSUPPORTED PROTOCOL VERSION
					{
						this.protocolState = ProtocolState.NOT_SUPPORTED;
						return;
					}

					int hostLength = ByteBufUtils.readVarInt(msg);
					msg.skipBytes(hostLength + 2); //Skip host string bytes to avoid allocation, also skip the port which is unsigned short
					
					int requestProtocol = ByteBufUtils.readVarInt(msg);
					if (requestProtocol == 0x02) //Login
					{
						this.protocolState = ProtocolState.LOGIN;

						if (Settings.TRACK_LOGINS)
						{
							this.userLoginData = new UserLoginData();
						}
					}
					else
					{
						this.protocolState = ProtocolState.NOT_SUPPORTED;
						return;
					}
				}
				
				if (this.userLoginData != null)
				{
					msg.readerIndex(readerIndex);
					
					this.userLoginData.readIncomingBytes(ctx, msg);
				}
			}
			break;
			case 1: //LOGIN
			{
				if (this.userLoginData != null)
				{
					this.userLoginData.readIncomingBytes(ctx, msg);
				}
			}
			break;
			case 2: //GAME
			{
				this.gameUser.readIncomingBytes(ctx, msg);
			}
			break;
		}
	}
	
	public void readOutgoingBytes(ChannelHandlerContext ctx, ByteBuf msg)
	{
		switch(this.protocolState.ordinal()) //ProGuard is dump
		{
			case 3: //NOT_SUPPORTED
			case 0: //HANDSHAKE
				return;
			case 1: //LOGIN
			{
				int readerIndex = msg.readerIndex();
				
				int packetId = ByteBufUtils.readVarInt(msg);
				if (packetId == 0x02) //Login success
				{
					this.sendLoginTrackData();
					
					UUID uuid = UUID.fromString(ByteBufUtils.readString(msg));
					
					this.protocolState = ProtocolState.GAME;
					this.gameUser = this.plugin.getGameUserManager().createUser(this, uuid);
				}
				else
				{
					if (this.userLoginData != null)
					{
						msg.readerIndex(readerIndex);

						this.userLoginData.readOutgoingBytes(ctx, msg);
					}
				}
			}
			break;
			case 2: //GAME
			{
				this.gameUser.readOutgoingBytes(ctx, msg);
			}
			break;
		}
	}
	
	public void abort(String reason)
	{
		this.abort();
		
		System.out.println("Aborted cheat detection logging for user for reason: " + reason);
	}

	public void abort(Throwable e)
	{
		this.abort("Critical exception");
		
		e.printStackTrace();
	}
	
	public void abortSilence()
	{
		this.abort();
	}
	
	private void abort()
	{
		this.protocolState = ProtocolState.NOT_SUPPORTED;
		this.userLoginData = null;
		this.gameUser = null;
	}
	
	public void sendLoginTrackData()
	{
		//Send login track shit to the server
		
		this.userLoginData = null;
	}
	
	public boolean hasProtocolVersion()
	{
		return this.protocolVersion != null;
	}
	
	public int getProtocolVersion()
	{
		if (!this.hasProtocolVersion())
		{
			throw new IllegalStateException("Protocol version is unknown yet");
		}
		
		return this.protocolVersion;
	}

	public void disconnected()
	{
		if (this.gameUser != null)
		{
			this.plugin.getGameUserManager().disconnected(this.gameUser);
		}
	}
}
