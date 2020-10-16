package net.goldtreeservers.cheatdetectionplugin.common.utils;

public class Settings
{
	public static boolean TRACK_LOGINS = false;
	
	/*@SuppressWarnings("serial")
	public static Map<Integer, Map<Integer>> IGNORED_PACKETS_SERVERBOUND = new HashMap<Integer, Set<Integer>>() {{
		this.put(1, new HashSet<Integer>() {{
			//this.add(0x01); //Chat
			//this.add(0x14); //Tab Complete
			//this.add(0x17); //Plugin Message
		}});
		
		this.put(49, new HashSet<Integer>() {{
			//this.add(0x15); //Tab Complete
			//this.add(0x18); //Plugin Message
		}});
		
		this.put(59, new HashSet<Integer>() {{
			//this.add(0x15); //Tab Complete
		}});
		
		this.put(67, new HashSet<Integer>() {{
			//this.add(0x02); //Chat
			//this.add(0x00); //Tab Complete
			//this.add(0x08); //Plugin Message
		}});
		
		this.put(80, new HashSet<Integer>() {{
			//this.add(0x01); //Tab Complete
			//this.add(0x09); //Plugin Message
		}});
		
		this.put(318, new HashSet<Integer>() {{
			//this.add(0x03); //Chat
			//this.add(0x02); //Tab Complete
			//this.add(0x0A); //Plugin Message
		}});
		
		this.put(332, new HashSet<Integer>() {{
			//this.add(0x0A); //Plugin Message
		}});
		
		this.put(336, new HashSet<Integer>() {{
			//this.add(0x02); //Chat
			//this.add(0x09); //Plugin Message
		}});
	}};*/
	
	/*@SuppressWarnings("serial")
	public static Map<Integer, Set<Integer>> IGNORED_PACKETS_CLEINTBOUND = new HashMap<Integer, Set<Integer>>() {{
		this.put(1, new HashSet<Integer>() {{
			//this.add(0x02); //Chat
			//this.add(0x38); //Player List Item
			//this.add(0x3A); //Tab Complete
			//this.add(0x3B); //Scoreboard Objective
			//this.add(0x3C); //Update Score
			//this.add(0x3D); //Display Scoreboard
			//this.add(0x3E); //Team
			//this.add(0x3F); //Plugin Message
			//this.add(0x40); //Disconnect
			//this.add(0x45); //Title
			//this.add(0x47); //Player List Header And Footer
			//this.add(0x37); //Statistic
			//this.add(0x03); //Time Update
			//this.add(0x29); //Named Sound Effect
			//this.add(0x0D); //Collect Item
			//this.add(0x28); //Effect
			//this.add(0x2A); //Particle
			//this.add(0x34); //Map
			//this.add(0x48); //Resource Pack Send
		}});
		
		this.put(49, new HashSet<Integer>() {{
			//this.add(0x49); //Boss Bar
		}});
		
		this.put(67, new HashSet<Integer>() {{
			//this.add(0x0F); //Chat
			//this.add(0x0C); //Boss Bar
			//this.add(0x2D); //Player List Item
			//this.add(0x0E); //Tab Complete
			//this.add(0x3F); //Scoreboard Objective
			//this.add(0x41); //Update Score
			//this.add(0x38); //Display Scoreboard
			//this.add(0x40); //Team
			//this.add(0x18); //Plugin Message
			//this.add(0x19); //Disconnect
			//this.add(0x44); //Title
			//this.add(0x46); //Player List Header And Footer
			//this.add(0x07); //Statistic
			//this.add(0x43); //Time Update
			//this.add(0x23); //Named Sound Effect
			//this.add(0x47); //Collect Item
			//this.add(0x21); //Effect
			//this.add(0x22); //Particle
			//this.add(0x25); //Map
			//this.add(0x32); //Resource Pack Send
		}});
		
		this.put(77, new HashSet<Integer>() {{
			//this.add(0x40); //Scoreboard Objective
			//this.add(0x42); //Update Score
			//this.add(0x41); //Team
			//this.add(0x45); //Title
			//this.add(0x47); //Player List Header And Footer
			//this.add(0x44); //Time Update
			//this.add(0x48); //Collect Item
		}});
		
		this.put(80, new HashSet<Integer>() {{
			//this.add(0x2E); //Player List Item
			//this.add(0x43); //Update Score
			//this.add(0x39); //Display Scoreboard
			//this.add(0x42); //Team
			//this.add(0x1A); //Disconnect
			//this.add(0x46); //Title
			//this.add(0x49); //Player List Header And Footer
			//this.add(0x45); //Time Update
			//this.add(0x19); //Named Sound Effect
			//this.add(0x48); //Sound Effect
			//this.add(0x4A); //Collect Item
			//this.add(0x22); //Effect
			//this.add(0x23); //Particle
			//this.add(0x33); //Resource Pack Send
		}});
		
		this.put(86, new HashSet<Integer>() {{
			//this.add(0x2D); //Player List Item
			//this.add(0x3F); //Scoreboard Objective
			//this.add(0x42); //Update Score
			//this.add(0x38); //Display Scoreboard
			//this.add(0x41); //Team
			//this.add(0x45); //Title
			//this.add(0x48); //Player List Header And Footer
			//this.add(0x44); //Time Update
			//this.add(0x47); //Sound Effect
			//this.add(0x49); //Collect Item
			//this.add(0x21); //Effect
			//this.add(0x22); //Particle
			//this.add(0x24); //Map
			//this.add(0x32); //Resource Pack Send
		}});
		
		this.put(110, new HashSet<Integer>() {{
			//this.add(0x47); //Player List Header And Footer
			//his.add(0x46); //Sound Effect
			//this.add(0x48); //Collect Item
		}});
		
		this.put(318, new HashSet<Integer>() {{
			//this.add(0x10); //Chat
			//this.add(0x0D); //Boss Bar
			//this.add(0x2E); //Player List Item
			//this.add(0x0F); //Tab Complete
			//this.add(0x41); //Scoreboard Objective
			//this.add(0x44); //Update Score
			//this.add(0x3A); //Display Scoreboard
			//this.add(0x43); //Team
			//this.add(0x19); //Plugin Message
			//his.add(0x1B); //Disconnect
			//this.add(0x47); //Title
			//this.add(0x49); //Player List Header And Footer
			//this.add(0x46); //Time Update
			//this.add(0x1A); //Named Sound Effect
			//this.add(0x48); //Sound Effect
			//this.add(0x4A); //Collect Item
			//this.add(0x08); //Advancements
			//this.add(0x22); //Effect
			//this.add(0x23); //Particle
			//this.add(0x34); //Resource Pack Send
		}});

		this.put(330, new HashSet<Integer>() {{
			//this.add(0x20); //Select Advancement Tab
			//this.add(0x4E); //Advancement Progress
		}});
		
		this.put(332, new HashSet<Integer>() {{
			//this.add(0x0F); //Chat
			//this.add(0x0C); //Boss Bar
			//this.add(0x2D); //Player List Item
			//this.add(0x0E); //Tab Complete
			//this.add(0x18); //Plugin Message
			//this.add(0x1A); //Disconnect
			//this.add(0x19); //Named Sound Effect
			//this.add(0x4C); //Advancements
			//this.add(0x21); //Effect
			//this.add(0x22); //Particle
			//this.add(0x33); //Resource Pack Send
			//this.add(0x19); //Select Advancement Tab
			//this.add(0x36); //Advancement Progress
		}});
		
		this.put(336, new HashSet<Integer>() {{
			//this.add(0x2E); //Player List Item
			//this.add(0x01); //Tab Complete
			//this.add(0x42); //Scoreboard Objective
			//this.add(0x45); //Update Score
			//this.add(0x3B); //Display Scoreboard
			//this.add(0x44); //Team
			//this.add(0x48); //Title
			//this.add(0x4A); //Player List Header And Footer
			//this.add(0x47); //Time Update
			//this.add(0x49); //Sound Effect
			//this.add(0x4B); //Collect Item
			//this.add(0x4D); //Advancements
			//this.add(0x34); //Resource Pack Send
			//this.add(0x37); //Select Advancement Tab
		}});
	}};*/
}
