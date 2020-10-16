package net.goldtreeservers.cheatdetectionplugin.common.protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public enum Packet
{
	KEEP_ALIVE
	{
		{
			this.addIdByProtocolVersionServerbound(1, 0x00);
			this.addIdByProtocolVersionServerbound(67, 0x0A);
			this.addIdByProtocolVersionServerbound(80, 0x0B);
			this.addIdByProtocolVersionServerbound(318, 0x0C);
			this.addIdByProtocolVersionServerbound(332, 0x0C);
			this.addIdByProtocolVersionServerbound(338, 0x0B); //1.12.1
			this.addIdByProtocolVersionServerbound(340, 0x0B); //1.12.2
			this.addIdByProtocolVersionServerbound(393, 0x0E); //1.13
			this.addIdByProtocolVersionServerbound(401, 0x0E); //1.13.1
			this.addIdByProtocolVersionServerbound(404, 0x0E); //1.13.2
			
			this.addIdByProtocolVersionClientbound(1, 0x00);
			this.addIdByProtocolVersionClientbound(67, 0x1F);
			this.addIdByProtocolVersionClientbound(80, 0x20);
			this.addIdByProtocolVersionClientbound(86, 0x1F);
			this.addIdByProtocolVersionClientbound(318, 0x20);
			this.addIdByProtocolVersionClientbound(332, 0x1F);
			this.addIdByProtocolVersionClientbound(338, 0x1F); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x1F); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x21); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x21); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x21); //1.13.2
			
			this.packetsDefined();
		}
	},
	CHAT
	{
		{
			this.addIdByProtocolVersionServerbound(1, 0x01);
			this.addIdByProtocolVersionServerbound(67, 0x02);
			this.addIdByProtocolVersionServerbound(318, 0x03);
			this.addIdByProtocolVersionServerbound(336, 0x02);
			this.addIdByProtocolVersionServerbound(338, 0x02); //1.12.1
			this.addIdByProtocolVersionServerbound(340, 0x02); //1.12.2
			this.addIdByProtocolVersionServerbound(393, 0x02); //1.13
			this.addIdByProtocolVersionServerbound(401, 0x02); //1.13.1
			this.addIdByProtocolVersionServerbound(404, 0x02); //1.13.2
			
			this.addIdByProtocolVersionClientbound(1, 0x02);
			this.addIdByProtocolVersionClientbound(67, 0x0F);
			this.addIdByProtocolVersionClientbound(318, 0x10);
			this.addIdByProtocolVersionClientbound(332, 0x0F);
			this.addIdByProtocolVersionClientbound(338, 0x0F); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x0F); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x0E); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x0E); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x0E); //1.13.2
			
			this.packetsDefined();
		}
	},
	TAB_COMPLETE
	{
		{
			this.addIdByProtocolVersionServerbound(1, 0x14);
			this.addIdByProtocolVersionServerbound(49, 0x15);
			this.addIdByProtocolVersionServerbound(59, 0x15);
			this.addIdByProtocolVersionServerbound(67, 0x00);
			this.addIdByProtocolVersionServerbound(80, 0x01);
			this.addIdByProtocolVersionServerbound(318, 0x02);
			this.addIdByProtocolVersionServerbound(338, 0x01); //1.12.1
			this.addIdByProtocolVersionServerbound(340, 0x01); //1.12.2
			this.addIdByProtocolVersionServerbound(393, 0x05); //1.13
			this.addIdByProtocolVersionServerbound(401, 0x05); //1.13.1
			this.addIdByProtocolVersionServerbound(404, 0x05); //1.13.2
			
			this.addIdByProtocolVersionClientbound(1, 0x3A);
			this.addIdByProtocolVersionClientbound(67, 0x3F);
			this.addIdByProtocolVersionClientbound(318, 0x0F);
			this.addIdByProtocolVersionClientbound(332, 0x0E);
			this.addIdByProtocolVersionClientbound(336, 0x01);
			this.addIdByProtocolVersionClientbound(338, 0x0E); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x0E); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x10); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x10); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x10); //1.13.2
			
			this.packetsDefined();
		}
	},
	PLUGIN_MESSAGE
	{
		{
			this.addIdByProtocolVersionServerbound(1, 0x17);
			this.addIdByProtocolVersionServerbound(49, 0x18);
			this.addIdByProtocolVersionServerbound(67, 0x08);
			this.addIdByProtocolVersionServerbound(80, 0x09);
			this.addIdByProtocolVersionServerbound(318, 0x0A);
			this.addIdByProtocolVersionServerbound(336, 0x09);
			this.addIdByProtocolVersionServerbound(338, 0x09); //1.12.1
			this.addIdByProtocolVersionServerbound(340, 0x09); //1.12.2
			this.addIdByProtocolVersionServerbound(393, 0x0A); //1.13
			this.addIdByProtocolVersionServerbound(401, 0x0A); //1.13.1
			this.addIdByProtocolVersionServerbound(404, 0x0A); //1.13.2
			
			this.addIdByProtocolVersionClientbound(1, 0x3F);
			this.addIdByProtocolVersionClientbound(67, 0x18);
			this.addIdByProtocolVersionClientbound(318, 0x19);
			this.addIdByProtocolVersionClientbound(332, 0x18);
			this.addIdByProtocolVersionClientbound(338, 0x18); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x18); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x19); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x19); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x19); //1.13.2
			
			this.packetsDefined();
		}
	},
	PLAYER_LIST_ITEM
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x38);
			this.addIdByProtocolVersionClientbound(67, 0x2D);
			this.addIdByProtocolVersionClientbound(80, 0x2E);
			this.addIdByProtocolVersionClientbound(86, 0x38);
			this.addIdByProtocolVersionClientbound(318, 0x2E);
			this.addIdByProtocolVersionClientbound(332, 0x2D);
			this.addIdByProtocolVersionClientbound(336, 0x2E);
			this.addIdByProtocolVersionClientbound(338, 0x2E); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x2E); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x30); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x30); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x30); //1.13.2
			
			this.packetsDefined();
		}
	},
	SCOREBOARD_OBJECTIVE
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x3B);
			this.addIdByProtocolVersionClientbound(67, 0x3F);
			this.addIdByProtocolVersionClientbound(77, 0x40);
			this.addIdByProtocolVersionClientbound(86, 0x3F);
			this.addIdByProtocolVersionClientbound(318, 0x41);
			this.addIdByProtocolVersionClientbound(336, 0x42);
			this.addIdByProtocolVersionClientbound(338, 0x42); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x42); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x45); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x45); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x45); //1.13.2
			
			this.packetsDefined();
		}
	},
	UPDATE_SCORE
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x3C);
			this.addIdByProtocolVersionClientbound(67, 0x41);
			this.addIdByProtocolVersionClientbound(77, 0x42);
			this.addIdByProtocolVersionClientbound(80, 0x43);
			this.addIdByProtocolVersionClientbound(86, 0x42);
			this.addIdByProtocolVersionClientbound(318, 0x44);
			this.addIdByProtocolVersionClientbound(336, 0x45);
			this.addIdByProtocolVersionClientbound(338, 0x45); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x45); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x48); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x48); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x48); //1.13.2
			
			this.packetsDefined();
		}
	},
	DISPLAY_SCOREBOARD
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x3D);
			this.addIdByProtocolVersionClientbound(67, 0x38);
			this.addIdByProtocolVersionClientbound(80, 0x39);
			this.addIdByProtocolVersionClientbound(86, 0x38);
			this.addIdByProtocolVersionClientbound(318, 0x3A);
			this.addIdByProtocolVersionClientbound(336, 0x3B);
			this.addIdByProtocolVersionClientbound(338, 0x3B); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x3B); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x3E); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x3E); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x3E); //1.13.2
			
			this.packetsDefined();
		}
	},
	TEAM
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x3E);
			this.addIdByProtocolVersionClientbound(67, 0x40);
			this.addIdByProtocolVersionClientbound(77, 0x41);
			this.addIdByProtocolVersionClientbound(80, 0x42);
			this.addIdByProtocolVersionClientbound(86, 0x41);
			this.addIdByProtocolVersionClientbound(318, 0x43);
			this.addIdByProtocolVersionClientbound(336, 0x44);
			this.addIdByProtocolVersionClientbound(338, 0x44); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x44); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x47); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x47); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x47); //1.13.2
			
			this.packetsDefined();
		}
	},
	DISCONNECT
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x40);
			this.addIdByProtocolVersionClientbound(67, 0x19);
			this.addIdByProtocolVersionClientbound(80, 0x1A);
			this.addIdByProtocolVersionClientbound(318, 0x1B);
			this.addIdByProtocolVersionClientbound(332, 0x1A);
			this.addIdByProtocolVersionClientbound(338, 0x1A); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x1A); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x1B); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x1B); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x1B); //1.13.2
			
			this.packetsDefined();
		}
	},
	TITLE
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x45);
			this.addIdByProtocolVersionClientbound(67, 0x44);
			this.addIdByProtocolVersionClientbound(77, 0x45);
			this.addIdByProtocolVersionClientbound(80, 0x46);
			this.addIdByProtocolVersionClientbound(86, 0x45);
			this.addIdByProtocolVersionClientbound(318, 0x47);
			this.addIdByProtocolVersionClientbound(336, 0x48);
			this.addIdByProtocolVersionClientbound(338, 0x48); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x48); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x4B); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x4B); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x4B); //1.13.2
			
			this.packetsDefined();
		}
	},
	PLAYER_LIST_HEADER_AND_FOOTER
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x47);
			this.addIdByProtocolVersionClientbound(67, 0x46);
			this.addIdByProtocolVersionClientbound(77, 0x47);
			this.addIdByProtocolVersionClientbound(80, 0x49);
			this.addIdByProtocolVersionClientbound(86, 0x48);
			this.addIdByProtocolVersionClientbound(110, 0x47);
			this.addIdByProtocolVersionClientbound(318, 0x49);
			this.addIdByProtocolVersionClientbound(336, 0x4A);
			this.addIdByProtocolVersionClientbound(338, 0x4A); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x4A); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x4E); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x4E); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x4E); //1.13.2
			
			this.packetsDefined();
		}
	},
	STATISTIC
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x37);
			this.addIdByProtocolVersionClientbound(67, 0x07);
			this.addIdByProtocolVersionClientbound(338, 0x07); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x07); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x07); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x07); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x07); //1.13.2
			
			this.packetsDefined();
		}
	},
	TIME_UPDATE
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x03);
			this.addIdByProtocolVersionClientbound(67, 0x43);
			this.addIdByProtocolVersionClientbound(77, 0x44);
			this.addIdByProtocolVersionClientbound(80, 0x45);
			this.addIdByProtocolVersionClientbound(86, 0x44);
			this.addIdByProtocolVersionClientbound(318, 0x46);
			this.addIdByProtocolVersionClientbound(336, 0x47);
			this.addIdByProtocolVersionClientbound(338, 0x47); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x47); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x4A); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x4A); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x4A); //1.13.2
			
			this.packetsDefined();
		}
	},
	NAMED_SOUND_EFFECT
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x29);
			this.addIdByProtocolVersionClientbound(67, 0x23);
			this.addIdByProtocolVersionClientbound(80, 0x19);
			this.addIdByProtocolVersionClientbound(318, 0x1A);
			this.addIdByProtocolVersionClientbound(332, 0x19);
			this.addIdByProtocolVersionClientbound(338, 0x19); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x19); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x1A); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x1A); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x1A); //1.13.2
			
			this.packetsDefined();
		}
	},
	COLLECT_ITEM
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x0D);
			this.addIdByProtocolVersionClientbound(67, 0x47);
			this.addIdByProtocolVersionClientbound(77, 0x4A);
			this.addIdByProtocolVersionClientbound(80, 0x48);
			this.addIdByProtocolVersionClientbound(86, 0x49);
			this.addIdByProtocolVersionClientbound(110, 0x48);
			this.addIdByProtocolVersionClientbound(318, 0x4A);
			this.addIdByProtocolVersionClientbound(336, 0x4B);
			this.addIdByProtocolVersionClientbound(338, 0x4B); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x4B); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x4F); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x4F); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x4F); //1.13.2
			
			this.packetsDefined();
		}
	},
	EFFECT
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x28);
			this.addIdByProtocolVersionClientbound(67, 0x21);
			this.addIdByProtocolVersionClientbound(80, 0x22);
			this.addIdByProtocolVersionClientbound(86, 0x21);
			this.addIdByProtocolVersionClientbound(110, 0x48);
			this.addIdByProtocolVersionClientbound(318, 0x22);
			this.addIdByProtocolVersionClientbound(332, 0x21);
			this.addIdByProtocolVersionClientbound(338, 0x21); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x21); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x23); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x23); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x23); //1.13.2
			
			this.packetsDefined();
		}
	},
	PARTICLE
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x2A);
			this.addIdByProtocolVersionClientbound(67, 0x22);
			this.addIdByProtocolVersionClientbound(80, 0x23);
			this.addIdByProtocolVersionClientbound(86, 0x22);
			this.addIdByProtocolVersionClientbound(318, 0x23);
			this.addIdByProtocolVersionClientbound(332, 0x22);
			this.addIdByProtocolVersionClientbound(338, 0x22); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x22); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x24); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x24); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x24); //1.13.2
			
			this.packetsDefined();
		}
	},
	MAP
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x34);
			this.addIdByProtocolVersionClientbound(67, 0x25);
			this.addIdByProtocolVersionClientbound(86, 0x24);
			this.addIdByProtocolVersionClientbound(338, 0x24); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x24); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x26); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x26); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x26); //1.13.2
			
			this.packetsDefined();
		}
	},
	RESOURCE_PACK_SEND
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x48);
			this.addIdByProtocolVersionClientbound(67, 0x32);
			this.addIdByProtocolVersionClientbound(80, 0x33);
			this.addIdByProtocolVersionClientbound(86, 0x32);
			this.addIdByProtocolVersionClientbound(318, 0x34);
			this.addIdByProtocolVersionClientbound(332, 0x33);
			this.addIdByProtocolVersionClientbound(336, 0x34);
			this.addIdByProtocolVersionClientbound(338, 0x34); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x34); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x37); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x37); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x37); //1.13.2
			
			this.packetsDefined();
		}
	},
	BOSS_BAR
	{
		{
			this.addIdByProtocolVersionClientbound(49, 0x49);
			this.addIdByProtocolVersionClientbound(67, 0x0C);
			this.addIdByProtocolVersionClientbound(318, 0x0D);
			this.addIdByProtocolVersionClientbound(332, 0x0C);
			this.addIdByProtocolVersionClientbound(338, 0x0C); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x0C); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x0C); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x0C); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x0C); //1.13.2
			
			this.packetsDefined();
		}
	},
	SOUND_EFFECT
	{
		{
			this.addIdByProtocolVersionClientbound(80, 0x48);
			this.addIdByProtocolVersionClientbound(86, 0x47);
			this.addIdByProtocolVersionClientbound(110, 0x46);
			this.addIdByProtocolVersionClientbound(318, 0x48);
			this.addIdByProtocolVersionClientbound(336, 0x49);
			this.addIdByProtocolVersionClientbound(338, 0x49); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x49); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x4D); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x4D); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x4D); //1.13.2
			
			this.packetsDefined();
		}
	},
	ADVANCEMENTS
	{
		{
			this.addIdByProtocolVersionClientbound(318, 0x08);
			this.addIdByProtocolVersionClientbound(332, 0x4C);
			this.addIdByProtocolVersionClientbound(336, 0x4D);
			this.addIdByProtocolVersionClientbound(338, 0x4D); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x4D); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x51); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x51); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x51); //1.13.2
			
			this.packetsDefined();
		}
	},
	SELECT_ADVANCEMENT_TAB
	{
		{
			this.addIdByProtocolVersionClientbound(330, 0x20);
			this.addIdByProtocolVersionClientbound(332, 0x19);
			this.addIdByProtocolVersionClientbound(336, 0x37);
			this.addIdByProtocolVersionClientbound(338, 0x37); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x37); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x3A); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x3A); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x3A); //1.13.2
			
			this.packetsDefined();
		}
	},
	ADVANCEMENT_PROGRESS
	{
		{
			this.addIdByProtocolVersionClientbound(330, 0x4E);
			this.addIdByProtocolVersionClientbound(332, 0x36);
			this.addIdByProtocolVersionClientbound(338, null); //1.12.1
			this.addIdByProtocolVersionClientbound(340, null); //1.12.2
			this.addIdByProtocolVersionClientbound(393, null); //1.13
			this.addIdByProtocolVersionClientbound(401, null); //1.13.1
			this.addIdByProtocolVersionClientbound(404, null); //1.13.2
			
			this.packetsDefined();
		}
	},
	CONFIRM_TRANSACTION
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x32);
			this.addIdByProtocolVersionClientbound(67, 0x11);
			this.addIdByProtocolVersionClientbound(318, 0x12);
			this.addIdByProtocolVersionClientbound(332, 0x11);
			this.addIdByProtocolVersionClientbound(338, 0x11); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x11); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x12); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x12); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x12); //1.13.2
			
			this.addIdByProtocolVersionServerbound(1, 0x0F);
			this.addIdByProtocolVersionServerbound(49, 0x10);
			this.addIdByProtocolVersionServerbound(67, 0x04);
			this.addIdByProtocolVersionServerbound(80, 0x05);
			this.addIdByProtocolVersionServerbound(318, 0x06);
			this.addIdByProtocolVersionServerbound(332, 0x06);
			this.addIdByProtocolVersionServerbound(336, 0x05);
			this.addIdByProtocolVersionServerbound(338, 0x05); //1.12.1
			this.addIdByProtocolVersionServerbound(340, 0x05); //1.12.2
			this.addIdByProtocolVersionServerbound(393, 0x06); //1.13
			this.addIdByProtocolVersionServerbound(401, 0x06); //1.13.1
			this.addIdByProtocolVersionServerbound(404, 0x06); //1.13.2
			
			this.packetsDefined();
		}
	},
	SPAWN_PLAYER
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x0C);
			this.addIdByProtocolVersionClientbound(67, 0x05);
			this.addIdByProtocolVersionClientbound(338, 0x05); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x05); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x05); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x05); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x05); //1.13.2
			
			this.packetsDefined();
		}
	},
	ENTITY
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x14);
			this.addIdByProtocolVersionClientbound(67, 0x25);
			this.addIdByProtocolVersionClientbound(86, 0x24);
			this.addIdByProtocolVersionClientbound(318, 0x25);
			this.addIdByProtocolVersionClientbound(332, 0x25);
			this.addIdByProtocolVersionClientbound(338, 0x25); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x25); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x27); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x27); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x27); //1.13.2
			
			this.packetsDefined();
		}
	},
	ENTITY_RELATIVE_MOVE
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x15);
			this.addIdByProtocolVersionClientbound(67, 0x26);
			this.addIdByProtocolVersionClientbound(86, 0x25);
			this.addIdByProtocolVersionClientbound(318, 0x26);
			this.addIdByProtocolVersionClientbound(332, 0x26);
			this.addIdByProtocolVersionClientbound(338, 0x26); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x26); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x28); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x28); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x28); //1.13.2
			
			this.packetsDefined();
		}
	},
	ENTITY_LOOK
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x16);
			this.addIdByProtocolVersionClientbound(67, 0x28);
			this.addIdByProtocolVersionClientbound(86, 0x27);
			this.addIdByProtocolVersionClientbound(318, 0x28);
			this.addIdByProtocolVersionClientbound(332, 0x28);
			this.addIdByProtocolVersionClientbound(338, 0x28); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x28); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x2A); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x2A); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x2A); //1.13.2
			
			this.packetsDefined();
		}
	},
	ENTITY_LOOK_AND_RELATIVE_MOVE
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x17);
			this.addIdByProtocolVersionClientbound(67, 0x27);
			this.addIdByProtocolVersionClientbound(86, 0x26);
			this.addIdByProtocolVersionClientbound(318, 0x27);
			this.addIdByProtocolVersionClientbound(332, 0x27);
			this.addIdByProtocolVersionClientbound(338, 0x27); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x27); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x29); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x29); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x29); //1.13.2
			
			this.packetsDefined();
		}
	},
	ENTITY_TELEPORT
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x18);
			this.addIdByProtocolVersionClientbound(67, 0x48);
			this.addIdByProtocolVersionClientbound(77, 0x49);
			this.addIdByProtocolVersionClientbound(80, 0x4B);
			this.addIdByProtocolVersionClientbound(86, 0x4A);
			this.addIdByProtocolVersionClientbound(110, 0x49);
			this.addIdByProtocolVersionClientbound(318, 0x4B);
			this.addIdByProtocolVersionClientbound(332, 0x4B);
			this.addIdByProtocolVersionClientbound(336, 0x4C);
			this.addIdByProtocolVersionClientbound(338, 0x4C); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x4C); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x50); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x50); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x50); //1.13.2
			
			this.packetsDefined();
		}
	},
	CHUNK_DATA
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x21);
			this.addIdByProtocolVersionClientbound(67, 0x20);
			this.addIdByProtocolVersionClientbound(80, 0x21);
			this.addIdByProtocolVersionClientbound(86, 0x20);
			this.addIdByProtocolVersionClientbound(318, 0x21);
			this.addIdByProtocolVersionClientbound(332, 0x20);
			this.addIdByProtocolVersionClientbound(338, 0x20); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x20); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x22); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x22); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x22); //1.13.2
			
			this.packetsDefined();
		}
	},
	MAP_CHUNK_BULK
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x26);
			this.addIdByProtocolVersionClientbound(62, null); //Removed
			
			this.packetsDefined();
		}
	},
	BLOCK_CHANGE
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x23);
			this.addIdByProtocolVersionClientbound(62, 0x24);
			this.addIdByProtocolVersionClientbound(67, 0x0B);
			this.addIdByProtocolVersionClientbound(318, 0x0C);
			this.addIdByProtocolVersionClientbound(332, 0x0B);
			this.addIdByProtocolVersionClientbound(338, 0x0B); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x0B); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x0B); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x0B); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x0B); //1.13.2
			
			this.packetsDefined();
		}
	},
	MULTI_BLOCK_CHANGE
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x22);
			this.addIdByProtocolVersionClientbound(62, 0x23);
			this.addIdByProtocolVersionClientbound(67, 0x10);
			this.addIdByProtocolVersionClientbound(318, 0x11);
			this.addIdByProtocolVersionClientbound(332, 0x10);
			this.addIdByProtocolVersionClientbound(338, 0x10); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x10); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x0F); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x0F); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x0F); //1.13.2
			
			this.packetsDefined();
		}
	},
	UPDATE_BLOCK_ENTITY
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x35);
			this.addIdByProtocolVersionClientbound(67, 0x09);
			this.addIdByProtocolVersionClientbound(318, 0x0A);
			this.addIdByProtocolVersionClientbound(332, 0x09);
			this.addIdByProtocolVersionClientbound(338, 0x09); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x09); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x09); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x09); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x09); //1.13.2
			
			this.packetsDefined();
		}
	},
	WINDOW_ITEMS
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x30);
			this.addIdByProtocolVersionClientbound(67, 0x14);
			this.addIdByProtocolVersionClientbound(318, 0x15);
			this.addIdByProtocolVersionClientbound(332, 0x14);
			this.addIdByProtocolVersionClientbound(338, 0x14); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x14); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x15); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x15); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x15); //1.13.2
			
			this.packetsDefined();
		}
	},
	SET_SLOT
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x2F);
			this.addIdByProtocolVersionClientbound(67, 0x16);
			this.addIdByProtocolVersionClientbound(318, 0x17);
			this.addIdByProtocolVersionClientbound(332, 0x16);
			this.addIdByProtocolVersionClientbound(338, 0x16); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x16); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x17); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x17); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x17); //1.13.2
			
			this.packetsDefined();
		}
	},
	ENTITY_EQUIPMENT
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x04);
			this.addIdByProtocolVersionClientbound(67, 0x3C);
			this.addIdByProtocolVersionClientbound(77, 0x3D);
			this.addIdByProtocolVersionClientbound(86, 0x3C);
			this.addIdByProtocolVersionClientbound(318, 0x3F);
			this.addIdByProtocolVersionClientbound(332, 0x3E);
			this.addIdByProtocolVersionClientbound(338, 0x3F); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x3F); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x42); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x42); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x42); //1.13.2
			
			this.packetsDefined();
		}
	},
	CLICK_WINDOW
	{
		{
			this.addIdByProtocolVersionServerbound(1, 0x0E);
			this.addIdByProtocolVersionServerbound(49, 0x0F);
			this.addIdByProtocolVersionServerbound(67, 0x06);
			this.addIdByProtocolVersionServerbound(80, 0x07);
			this.addIdByProtocolVersionServerbound(318, 0x08);
			this.addIdByProtocolVersionServerbound(332, 0x08);
			this.addIdByProtocolVersionServerbound(338, 0x07); //1.12.1
			this.addIdByProtocolVersionServerbound(340, 0x07); //1.12.2
			this.addIdByProtocolVersionServerbound(393, 0x08); //1.13
			this.addIdByProtocolVersionServerbound(401, 0x08); //1.13.1
			this.addIdByProtocolVersionServerbound(404, 0x08); //1.13.2
			
			this.packetsDefined();
		}
	},
	CREATE_INVENTORY_ACTION
	{
		{
			this.addIdByProtocolVersionServerbound(1, 0x10);
			this.addIdByProtocolVersionServerbound(49, 0x11);
			this.addIdByProtocolVersionServerbound(67, 0x15);
			this.addIdByProtocolVersionServerbound(77, 0x16);
			this.addIdByProtocolVersionServerbound(80, 0x18);
			this.addIdByProtocolVersionServerbound(318, 0x1A);
			this.addIdByProtocolVersionServerbound(332, 0x1B);
			this.addIdByProtocolVersionServerbound(338, 0x1B); //1.12.1
			this.addIdByProtocolVersionServerbound(340, 0x1B); //1.12.2
			this.addIdByProtocolVersionServerbound(393, 0x24); //1.13
			this.addIdByProtocolVersionServerbound(401, 0x24); //1.13.1
			this.addIdByProtocolVersionServerbound(404, 0x24); //1.13.2
			
			this.packetsDefined();
		}
	},
	PLAYER_BLOCK_PLACEMENT
	{
		{
			this.addIdByProtocolVersionServerbound(1, 0x08);
			this.addIdByProtocolVersionServerbound(49, 0x09);
			this.addIdByProtocolVersionServerbound(67, 0x19);
			this.addIdByProtocolVersionServerbound(77, 0x1A);
			this.addIdByProtocolVersionServerbound(80, 0x1C);
			this.addIdByProtocolVersionServerbound(318, 0x1E);
			this.addIdByProtocolVersionServerbound(332, 0x1F);
			this.addIdByProtocolVersionServerbound(338, 0x1F); //1.12.1
			this.addIdByProtocolVersionServerbound(340, 0x1F); //1.12.2
			this.addIdByProtocolVersionServerbound(393, 0x29); //1.13
			this.addIdByProtocolVersionServerbound(401, 0x29); //1.13.1
			this.addIdByProtocolVersionServerbound(404, 0x29); //1.13.2
			
			this.packetsDefined();
		}
	},
	RESPAWN
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x07);
			this.addIdByProtocolVersionClientbound(67, 0x33);
			this.addIdByProtocolVersionClientbound(80, 0x34);
			this.addIdByProtocolVersionClientbound(86, 0x33);
			this.addIdByProtocolVersionClientbound(318, 0x35);
			this.addIdByProtocolVersionClientbound(332, 0x34);
			this.addIdByProtocolVersionClientbound(338, 0x35); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x35); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x38); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x38); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x38); //1.13.2
			
			this.packetsDefined();
		}
	},
	PLAYER_POSITION_AND_LOOK
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x08);
			this.addIdByProtocolVersionClientbound(67, 0x2E);
			this.addIdByProtocolVersionClientbound(80, 0x2F);
			this.addIdByProtocolVersionClientbound(86, 0x2E);
			this.addIdByProtocolVersionClientbound(318, 0x2F);
			this.addIdByProtocolVersionClientbound(332, 0x0F);
			this.addIdByProtocolVersionClientbound(338, 0x0E); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x0E); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x11); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x11); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x11); //1.13.2
			
			this.packetsDefined();
		}
	},
	UPDATE_SIGN
	{
		{
			this.addIdByProtocolVersionServerbound(1, 0x12);
			this.addIdByProtocolVersionServerbound(49, 0x13);
			this.addIdByProtocolVersionServerbound(67, 0x16);
			this.addIdByProtocolVersionServerbound(77, 0x17);
			this.addIdByProtocolVersionServerbound(80, 0x19);
			this.addIdByProtocolVersionServerbound(318, 0x1B);
			this.addIdByProtocolVersionServerbound(332, 0x1C);
			this.addIdByProtocolVersionServerbound(338, 0x1C); //1.12.1
			this.addIdByProtocolVersionServerbound(340, 0x1C); //1.12.2
			this.addIdByProtocolVersionServerbound(393, 0x26); //1.13
			this.addIdByProtocolVersionServerbound(401, 0x26); //1.13.1
			this.addIdByProtocolVersionServerbound(404, 0x26); //1.13.2
			
			this.addIdByProtocolVersionClientbound(1, 0x33);
			this.addIdByProtocolVersionClientbound(67, 0x45);
			this.addIdByProtocolVersionClientbound(77, 0x46);
			this.addIdByProtocolVersionClientbound(80, 0x47);
			this.addIdByProtocolVersionClientbound(86, 0x46);
			this.addIdByProtocolVersionClientbound(110, null); //Removed
			
			this.packetsDefined();
		}
	},
	CHANGE_GAME_STATE
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x2B);
			this.addIdByProtocolVersionClientbound(67, 0x1E);
			this.addIdByProtocolVersionClientbound(86, 0x1E);
			this.addIdByProtocolVersionClientbound(318, 0x1F);
			this.addIdByProtocolVersionClientbound(332, 0x1E);
			this.addIdByProtocolVersionClientbound(338, 0x1E); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x1E); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x20); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x20); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x20); //1.13.2
			
			this.packetsDefined();
		}
	},
	DESTROY_ENTITIES
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x13);
			this.addIdByProtocolVersionClientbound(67, 0x30);
			this.addIdByProtocolVersionClientbound(80, 0x31);
			this.addIdByProtocolVersionClientbound(86, 0x30);
			this.addIdByProtocolVersionClientbound(318, 0x32);
			this.addIdByProtocolVersionClientbound(332, 0x31);
			this.addIdByProtocolVersionClientbound(338, 0x32); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x32); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x35); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x35); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x35); //1.13.2
			
			this.packetsDefined();
		}
	},
	JOIN_PACKET
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x01);
			this.addIdByProtocolVersionClientbound(67, 0x24);
			this.addIdByProtocolVersionClientbound(86, 0x23);
			this.addIdByProtocolVersionClientbound(318, 0x24);
			this.addIdByProtocolVersionClientbound(332, 0x23);
			this.addIdByProtocolVersionClientbound(338, 0x23); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x23); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x25); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x25); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x25); //1.13.2
			
			this.packetsDefined();
		}
	},
	OPEN_WINDOW
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x2D);
			this.addIdByProtocolVersionClientbound(67, 0x13);
			this.addIdByProtocolVersionClientbound(318, 0x14);
			this.addIdByProtocolVersionClientbound(332, 0x13);
			this.addIdByProtocolVersionClientbound(338, 0x13); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x13); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x14); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x14); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x14); //1.13.2
			
			this.packetsDefined();
		}
	},
	STATISTICS
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x37);
			this.addIdByProtocolVersionClientbound(67, 0x07);
			this.addIdByProtocolVersionClientbound(338, 0x07); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x07); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x07); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x07); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x07); //1.13.2
			
			this.packetsDefined();
		}
	},
	PLAYER_ABILITIES
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x39);
			this.addIdByProtocolVersionClientbound(67, 0x2B);
			this.addIdByProtocolVersionClientbound(80, 0x2C);
			this.addIdByProtocolVersionClientbound(86, 0x2B);
			this.addIdByProtocolVersionClientbound(318, 0x2C);
			this.addIdByProtocolVersionClientbound(332, 0x2B);
			this.addIdByProtocolVersionClientbound(338, 0x2C); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x2C); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x2E); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x2E); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x2E); //1.13.2
			
			this.addIdByProtocolVersionServerbound(1, 0x13);
			this.addIdByProtocolVersionServerbound(49, 0x14);
			this.addIdByProtocolVersionServerbound(67, 0x0F);
			this.addIdByProtocolVersionServerbound(77, 0x10);
			this.addIdByProtocolVersionServerbound(80, 0x12);
			this.addIdByProtocolVersionServerbound(318, 0x13);
			this.addIdByProtocolVersionServerbound(332, 0x13);
			this.addIdByProtocolVersionServerbound(338, 0x13); //1.12.1
			this.addIdByProtocolVersionServerbound(340, 0x13); //1.12.2
			this.addIdByProtocolVersionServerbound(393, 0x17); //1.13
			this.addIdByProtocolVersionServerbound(401, 0x17); //1.13.1
			this.addIdByProtocolVersionServerbound(404, 0x17); //1.13.2
			
			this.packetsDefined();
		}
	},
	UPDATE_ENTITY_NBT
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x49);
			this.addIdByProtocolVersionClientbound(49, null); //Removed
			
			this.packetsDefined();
		}
	},
	ENTITY_ATTACH
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x1B);
			this.addIdByProtocolVersionClientbound(67, 0x3A);
			this.addIdByProtocolVersionClientbound(77, 0x3B);
			this.addIdByProtocolVersionClientbound(86, 0x3A);
			this.addIdByProtocolVersionClientbound(318, 0x3C);
			this.addIdByProtocolVersionClientbound(332, 0x3C);
			this.addIdByProtocolVersionClientbound(336, 0x3D);
			this.addIdByProtocolVersionClientbound(338, 0x3D); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x3D); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x40); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x40); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x40); //1.13.2
			
			this.packetsDefined();
		}
	},
	HELD_ITEM_CHANGE
	{
		{
			this.addIdByProtocolVersionClientbound(1, 0x09);
			this.addIdByProtocolVersionClientbound(67, 0x37);
			this.addIdByProtocolVersionClientbound(80, 0x38);
			this.addIdByProtocolVersionClientbound(318, 0x39);
			this.addIdByProtocolVersionClientbound(332, 0x39);
			this.addIdByProtocolVersionClientbound(338, 0x3A); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x3A); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x3D); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x3D); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x3D); //1.13.2
			
			this.addIdByProtocolVersionServerbound(1, 0x09);
			this.addIdByProtocolVersionServerbound(49, 0x0A);
			this.addIdByProtocolVersionServerbound(67, 0x14);
			this.addIdByProtocolVersionServerbound(77, 0x15);
			this.addIdByProtocolVersionServerbound(80, 0x17);
			this.addIdByProtocolVersionServerbound(318, 0x19);
			this.addIdByProtocolVersionServerbound(332, 0x1A);
			this.addIdByProtocolVersionServerbound(338, 0x1A); //1.12.1
			this.addIdByProtocolVersionServerbound(340, 0x1A); //1.12.2
			this.addIdByProtocolVersionServerbound(393, 0x21); //1.13
			this.addIdByProtocolVersionServerbound(401, 0x21); //1.13.1
			this.addIdByProtocolVersionServerbound(404, 0x21); //1.13.2
			
			this.packetsDefined();
		}
	},
	DECLARE_COMMANDS
	{
		{
			this.addIdByProtocolVersionClientbound(393, 0x11); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x11); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x11); //1.13.2
			
			this.packetsDefined();
		}
	},
	NBT_BLOCK_QUERY
	{
		{
			this.addIdByProtocolVersionClientbound(393, 0x1D); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x1D); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x1D); //1.13.2
			
			this.packetsDefined();
		}
	},
	CRAFT_RECIPE_RESPONSE
	{
		{
			this.addIdByProtocolVersionClientbound(393, 0x2D); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x2D); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x2D); //1.13.2
			
			this.packetsDefined();
		}
	},
	UNLOCK_RECIPES
	{
		{
			this.addIdByProtocolVersionClientbound(393, 0x34); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x34); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x34); //1.13.2
			
			this.packetsDefined();
		}
	},
	TAGS
	{
		{
			this.addIdByProtocolVersionClientbound(393, 0x55); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x55); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x55); //1.13.2
			
			this.packetsDefined();
		}
	},
	DECLARE_RECIPES
	{
		{
			this.addIdByProtocolVersionClientbound(393, 0x54); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x54); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x54); //1.13.2
			
			this.packetsDefined();
		}
	},
	CAMERA
	{
		{
			this.addIdByProtocolVersionClientbound(47, 0x43); //1.8.x
			this.addIdByProtocolVersionClientbound(107, 0x36); //1.9
			this.addIdByProtocolVersionClientbound(108, 0x36); //1.9.1
			this.addIdByProtocolVersionClientbound(109, 0x36); //1.9.2
			this.addIdByProtocolVersionClientbound(110, 0x36); //1.9.3 & 1.9.4
			this.addIdByProtocolVersionClientbound(210, 0x36); //1.10.x
			this.addIdByProtocolVersionClientbound(315, 0x36); //1.11
			this.addIdByProtocolVersionClientbound(316, 0x36); //1.11.1 & 1.11.2
			this.addIdByProtocolVersionClientbound(335, 0x38); //1.12
			this.addIdByProtocolVersionClientbound(338, 0x39); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x39); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x3C); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x3C); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x3C); //1.13.2
			
			this.packetsDefined();
		}
	},
	TELEPORT_CONFIRM
	{
		{
			this.addIdByProtocolVersionClientbound(107, 0x00); //1.9
			this.addIdByProtocolVersionClientbound(108, 0x00); //1.9.1
			this.addIdByProtocolVersionClientbound(109, 0x00); //1.9.2
			this.addIdByProtocolVersionClientbound(110, 0x00); //1.9.3 & 1.9.4
			this.addIdByProtocolVersionClientbound(210, 0x00); //1.10.x
			this.addIdByProtocolVersionClientbound(315, 0x00); //1.11
			this.addIdByProtocolVersionClientbound(316, 0x00); //1.11.1 & 1.11.2
			this.addIdByProtocolVersionClientbound(335, 0x00); //1.12
			this.addIdByProtocolVersionClientbound(338, 0x00); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x00); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x00); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x00); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x00); //1.13.2
			
			this.packetsDefined();
		}
	},
	UPDATE_HEALTH
	{
		{
			this.addIdByProtocolVersionClientbound(47, 0x06); //1.8.x
			this.addIdByProtocolVersionClientbound(107, 0x3E); //1.9
			this.addIdByProtocolVersionClientbound(108, 0x3E); //1.9.1
			this.addIdByProtocolVersionClientbound(109, 0x3E); //1.9.2
			this.addIdByProtocolVersionClientbound(110, 0x3E); //1.9.3 & 1.9.4
			this.addIdByProtocolVersionClientbound(210, 0x3E); //1.10.x
			this.addIdByProtocolVersionClientbound(315, 0x3E); //1.11
			this.addIdByProtocolVersionClientbound(316, 0x3E); //1.11.1 & 1.11.2
			this.addIdByProtocolVersionClientbound(335, 0x40); //1.12
			this.addIdByProtocolVersionClientbound(338, 0x41); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x41); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x44); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x44); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x44); //1.13.2
		}
	},
	USE_BED
	{
		{
			this.addIdByProtocolVersionClientbound(47, 0x0A); //1.8.x
			this.addIdByProtocolVersionClientbound(107, 0x2F); //1.9
			this.addIdByProtocolVersionClientbound(108, 0x2F); //1.9.1
			this.addIdByProtocolVersionClientbound(109, 0x2F); //1.9.2
			this.addIdByProtocolVersionClientbound(110, 0x2F); //1.9.3 & 1.9.4
			this.addIdByProtocolVersionClientbound(210, 0x2F); //1.10.x
			this.addIdByProtocolVersionClientbound(315, 0x2F); //1.11
			this.addIdByProtocolVersionClientbound(316, 0x2F); //1.11.1 & 1.11.2
			this.addIdByProtocolVersionClientbound(335, 0x2F); //1.12
			this.addIdByProtocolVersionClientbound(338, 0x2F); //1.12.1
			this.addIdByProtocolVersionClientbound(340, 0x30); //1.12.2
			this.addIdByProtocolVersionClientbound(393, 0x33); //1.13
			this.addIdByProtocolVersionClientbound(401, 0x33); //1.13.1
			this.addIdByProtocolVersionClientbound(404, 0x33); //1.13.2
		}
	},
	ANIMATION
	{
		{
			this.addIdByProtocolVersionClientbound(47, 0x0B); //1.8.x
		}
	},
	CLOSE_WINDOW
	{
		{
			this.addIdByProtocolVersionClientbound(47, 0x2E); //1.8.x
		}
	},
	ENTITY_PROPERTIES
	{
		{
			this.addIdByProtocolVersionClientbound(47, 0x20); //1.8.x
		}
	},
	ENTITY_VELOCITY
	{
		{
			this.addIdByProtocolVersionClientbound(47, 0x12); //1.8.x
		}
	},
	EXPLOSION
	{
		{
			this.addIdByProtocolVersionClientbound(47, 0x27); //1.8.x
		}
	};

	private Map<Integer, Integer> idsByProtocolVersionServerbound;
	private Map<Integer, Integer> idsByProtocolVersionClientbound;
	
	Packet()
	{
		this.idsByProtocolVersionClientbound = new HashMap<>();
		this.idsByProtocolVersionServerbound = new HashMap<>();
	}
	
	void addIdByProtocolVersionServerbound(int protocolVersion, Integer id)
	{
		this.idsByProtocolVersionServerbound.put(protocolVersion, id);
	}
	
	void packetsDefined()
	{
		int clientBoundMax = this.idsByProtocolVersionClientbound.keySet().stream().filter((p) -> p != null).mapToInt((p) -> p.intValue()).max().orElse(0);
		int serverBoundMax = this.idsByProtocolVersionServerbound.keySet().stream().filter((p) -> p != null).mapToInt((p) -> p.intValue()).max().orElse(0);
	
		this.idsByProtocolVersionClientbound.put(clientBoundMax + 1, null);
		this.idsByProtocolVersionServerbound.put(serverBoundMax + 1, null);
	}
	
	void addIdByProtocolVersionClientbound(int protocolVersion, Integer id)
	{
		this.idsByProtocolVersionClientbound.put(protocolVersion, id);
	}
	
	public Integer getClientboundId(int protocolVersion)
	{
		return this.getHighest(this.idsByProtocolVersionClientbound, protocolVersion);
	}
	
	public Integer getServerboundId(int protocolVersion)
	{
		return this.getHighest(this.idsByProtocolVersionServerbound, protocolVersion);
	}
	
	private Integer getHighest(Map<Integer, Integer> map, int protocolVersion)
	{
		//Fast path
		Integer packetId = map.get(protocolVersion);
		if (packetId != null)
		{
			return packetId;
		}

		//Slow path
		int protocolVersionTestAgainst = 0;
		for(Entry<Integer, Integer> packet : map.entrySet())
		{
			int requiredProtocol = packet.getKey();
			if (protocolVersion > requiredProtocol && requiredProtocol > protocolVersionTestAgainst)
			{
				protocolVersionTestAgainst = requiredProtocol;
				packetId = packet.getValue();
			}
		}
		
		return packetId;
	}
}
