package net.goldtreeservers.cheatdetectionplugin.common.user.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Charsets;
import com.google.common.collect.Sets;

import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import net.goldtreeservers.cheatdetectionplugin.common.protocol.Packet;
import net.goldtreeservers.cheatdetectionplugin.common.utils.ByteBufUtils;

public class ProtocolData
{
	private static final Set<Packet> IGNORED_PACKETS = Sets.newHashSet(
			Packet.CHAT,
			Packet.TAB_COMPLETE,
			Packet.PLUGIN_MESSAGE,
			Packet.PLAYER_LIST_ITEM,
			Packet.SCOREBOARD_OBJECTIVE,
			Packet.UPDATE_SCORE,
			Packet.DISPLAY_SCOREBOARD,
			Packet.TEAM,
			Packet.DISCONNECT,
			Packet.TITLE,
			Packet.PLAYER_LIST_HEADER_AND_FOOTER,
			Packet.STATISTIC,
			Packet.TIME_UPDATE,
			Packet.NAMED_SOUND_EFFECT,
			Packet.COLLECT_ITEM,
			Packet.EFFECT,
			Packet.PARTICLE,
			Packet.MAP,
			Packet.RESOURCE_PACK_SEND,
			Packet.BOSS_BAR,
			Packet.SOUND_EFFECT,
			Packet.ADVANCEMENTS,
			Packet.SELECT_ADVANCEMENT_TAB,
			Packet.ADVANCEMENT_PROGRESS,
			Packet.BLOCK_CHANGE, //Think of something
			Packet.MULTI_BLOCK_CHANGE, //Think of something
			Packet.UPDATE_BLOCK_ENTITY,
			Packet.WINDOW_ITEMS,
			Packet.SET_SLOT,
			Packet.ENTITY_EQUIPMENT,
			Packet.CREATE_INVENTORY_ACTION,
			Packet.UPDATE_SIGN,
			Packet.STATISTICS,
			Packet.UPDATE_ENTITY_NBT,
			Packet.DECLARE_COMMANDS,
			Packet.NBT_BLOCK_QUERY,
			Packet.CRAFT_RECIPE_RESPONSE,
			Packet.UNLOCK_RECIPES,
			Packet.TAGS,
			Packet.DECLARE_RECIPES);
	
	private static final Set<Packet> CONFIRM_PACKETS = Sets.newHashSet(Packet.SPAWN_PLAYER,
			Packet.ENTITY,
			Packet.ENTITY_RELATIVE_MOVE,
			Packet.ENTITY_LOOK,
			Packet.ENTITY_LOOK_AND_RELATIVE_MOVE,
			Packet.ENTITY_TELEPORT,
			Packet.RESPAWN,
			Packet.DESTROY_ENTITIES,
			Packet.JOIN_PACKET,
			Packet.OPEN_WINDOW,
			Packet.PLAYER_ABILITIES,
			Packet.ENTITY_ATTACH,
			Packet.HELD_ITEM_CHANGE,
			Packet.CAMERA,
			Packet.CHUNK_DATA,
			Packet.MAP_CHUNK_BULK,
			Packet.BLOCK_CHANGE,
			Packet.MULTI_BLOCK_CHANGE,
			Packet.UPDATE_HEALTH,
			Packet.USE_BED,
			Packet.CLOSE_WINDOW,
			Packet.ENTITY_PROPERTIES,
			Packet.ENTITY_VELOCITY,
			Packet.EXPLOSION);
	
	@SuppressWarnings("serial")
	private static final Map<String, Integer> FORGE_POTIONS_IDS = new HashMap<String, Integer>()
	{
		{
			this.put("minecraft:instant_health", 6);
			this.put("minecraft:instant_damage", 7);
			this.put("minecraft:resistance", 11);
			this.put("minecraft:weakness", 18);
			this.put("minecraft:hunger", 17);
			this.put("minecraft:fire_resistance", 12);
			this.put("minecraft:slowness", 2);
			this.put("minecraft:strength", 5);
			this.put("minecraft:poison", 19);
			this.put("minecraft:absorption", 22);
			this.put("minecraft:mining_fatigue", 4);
			this.put("minecraft:health_boost", 21);
			this.put("minecraft:night_vision", 16);
			this.put("minecraft:invisibility", 14);
			this.put("minecraft:saturation", 23);
			this.put("minecraft:nausea", 9);
			this.put("minecraft:regeneration", 10);
			this.put("minecraft:jump_boost", 8);
			this.put("minecraft:blindness", 15);
			this.put("minecraft:haste", 3);
			this.put("minecraft:wither", 20);
			this.put("minecraft:speed", 1);
			this.put("minecraft:water_breathing", 13);
		}
	};
	
	@SuppressWarnings("serial")
	private static final Map<String, Integer> FORGE_VILLAGER_PROFESSIONS_IDS = new HashMap<String, Integer>()
	{
		{
			this.put("minecraft:smith", 3);
			this.put("minecraft:butcher", 4);
			this.put("minecraft:priest", 2);
			this.put("minecraft:librarian", 1);
			this.put("minecraft:farmer", 0);
		}
	};
	
	@SuppressWarnings("serial")
	private static final Map<String, Integer> FORGE_BLOCKS_IDS = new HashMap<String, Integer>()
	{
		{
			this.put("minecraft:wooden_button", 143);
			this.put("minecraft:stained_hardened_clay", 159);
			this.put("minecraft:daylight_detector_inverted", 178);
			this.put("minecraft:stone_pressure_plate", 70);
			this.put("minecraft:golden_rail", 27);
			this.put("minecraft:double_stone_slab2", 181);
			this.put("minecraft:acacia_fence_gate", 187);
			this.put("minecraft:oak_stairs", 53);
			this.put("minecraft:grass", 2);
			this.put("minecraft:cake", 92);
			this.put("minecraft:fire", 51);
			this.put("minecraft:wooden_door", 64);
			this.put("minecraft:monster_egg", 97);
			this.put("minecraft:birch_fence", 189);
			this.put("minecraft:enchanting_table", 116);
			this.put("minecraft:deadbush", 32);
			this.put("minecraft:tripwire", 132);
			this.put("minecraft:cobblestone_wall", 139);
			this.put("minecraft:carrots", 141);
			this.put("minecraft:stone", 1);
			this.put("minecraft:unpowered_repeater", 93);
			this.put("minecraft:sand", 12);
			this.put("minecraft:redstone_ore", 73);
			this.put("minecraft:nether_brick_stairs", 114);
			this.put("minecraft:hay_block", 170);
			this.put("minecraft:trapdoor", 96);
			this.put("minecraft:birch_door", 194);
			this.put("minecraft:stonebrick", 98);
			this.put("minecraft:wheat", 59);
			this.put("minecraft:dark_oak_fence", 191);
			this.put("minecraft:jungle_fence_gate", 185);
			this.put("minecraft:jukebox", 84);
			this.put("minecraft:ice", 79);
			this.put("minecraft:beacon", 138);
			this.put("minecraft:waterlily", 111);
			this.put("minecraft:acacia_fence", 192);
			this.put("minecraft:powered_comparator", 150);
			this.put("minecraft:standing_banner", 176);
			this.put("minecraft:end_portal", 119);
			this.put("minecraft:bed", 26);
			this.put("minecraft:birch_fence_gate", 184);
			this.put("minecraft:sea_lantern", 169);
			this.put("minecraft:powered_repeater", 94);
			this.put("minecraft:wool", 35);
			this.put("minecraft:pumpkin_stem", 104);
			this.put("minecraft:double_wooden_slab", 125);
			this.put("minecraft:lit_redstone_ore", 74);
			this.put("minecraft:crafting_table", 58);
			this.put("minecraft:lever", 69);
			this.put("minecraft:tnt", 46);
			this.put("minecraft:jungle_fence", 190);
			this.put("minecraft:brown_mushroom_block", 99);
			this.put("minecraft:acacia_stairs", 163);
			this.put("minecraft:air", 0);
			this.put("minecraft:standing_sign", 63);
			this.put("minecraft:red_sandstone", 179);
			this.put("minecraft:log", 17);
			this.put("minecraft:brick_block", 45);
			this.put("minecraft:pumpkin", 86);
			this.put("minecraft:light_weighted_pressure_plate", 147);
			this.put("minecraft:bookshelf", 47);
			this.put("minecraft:end_portal_frame", 120);
			this.put("minecraft:diamond_ore", 56);
			this.put("minecraft:flowing_water", 8);
			this.put("minecraft:wall_banner", 177);
			this.put("minecraft:wall_sign", 68);
			this.put("minecraft:iron_door", 71);
			this.put("minecraft:reeds", 83);
			this.put("minecraft:stained_glass", 95);
			this.put("minecraft:mob_spawner", 52);
			this.put("minecraft:noteblock", 25);
			this.put("minecraft:dirt", 3);
			this.put("minecraft:redstone_wire", 55);
			this.put("minecraft:stone_button", 77);
			this.put("minecraft:ender_chest", 130);
			this.put("minecraft:glass", 20);
			this.put("minecraft:double_plant", 175);
			this.put("minecraft:slime", 165);
			this.put("minecraft:gold_block", 41);
			this.put("minecraft:hardened_clay", 172);
			this.put("minecraft:unlit_redstone_torch", 75);
			this.put("minecraft:end_stone", 121);
			this.put("minecraft:detector_rail", 28);
			this.put("minecraft:piston", 33);
			this.put("minecraft:snow_layer", 78);
			this.put("minecraft:red_sandstone_stairs", 180);
			this.put("minecraft:cactus", 81);
			this.put("minecraft:leaves2", 161);
			this.put("minecraft:cocoa", 127);
			this.put("minecraft:iron_ore", 15);
			this.put("minecraft:leaves", 18);
			this.put("minecraft:sapling", 6);
			this.put("minecraft:fence_gate", 107);
			this.put("minecraft:netherrack", 87);
			this.put("minecraft:redstone_torch", 76);
			this.put("minecraft:wooden_pressure_plate", 72);
			this.put("minecraft:sandstone_stairs", 128);
			this.put("minecraft:mossy_cobblestone", 48);
			this.put("minecraft:furnace", 61);
			this.put("minecraft:hopper", 154);
			this.put("minecraft:iron_trapdoor", 167);
			this.put("minecraft:nether_brick_fence", 113);
			this.put("minecraft:log2", 162);
			this.put("minecraft:redstone_lamp", 123);
			this.put("minecraft:quartz_stairs", 156);
			this.put("minecraft:brewing_stand", 117);
			this.put("minecraft:barrier", 166);
			this.put("minecraft:spruce_fence", 188);
			this.put("minecraft:dark_oak_door", 197);
			this.put("minecraft:piston_extension", 36);
			this.put("minecraft:dragon_egg", 122);
			this.put("minecraft:stone_stairs", 67);
			this.put("minecraft:ladder", 65);
			this.put("minecraft:sticky_piston", 29);
			this.put("minecraft:melon_block", 103);
			this.put("minecraft:unpowered_comparator", 149);
			this.put("minecraft:snow", 80);
			this.put("minecraft:skull", 144);
			this.put("minecraft:glass_pane", 102);
			this.put("minecraft:flowing_lava", 10);
			this.put("minecraft:jungle_stairs", 136);
			this.put("minecraft:rail", 66);
			this.put("minecraft:spruce_fence_gate", 183);
			this.put("minecraft:diamond_block", 57);
			this.put("minecraft:tallgrass", 31);
			this.put("minecraft:lapis_block", 22);
			this.put("minecraft:prismarine", 168);
			this.put("minecraft:stone_slab", 44);
			this.put("minecraft:farmland", 60);
			this.put("minecraft:bedrock", 7);
			this.put("minecraft:iron_block", 42);
			this.put("minecraft:lit_redstone_lamp", 124);
			this.put("minecraft:activator_rail", 157);
			this.put("minecraft:spruce_stairs", 134);
			this.put("minecraft:gravel", 13);
			this.put("minecraft:carpet", 171);
			this.put("minecraft:nether_wart", 115);
			this.put("minecraft:nether_brick", 112);
			this.put("minecraft:vine", 106);
			this.put("minecraft:soul_sand", 88);
			this.put("minecraft:red_mushroom_block", 100);
			this.put("minecraft:glowstone", 89);
			this.put("minecraft:stone_slab2", 182);
			this.put("minecraft:tripwire_hook", 131);
			this.put("minecraft:piston_head", 34);
			this.put("minecraft:web", 30);
			this.put("minecraft:stone_brick_stairs", 109);
			this.put("minecraft:trapped_chest", 146);
			this.put("minecraft:dropper", 158);
			this.put("minecraft:potatoes", 142);
			this.put("minecraft:lava", 11);
			this.put("minecraft:gold_ore", 14);
			this.put("minecraft:chest", 54);
			this.put("minecraft:brown_mushroom", 39);
			this.put("minecraft:birch_stairs", 135);
			this.put("minecraft:lit_furnace", 62);
			this.put("minecraft:cauldron", 118);
			this.put("minecraft:stained_glass_pane", 160);
			this.put("minecraft:yellow_flower", 37);
			this.put("minecraft:daylight_detector", 151);
			this.put("minecraft:portal", 90);
			this.put("minecraft:dark_oak_fence_gate", 186);
			this.put("minecraft:dark_oak_stairs", 164);
			this.put("minecraft:quartz_block", 155);
			this.put("minecraft:dispenser", 23);
			this.put("minecraft:coal_ore", 16);
			this.put("minecraft:fence", 85);
			this.put("minecraft:jungle_door", 195);
			this.put("minecraft:water", 9);
			this.put("minecraft:planks", 5);
			this.put("minecraft:torch", 50);
			this.put("minecraft:flower_pot", 140);
			this.put("minecraft:acacia_door", 196);
			this.put("minecraft:brick_stairs", 108);
			this.put("minecraft:quartz_ore", 153);
			this.put("minecraft:double_stone_slab", 43);
			this.put("minecraft:melon_stem", 105);
			this.put("minecraft:spruce_door", 193);
			this.put("minecraft:cobblestone", 4);
			this.put("minecraft:heavy_weighted_pressure_plate", 148);
			this.put("minecraft:coal_block", 173);
			this.put("minecraft:redstone_block", 152);
			this.put("minecraft:wooden_slab", 126);
			this.put("minecraft:command_block", 137);
			this.put("minecraft:sandstone", 24);
			this.put("minecraft:packed_ice", 174);
			this.put("minecraft:lit_pumpkin", 91);
			this.put("minecraft:red_flower", 38);
			this.put("minecraft:emerald_block", 133);
			this.put("minecraft:iron_bars", 101);
			this.put("minecraft:sponge", 19);
			this.put("minecraft:lapis_ore", 21);
			this.put("minecraft:mycelium", 110);
			this.put("minecraft:anvil", 145);
			this.put("minecraft:obsidian", 49);
			this.put("minecraft:red_mushroom", 40);
			this.put("minecraft:clay", 82);
			this.put("minecraft:emerald_ore", 129);
		}
	};
	
	@SuppressWarnings("serial")
	private static final Map<String, Integer> FORGE_ITEMS_IDS = new HashMap<String, Integer>()
	{
		{
			this.put("minecraft:iron_leggings", 308);
			this.put("minecraft:name_tag", 421);
			this.put("minecraft:redstone", 331);
			this.put("minecraft:wooden_button", 143);
			this.put("minecraft:stained_hardened_clay", 159);
			this.put("minecraft:wheat_seeds", 295);
			this.put("minecraft:stone_pressure_plate", 70);
			this.put("minecraft:chainmail_helmet", 302);
			this.put("minecraft:golden_horse_armor", 418);
			this.put("minecraft:compass", 345);
			this.put("minecraft:golden_rail", 27);
			this.put("minecraft:shears", 359);
			this.put("minecraft:experience_bottle", 384);
			this.put("minecraft:golden_hoe", 294);
			this.put("minecraft:acacia_fence_gate", 187);
			this.put("minecraft:rabbit_hide", 415);
			this.put("minecraft:comparator", 404);
			this.put("minecraft:stone_shovel", 273);
			this.put("minecraft:ender_eye", 381);
			this.put("minecraft:oak_stairs", 53);
			this.put("minecraft:grass", 2);
			this.put("minecraft:chainmail_leggings", 304);
			this.put("minecraft:saddle", 329);
			this.put("minecraft:cake", 354);
			this.put("minecraft:monster_egg", 97);
			this.put("minecraft:wooden_door", 324);
			this.put("minecraft:netherbrick", 405);
			this.put("minecraft:birch_fence", 189);
			this.put("minecraft:enchanting_table", 116);
			this.put("minecraft:deadbush", 32);
			this.put("minecraft:apple", 260);
			this.put("minecraft:record_cat", 2257);
			this.put("minecraft:beef", 363);
			this.put("minecraft:diamond_pickaxe", 278);
			this.put("minecraft:cobblestone_wall", 139);
			this.put("minecraft:record_13", 2256);
			this.put("minecraft:stone", 1);
			this.put("minecraft:sand", 12);
			this.put("minecraft:record_11", 2266);
			this.put("minecraft:redstone_ore", 73);
			this.put("minecraft:mushroom_stew", 282);
			this.put("minecraft:melon_seeds", 362);
			this.put("minecraft:cooked_chicken", 366);
			this.put("minecraft:nether_brick_stairs", 114);
			this.put("minecraft:hay_block", 170);
			this.put("minecraft:trapdoor", 96);
			this.put("minecraft:birch_door", 428);
			this.put("minecraft:carrot_on_a_stick", 398);
			this.put("minecraft:stonebrick", 98);
			this.put("minecraft:leather_leggings", 300);
			this.put("minecraft:wheat", 296);
			this.put("minecraft:dye", 351);
			this.put("minecraft:iron_sword", 267);
			this.put("minecraft:dark_oak_fence", 191);
			this.put("minecraft:filled_map", 358);
			this.put("minecraft:jungle_fence_gate", 185);
			this.put("minecraft:golden_carrot", 396);
			this.put("minecraft:jukebox", 84);
			this.put("minecraft:ice", 79);
			this.put("minecraft:beacon", 138);
			this.put("minecraft:waterlily", 111);
			this.put("minecraft:quartz", 406);
			this.put("minecraft:written_book", 387);
			this.put("minecraft:acacia_fence", 192);
			this.put("minecraft:wooden_pickaxe", 270);
			this.put("minecraft:golden_shovel", 284);
			this.put("minecraft:lava_bucket", 327);
			this.put("minecraft:bed", 355);
			this.put("minecraft:birch_fence_gate", 184);
			this.put("minecraft:gunpowder", 289);
			this.put("minecraft:sea_lantern", 169);
			this.put("minecraft:rabbit_stew", 413);
			this.put("minecraft:wool", 35);
			this.put("minecraft:ghast_tear", 370);
			this.put("minecraft:pumpkin_seeds", 361);
			this.put("minecraft:iron_boots", 309);
			this.put("minecraft:golden_apple", 322);
			this.put("minecraft:bowl", 281);
			this.put("minecraft:cooked_mutton", 424);
			this.put("minecraft:record_mall", 2261);
			this.put("minecraft:chainmail_chestplate", 303);
			this.put("minecraft:crafting_table", 58);
			this.put("minecraft:lever", 69);
			this.put("minecraft:blaze_powder", 377);
			this.put("minecraft:tnt", 46);
			this.put("minecraft:golden_boots", 317);
			this.put("minecraft:milk_bucket", 335);
			this.put("minecraft:iron_horse_armor", 417);
			this.put("minecraft:jungle_fence", 190);
			this.put("minecraft:brown_mushroom_block", 99);
			this.put("minecraft:acacia_stairs", 163);
			this.put("minecraft:arrow", 262);
			this.put("minecraft:chest_minecart", 342);
			this.put("minecraft:red_sandstone", 179);
			this.put("minecraft:glass_bottle", 374);
			this.put("minecraft:log", 17);
			this.put("minecraft:feather", 288);
			this.put("minecraft:wooden_hoe", 290);
			this.put("minecraft:ender_pearl", 368);
			this.put("minecraft:brick_block", 45);
			this.put("minecraft:pumpkin", 86);
			this.put("minecraft:iron_axe", 258);
			this.put("minecraft:light_weighted_pressure_plate", 147);
			this.put("minecraft:melon", 360);
			this.put("minecraft:bookshelf", 47);
			this.put("minecraft:stone_sword", 272);
			this.put("minecraft:end_portal_frame", 120);
			this.put("minecraft:diamond_ore", 56);
			this.put("minecraft:diamond_shovel", 277);
			this.put("minecraft:leather_helmet", 298);
			this.put("minecraft:magma_cream", 378);
			this.put("minecraft:coal", 263);
			this.put("minecraft:string", 287);
			this.put("minecraft:iron_door", 330);
			this.put("minecraft:reeds", 338);
			this.put("minecraft:rabbit_foot", 414);
			this.put("minecraft:leather_chestplate", 299);
			this.put("minecraft:stained_glass", 95);
			this.put("minecraft:mob_spawner", 52);
			this.put("minecraft:record_wait", 2267);
			this.put("minecraft:noteblock", 25);
			this.put("minecraft:dirt", 3);
			this.put("minecraft:gold_nugget", 371);
			this.put("minecraft:diamond", 264);
			this.put("minecraft:diamond_sword", 276);
			this.put("minecraft:stone_button", 77);
			this.put("minecraft:ender_chest", 130);
			this.put("minecraft:diamond_axe", 279);
			this.put("minecraft:iron_helmet", 306);
			this.put("minecraft:armor_stand", 416);
			this.put("minecraft:glass", 20);
			this.put("minecraft:double_plant", 175);
			this.put("minecraft:firework_charge", 402);
			this.put("minecraft:slime", 165);
			this.put("minecraft:gold_block", 41);
			this.put("minecraft:golden_leggings", 316);
			this.put("minecraft:mutton", 423);
			this.put("minecraft:stone_axe", 275);
			this.put("minecraft:hardened_clay", 172);
			this.put("minecraft:porkchop", 319);
			this.put("minecraft:end_stone", 121);
			this.put("minecraft:detector_rail", 28);
			this.put("minecraft:record_far", 2260);
			this.put("minecraft:stick", 280);
			this.put("minecraft:piston", 33);
			this.put("minecraft:snow_layer", 78);
			this.put("minecraft:flint", 318);
			this.put("minecraft:speckled_melon", 382);
			this.put("minecraft:red_sandstone_stairs", 180);
			this.put("minecraft:lead", 420);
			this.put("minecraft:record_strad", 2264);
			this.put("minecraft:chicken", 365);
			this.put("minecraft:cactus", 81);
			this.put("minecraft:leaves2", 161);
			this.put("minecraft:iron_ore", 15);
			this.put("minecraft:leaves", 18);
			this.put("minecraft:sapling", 6);
			this.put("minecraft:fence_gate", 107);
			this.put("minecraft:iron_chestplate", 307);
			this.put("minecraft:netherrack", 87);
			this.put("minecraft:prismarine_crystals", 410);
			this.put("minecraft:redstone_torch", 76);
			this.put("minecraft:diamond_hoe", 293);
			this.put("minecraft:wooden_pressure_plate", 72);
			this.put("minecraft:fireworks", 401);
			this.put("minecraft:sandstone_stairs", 128);
			this.put("minecraft:mossy_cobblestone", 48);
			this.put("minecraft:furnace", 61);
			this.put("minecraft:hopper", 154);
			this.put("minecraft:iron_trapdoor", 167);
			this.put("minecraft:nether_brick_fence", 113);
			this.put("minecraft:emerald", 388);
			this.put("minecraft:boat", 333);
			this.put("minecraft:bow", 261);
			this.put("minecraft:nether_star", 399);
			this.put("minecraft:pumpkin_pie", 400);
			this.put("minecraft:log2", 162);
			this.put("minecraft:redstone_lamp", 123);
			this.put("minecraft:quartz_stairs", 156);
			this.put("minecraft:brewing_stand", 379);
			this.put("minecraft:golden_axe", 286);
			this.put("minecraft:prismarine_shard", 409);
			this.put("minecraft:barrier", 166);
			this.put("minecraft:slime_ball", 341);
			this.put("minecraft:spruce_fence", 188);
			this.put("minecraft:dark_oak_door", 431);
			this.put("minecraft:banner", 425);
			this.put("minecraft:flint_and_steel", 259);
			this.put("minecraft:dragon_egg", 122);
			this.put("minecraft:cooked_beef", 364);
			this.put("minecraft:glowstone_dust", 348);
			this.put("minecraft:stone_stairs", 67);
			this.put("minecraft:ladder", 65);
			this.put("minecraft:sticky_piston", 29);
			this.put("minecraft:melon_block", 103);
			this.put("minecraft:snow", 80);
			this.put("minecraft:skull", 397);
			this.put("minecraft:item_frame", 389);
			this.put("minecraft:glass_pane", 102);
			this.put("minecraft:iron_shovel", 256);
			this.put("minecraft:fishing_rod", 346);
			this.put("minecraft:jungle_stairs", 136);
			this.put("minecraft:minecart", 328);
			this.put("minecraft:rail", 66);
			this.put("minecraft:spruce_fence_gate", 183);
			this.put("minecraft:diamond_block", 57);
			this.put("minecraft:tallgrass", 31);
			this.put("minecraft:sugar", 353);
			this.put("minecraft:clay_ball", 337);
			this.put("minecraft:lapis_block", 22);
			this.put("minecraft:prismarine", 168);
			this.put("minecraft:stone_slab", 44);
			this.put("minecraft:farmland", 60);
			this.put("minecraft:bedrock", 7);
			this.put("minecraft:iron_block", 42);
			this.put("minecraft:paper", 339);
			this.put("minecraft:brick", 336);
			this.put("minecraft:wooden_axe", 271);
			this.put("minecraft:activator_rail", 157);
			this.put("minecraft:spruce_stairs", 134);
			this.put("minecraft:gravel", 13);
			this.put("minecraft:carpet", 171);
			this.put("minecraft:nether_wart", 372);
			this.put("minecraft:nether_brick", 112);
			this.put("minecraft:vine", 106);
			this.put("minecraft:soul_sand", 88);
			this.put("minecraft:red_mushroom_block", 100);
			this.put("minecraft:glowstone", 89);
			this.put("minecraft:wooden_sword", 268);
			this.put("minecraft:potion", 373);
			this.put("minecraft:golden_pickaxe", 285);
			this.put("minecraft:cooked_fish", 350);
			this.put("minecraft:stone_slab2", 182);
			this.put("minecraft:map", 395);
			this.put("minecraft:tripwire_hook", 131);
			this.put("minecraft:sign", 323);
			this.put("minecraft:web", 30);
			this.put("minecraft:book", 340);
			this.put("minecraft:wooden_shovel", 269);
			this.put("minecraft:stone_brick_stairs", 109);
			this.put("minecraft:trapped_chest", 146);
			this.put("minecraft:hopper_minecart", 408);
			this.put("minecraft:dropper", 158);
			this.put("minecraft:fire_charge", 385);
			this.put("minecraft:gold_ore", 14);
			this.put("minecraft:chest", 54);
			this.put("minecraft:brown_mushroom", 39);
			this.put("minecraft:birch_stairs", 135);
			this.put("minecraft:lit_furnace", 62);
			this.put("minecraft:baked_potato", 393);
			this.put("minecraft:cauldron", 380);
			this.put("minecraft:stained_glass_pane", 160);
			this.put("minecraft:rabbit", 411);
			this.put("minecraft:yellow_flower", 37);
			this.put("minecraft:daylight_detector", 151);
			this.put("minecraft:painting", 321);
			this.put("minecraft:cooked_porkchop", 320);
			this.put("minecraft:clock", 347);
			this.put("minecraft:dark_oak_fence_gate", 186);
			this.put("minecraft:dark_oak_stairs", 164);
			this.put("minecraft:quartz_block", 155);
			this.put("minecraft:dispenser", 23);
			this.put("minecraft:bone", 352);
			this.put("minecraft:coal_ore", 16);
			this.put("minecraft:fence", 85);
			this.put("minecraft:jungle_door", 429);
			this.put("minecraft:stone_hoe", 291);
			this.put("minecraft:bucket", 325);
			this.put("minecraft:bread", 297);
			this.put("minecraft:iron_ingot", 265);
			this.put("minecraft:tnt_minecart", 407);
			this.put("minecraft:planks", 5);
			this.put("minecraft:rotten_flesh", 367);
			this.put("minecraft:iron_hoe", 292);
			this.put("minecraft:carrot", 391);
			this.put("minecraft:torch", 50);
			this.put("minecraft:record_chirp", 2259);
			this.put("minecraft:command_block_minecart", 422);
			this.put("minecraft:diamond_boots", 313);
			this.put("minecraft:flower_pot", 390);
			this.put("minecraft:acacia_door", 430);
			this.put("minecraft:brick_stairs", 108);
			this.put("minecraft:quartz_ore", 153);
			this.put("minecraft:record_blocks", 2258);
			this.put("minecraft:diamond_horse_armor", 419);
			this.put("minecraft:leather_boots", 301);
			this.put("minecraft:spruce_door", 427);
			this.put("minecraft:blaze_rod", 369);
			this.put("minecraft:diamond_chestplate", 311);
			this.put("minecraft:furnace_minecart", 343);
			this.put("minecraft:cobblestone", 4);
			this.put("minecraft:heavy_weighted_pressure_plate", 148);
			this.put("minecraft:spawn_egg", 383);
			this.put("minecraft:coal_block", 173);
			this.put("minecraft:redstone_block", 152);
			this.put("minecraft:writable_book", 386);
			this.put("minecraft:golden_helmet", 314);
			this.put("minecraft:snowball", 332);
			this.put("minecraft:wooden_slab", 126);
			this.put("minecraft:command_block", 137);
			this.put("minecraft:sandstone", 24);
			this.put("minecraft:record_stal", 2263);
			this.put("minecraft:packed_ice", 174);
			this.put("minecraft:lit_pumpkin", 91);
			this.put("minecraft:chainmail_boots", 305);
			this.put("minecraft:red_flower", 38);
			this.put("minecraft:emerald_block", 133);
			this.put("minecraft:potato", 392);
			this.put("minecraft:water_bucket", 326);
			this.put("minecraft:iron_bars", 101);
			this.put("minecraft:record_ward", 2265);
			this.put("minecraft:sponge", 19);
			this.put("minecraft:lapis_ore", 21);
			this.put("minecraft:mycelium", 110);
			this.put("minecraft:golden_sword", 283);
			this.put("minecraft:egg", 344);
			this.put("minecraft:anvil", 145);
			this.put("minecraft:fermented_spider_eye", 376);
			this.put("minecraft:diamond_helmet", 310);
			this.put("minecraft:stone_pickaxe", 274);
			this.put("minecraft:obsidian", 49);
			this.put("minecraft:poisonous_potato", 394);
			this.put("minecraft:red_mushroom", 40);
			this.put("minecraft:enchanted_book", 403);
			this.put("minecraft:repeater", 356);
			this.put("minecraft:cooked_rabbit", 412);
			this.put("minecraft:spider_eye", 375);
			this.put("minecraft:iron_pickaxe", 257);
			this.put("minecraft:gold_ingot", 266);
			this.put("minecraft:clay", 82);
			this.put("minecraft:leather", 334);
			this.put("minecraft:diamond_leggings", 312);
			this.put("minecraft:golden_chestplate", 315);
			this.put("minecraft:record_mellohi", 2262);
			this.put("minecraft:fish", 349);
			this.put("minecraft:emerald_ore", 129);
			this.put("minecraft:cookie", 357);
		}
	};
	
	private static final Map<Integer, ProtocolData> CACHED_PROTOCOL_DATA = new ConcurrentHashMap<>();

	private TIntSet ignoredServerboundPackets;
	private TIntSet ignoredClientboundPackets;
	
	private TIntSet confirmPackets;
	
	private ByteBuf confirmTransactionPrevClientbound;
	private ByteBuf confirmTransactionClientbound;

	private ByteBuf forgeChannelsClientbound;
	private ByteBuf channelsClientbound;
	
	@Getter private boolean hasForgeSupport;
	
	private ByteBuf forgeHelloClientbound;
	private ByteBuf forgeModListClientbound;
	private ByteBuf forgeRegistryPotionsClientbound;
	private ByteBuf forgeRegistryVillagerProfessionsClientbound;
	private ByteBuf forgeRegistryBlocksClientbound;
	private ByteBuf forgeRegistryItemsClientbound;
	private ByteBuf forgeResetClientbound;

	@Getter private int joinPacketClientbound;
	@Getter private int pluginMessageClientbound;
	@Getter private int changeGameStateClientbound;
	@Getter private int chunkDataClientbound;
	
	@Getter private Integer animationClientbound;
	@Getter private Integer chunkDataBulkClientbound;

	@Getter private int confirmTransactionServerbound;
	@Getter private int pluginMessageServerbound;
	@Getter private int clickWindowServerbound;
	@Getter private int blockPlacementServerbound;
	
	public ProtocolData(int protocolVersion)
	{
		this.ignoredServerboundPackets = ProtocolData.getServerboundIgnoredPackets(protocolVersion);
		this.ignoredClientboundPackets = ProtocolData.getClientboundIgnoredPackets(protocolVersion);
		
		this.confirmPackets = ProtocolData.getConfirmPackets(protocolVersion);
		
		this.confirmTransactionPrevClientbound = ProtocolData.getConfirmPacketPrev(protocolVersion);
		this.confirmTransactionClientbound = ProtocolData.getConfirmPacket(protocolVersion);

		this.forgeChannelsClientbound = ProtocolData.getForgeChannels(protocolVersion);
		this.channelsClientbound = ProtocolData.getChannels(protocolVersion);
		
		if (protocolVersion == 47)
		{
			this.hasForgeSupport = true;
			
			this.forgeHelloClientbound = ProtocolData.getForgeHello(protocolVersion);
			this.forgeModListClientbound = ProtocolData.getForgeModList(protocolVersion);
			this.forgeRegistryPotionsClientbound = ProtocolData.getForgeRegistryPotions(protocolVersion);
			this.forgeRegistryVillagerProfessionsClientbound = ProtocolData.getForgeRegistryVillagerProfessions(protocolVersion);
			this.forgeRegistryBlocksClientbound = ProtocolData.getForgeRegistryBlocks(protocolVersion);
			this.forgeRegistryItemsClientbound = ProtocolData.getForgeRegistryItems(protocolVersion);
			this.forgeResetClientbound = ProtocolData.getForgeReset(protocolVersion);
		}

		this.joinPacketClientbound = Packet.JOIN_PACKET.getClientboundId(protocolVersion);
		this.pluginMessageClientbound = Packet.PLUGIN_MESSAGE.getClientboundId(protocolVersion);
		this.changeGameStateClientbound = Packet.CHANGE_GAME_STATE.getClientboundId(protocolVersion);
		this.chunkDataClientbound = Packet.CHUNK_DATA.getClientboundId(protocolVersion);
		
		this.animationClientbound = Packet.ANIMATION.getClientboundId(protocolVersion);
		this.chunkDataBulkClientbound = Packet.MAP_CHUNK_BULK.getClientboundId(protocolVersion);

		this.confirmTransactionServerbound = Packet.CONFIRM_TRANSACTION.getServerboundId(protocolVersion);
		this.clickWindowServerbound = Packet.CLICK_WINDOW.getServerboundId(protocolVersion);
		this.blockPlacementServerbound = Packet.PLAYER_BLOCK_PLACEMENT.getServerboundId(protocolVersion);
		this.pluginMessageServerbound = Packet.PLUGIN_MESSAGE.getServerboundId(protocolVersion);
	}
	
	public boolean shouldIgnoreServerbound(int packetId)
	{
		return this.ignoredServerboundPackets.contains(packetId);
	}
	
	public boolean shouldIgnoreClientbound(int packetId)
	{
		return this.ignoredClientboundPackets.contains(packetId);
	}
	
	public boolean shouldConfirm(int packetId)
	{
		return this.confirmPackets.contains(packetId);
	}
	
	public ByteBuf getConfirmTransactionPrevClientbound()
	{
		return this.confirmTransactionPrevClientbound.slice();
	}
	
	public ByteBuf getConfirmTransactionClientbound()
	{
		return this.confirmTransactionClientbound.slice();
	}
	
	public ByteBuf getForgeChannelsClientbound()
	{
		return this.forgeChannelsClientbound.slice();
	}
	
	public ByteBuf getChannelsClientbound()
	{
		return this.channelsClientbound.slice();
	}
	
	public ByteBuf getForgeHelloClientbound()
	{
		return this.forgeHelloClientbound.slice();
	}

	public ByteBuf getForgeModListClientbound()
	{
		return this.forgeModListClientbound.slice();
	}

	public ByteBuf getForgeRegistryPotionsClientbound()
	{
		return this.forgeRegistryPotionsClientbound.slice();
	}

	public ByteBuf getForgeRegistryVillagerProfessionsClientbound()
	{
		return this.forgeRegistryVillagerProfessionsClientbound.slice();
	}

	public ByteBuf getForgeRegistryBlocksClientbound()
	{
		return this.forgeRegistryBlocksClientbound.slice();
	}

	public ByteBuf getForgeRegistryItemsClientbound()
	{
		return this.forgeRegistryItemsClientbound.slice();
	}

	public ByteBuf getForgeResetClientbound()
	{
		return this.forgeResetClientbound.slice();
	}
	
	private static TIntSet getServerboundIgnoredPackets(int protocolVersion)
	{
		TIntSet packets = new TIntHashSet();
		
		for(Packet packet : ProtocolData.IGNORED_PACKETS)
		{
			Integer id = packet.getServerboundId(protocolVersion);
			if (id != null)
			{
				packets.add(id);
			}
		}
	
		return packets;
	}
	
	private static TIntSet getClientboundIgnoredPackets(int protocolVersion)
	{
		TIntSet packets = new TIntHashSet();
		
		for(Packet packet : ProtocolData.IGNORED_PACKETS)
		{
			Integer id = packet.getClientboundId(protocolVersion);
			if (id != null)
			{
				packets.add(id);
			}
		}
			
		return packets;
	}
	
	private static TIntSet getConfirmPackets(int protocolVersion)
	{
		TIntSet packets = new TIntHashSet();
		
		for(Packet packet : ProtocolData.CONFIRM_PACKETS)
		{
			Integer id = packet.getClientboundId(protocolVersion);
			if (id != null)
			{
				packets.add(id);
			}
		}
		
		//On protocol 79 there was new packet added called "Teleport Confirm"
		//If this packet is not found, add player position and look to confirmed packets
		if (Packet.TELEPORT_CONFIRM.getServerboundId(protocolVersion) == null)
		{
			Integer playerPosAndLookId = Packet.PLAYER_POSITION_AND_LOOK.getClientboundId(protocolVersion);
			if (playerPosAndLookId != null)
			{
				packets.add(playerPosAndLookId);
			}
		}
		
		return packets;
	}
	
	private static ByteBuf getConfirmPacketPrev(int protocolVersion)
	{
		ByteBuf buf = Unpooled.buffer(5);
		
		ByteBufUtils.writeVarInt(buf, Packet.CONFIRM_TRANSACTION.getClientboundId(protocolVersion)); //Packet id
		
		buf.writeByte(0); //User inventory
		buf.writeShort(-5); //Action number
		buf.writeBoolean(false); //Accepted
		
		return buf;
	}
	
	private static ByteBuf getConfirmPacket(int protocolVersion)
	{
		ByteBuf buf = Unpooled.buffer(5);
			
		ByteBufUtils.writeVarInt(buf, Packet.CONFIRM_TRANSACTION.getClientboundId(protocolVersion)); //Packet id
		
		buf.writeByte(0); //User inventory
		buf.writeShort(-3); //Action number
		buf.writeBoolean(false); //Accepted
		
		return buf;
	}
	
	private static ByteBuf getForgeChannels(int protocolVersion)
	{
		final String[] channels = new String[]
		{
			"FML|HS",
			"FML",
			"FML|MP",
			"FML",
			"FORGE"
		};
		
		return ProtocolData.generateChannels(protocolVersion, channels);
	}
	
	private static ByteBuf getChannels(int protocolVersion)
	{
		final String[] channels = new String[]
		{
			//Lunar
			"Lunar-Client",
			
			//Labymod
			"LABYMOD", //Deprecated
			"LMC",
			
			//Badlion
			"badlion:mods"
		};
		
		return ProtocolData.generateChannels(protocolVersion, channels);
	}
	
	private static ByteBuf generateChannels(int protocolVersion, String[] channels)
	{
		final byte[] channelBytes = String.join("\0", channels).getBytes(Charsets.UTF_8);
		
		ByteBuf buf = Unpooled.buffer();
			
		ByteBufUtils.writeVarInt(buf, Packet.PLUGIN_MESSAGE.getClientboundId(protocolVersion)); //Packet id
		
		ByteBufUtils.writeString(buf, protocolVersion >= 385 ? "minecraft:register" : "REGISTER");
		buf.writeBytes(channelBytes);
		
		return buf;
	}
	
	private static ByteBuf getForgeHello(int protocolVersion)
	{
		ByteBuf buf = Unpooled.buffer();
		
		ByteBufUtils.writeVarInt(buf, Packet.PLUGIN_MESSAGE.getClientboundId(protocolVersion)); //Packet id
		ByteBufUtils.writeString(buf, "FML|HS");
		buf.writeByte(0); //Id
		buf.writeByte(0); //Protocol version
		
		return buf;
	}
	
	private static ByteBuf getForgeModList(int protocolVersion)
	{
		ByteBuf buf = Unpooled.buffer();
			
		ByteBufUtils.writeVarInt(buf, Packet.PLUGIN_MESSAGE.getClientboundId(protocolVersion)); //Packet id
		ByteBufUtils.writeString(buf, "FML|HS");
		buf.writeByte(2); //Id
		ByteBufUtils.writeVarInt(buf, 0); //Count
		
		return buf;
	}
	
	private static ByteBuf getRegistryPacketStart(int protocolVersion, boolean hasMore)
	{
		ByteBuf buf = Unpooled.buffer();
		
		ByteBufUtils.writeVarInt(buf, Packet.PLUGIN_MESSAGE.getClientboundId(protocolVersion)); //Packet id
		ByteBufUtils.writeString(buf, "FML|HS");
		buf.writeByte(3); //Id
		buf.writeBoolean(hasMore);
		
		return buf;
	}
	
	private static void writeForgeRegistryIds(ByteBuf buffer, Map<String, Integer> ids)
	{
		ByteBufUtils.writeVarInt(buffer, ids.size());
		
		for(Entry<String, Integer> id : ids.entrySet())
		{
			ByteBufUtils.writeString(buffer, id.getKey());
			ByteBufUtils.writeVarInt(buffer, id.getValue());
		}
	}

	private static ByteBuf getForgeRegistryPotions(int protocolVersion)
	{
		ByteBuf buf = ProtocolData.getRegistryPacketStart(protocolVersion, true);
		ByteBufUtils.writeString(buf, "minecraft:potions");
		ProtocolData.writeForgeRegistryIds(buf, ProtocolData.FORGE_POTIONS_IDS);
		
		ByteBufUtils.writeVarInt(buf, 0); //Substitutions
		
		return buf;
	}

	private static ByteBuf getForgeRegistryVillagerProfessions(int protocolVersion)
	{
		ByteBuf buf = ProtocolData.getRegistryPacketStart(protocolVersion, true);
		ByteBufUtils.writeString(buf, "minecraft:villagerprofessions");
		ProtocolData.writeForgeRegistryIds(buf, ProtocolData.FORGE_VILLAGER_PROFESSIONS_IDS);
		
		ByteBufUtils.writeVarInt(buf, 0); //Substitutions
		
		return buf;
	}

	private static ByteBuf getForgeRegistryBlocks(int protocolVersion)
	{
		ByteBuf buf = ProtocolData.getRegistryPacketStart(protocolVersion, true);
		ByteBufUtils.writeString(buf, "minecraft:blocks");
		ProtocolData.writeForgeRegistryIds(buf, ProtocolData.FORGE_BLOCKS_IDS);
		
		ByteBufUtils.writeVarInt(buf, 0); //Substitutions
		
		return buf;
	}
	
	private static ByteBuf getForgeRegistryItems(int protocolVersion)
	{
		ByteBuf buf = ProtocolData.getRegistryPacketStart(protocolVersion, false);
		ByteBufUtils.writeString(buf, "minecraft:items");
		ProtocolData.writeForgeRegistryIds(buf, ProtocolData.FORGE_ITEMS_IDS);
		
		ByteBufUtils.writeVarInt(buf, 0); //Substitutions
		
		return buf;
	}
	
	private static ByteBuf getForgeReset(int protocolVersion)
	{
		ByteBuf buf = Unpooled.buffer();
			
		ByteBufUtils.writeVarInt(buf, Packet.PLUGIN_MESSAGE.getClientboundId(protocolVersion)); //Packet id
		ByteBufUtils.writeString(buf, "FML|HS");
		buf.writeByte(-2); //Id
			
		return buf;
	}
	
	public static ProtocolData getProtocolData(int protocolVersion)
	{
		ProtocolData data = ProtocolData.CACHED_PROTOCOL_DATA.get(protocolVersion);
		if (data == null)
		{
			data = new ProtocolData(protocolVersion);
			
			ProtocolData old = ProtocolData.CACHED_PROTOCOL_DATA.putIfAbsent(protocolVersion, data);
			if (old != null)
			{
				return old;
			}
		}
		
		return data;
	}
}
