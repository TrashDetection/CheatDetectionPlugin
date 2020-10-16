package net.goldtreeservers.cheatdetectionplugin.common.user.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChunkSection
{
	public static final int COUNT = 16;
	public static final int SIZE = 16 * 16 * 16;
	
	private final short[] blocks;
	
	private List<Short> paletta;
	private Map<Short, Short> paletteMap;
	
	public ChunkSection()
	{
		this.blocks = new short[ChunkSection.SIZE];
		
		this.paletta = new ArrayList<>();
		this.paletteMap = new HashMap<>();
		
		this.addPaletteEntry((short)0); //Air
	}
	
	public void addPaletteEntry(short mask)
	{
		this.paletteMap.put(mask, (short)this.paletteMap.size());
		this.paletta.add(mask);
	}
	
	public void setBlock(int idx, short mask)
	{
		Short index = this.paletteMap.get(mask);
		if (index == null)
		{
			this.paletteMap.put(mask, index = (short)this.paletteMap.size());
			this.paletta.add(mask);
		}
		
		this.blocks[idx] = mask;
	}
	
    public int getPaletteSize()
    {
        return this.paletteMap.size();
    }
    
    public short getBlock(int idx)
    {
    	return this.blocks[idx];
    }
    
    public Collection<Short> getPaletta()
    {
    	return this.paletta;
    }
}
