package net.goldtreeservers.cheatdetectionplugin.common.net.communication;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.incoming.BanUserIncomingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.incoming.DisconnectIncomingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.incoming.PingIncomingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.incoming.ServerRegisteredIncomingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.incoming.SessionRestoreFailedIncomingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.incoming.UserViolationsIncomingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.DisconnectOutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.LoginOutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.PongOutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.RegisterServerOutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.RequestUserViolationsOutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.RestoreServerOutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.UserBannedOutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.net.communication.outgoing.UserDataOutgoingPacket;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ByteBufUtils;

public class PacketManager
{
	private Map<Integer, Class<? extends IncomingPacket>> incomingPackets;
	private Map<Class<? extends OutgoingPacket>, Integer> outgoingPackets;
	
	public PacketManager()
	{
		this.incomingPackets = new HashMap<>();
		this.outgoingPackets = new HashMap<>();
		
		this.addIncomingPackets();
		this.addOutgoingPackets();
	}
	
	private void addIncomingPackets()
	{
		this.incomingPackets.put(0, ServerRegisteredIncomingPacket.class);
		this.incomingPackets.put(1, PingIncomingPacket.class);
		this.incomingPackets.put(2, DisconnectIncomingPacket.class);
		this.incomingPackets.put(3, SessionRestoreFailedIncomingPacket.class);
		this.incomingPackets.put(4, UserViolationsIncomingPacket.class);
		this.incomingPackets.put(5, BanUserIncomingPacket.class);
	}
	
	private void addOutgoingPackets()
	{
		this.outgoingPackets.put(LoginOutgoingPacket.class, 0);
		this.outgoingPackets.put(RegisterServerOutgoingPacket.class, 1);
		this.outgoingPackets.put(RestoreServerOutgoingPacket.class, 2);
		this.outgoingPackets.put(PongOutgoingPacket.class, 3);
		this.outgoingPackets.put(DisconnectOutgoingPacket.class, 4);
		this.outgoingPackets.put(UserDataOutgoingPacket.class, 5);
		this.outgoingPackets.put(RequestUserViolationsOutgoingPacket.class, 6);
		this.outgoingPackets.put(UserBannedOutgoingPacket.class, 7);
	}
	
	public IncomingPacket readIncomingPacket(ByteBuf in)
	{
		int packetId = ByteBufUtils.readVarInt(in);
		
		Class<? extends IncomingPacket> clazz = this.incomingPackets.get(packetId);
		if (clazz != null)
		{
			try
			{
				IncomingPacket packet = clazz.newInstance();
				packet.read(in);
				
				return packet;
			} 
			catch (InstantiationException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public ByteBuf writeOutgoingPacket(OutgoingPacket packet)
	{
		ByteBuf out = Unpooled.buffer();
		
		this.writeOutgoingPacket(packet, out);
		
		return out;
	}
	
	public void writeOutgoingPacket(OutgoingPacket packet, ByteBuf out)
	{
		Integer packetId = this.outgoingPackets.get(packet.getClass());
		if (packetId == null)
		{
			throw new RuntimeException("Packet id not found");
		}
		
		ByteBufUtils.writeVarInt(out, packetId);
		
		packet.write(out);
	}
}
