package net.goldtreeservers.cheatdetectionplugin.common.utils;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import net.jpountz.xxhash.StreamingXXHash64;
import net.jpountz.xxhash.XXHashFactory;

public class ByteBufUtils
{
	private static final XXHashFactory xxHashFactory = XXHashFactory.fastestInstance();
	
    public static int readVarInt(ByteBuf input)
    {
        return ByteBufUtils.readVarInt(input, 5);
    }

    public static int readVarInt(ByteBuf input, int maxBytes)
    {
        int out = 0;
        int bytes = 0;

        while (true)
        {
            byte in = input.readByte();

            out |= (in & 0x7F) << (bytes++ * 7);

            if (bytes > maxBytes)
            {
                throw new RuntimeException("VarInt is too big");
            }

            if ((in & 0x80) != 0x80)
            {
                break;
            }
        }

        return out;
    }
    
    public static String readString(ByteBuf buf)
    {
        int len = ByteBufUtils.readVarInt(buf);
        if (len > Short.MAX_VALUE)
        {
            throw new RuntimeException(String.format("Cannot receive string longer than %d (got %d characters)", Short.MAX_VALUE, len));
        }

        byte[] b = new byte[len];
        buf.readBytes(b);

        return new String(b, CharsetUtil.UTF_8);
    }
    
    public static UUID readUniqueId(ByteBuf buf)
    {
    	return new UUID(buf.readLong(), buf.readLong());
    }

    public static void writeVarInt(ByteBuf output, int value)
    {
        while (true)
        {
        	int part = value & 0x7F;

            value >>>= 7;
            if  (value != 0)
            {
                part |= 0x80;
            }

            output.writeByte(part);

            if (value == 0)
            {
                break;
            }
        }
    }
    
    public static void writeString(ByteBuf buf, String value)
    {
    	byte[] bytes = value.getBytes(CharsetUtil.UTF_8);
    	
    	ByteBufUtils.writeBytes(buf, bytes);
    }
    
    public static void writeUniqueId(ByteBuf buf, UUID uniqueId)
    {
    	buf.writeLong(uniqueId.getMostSignificantBits());
    	buf.writeLong(uniqueId.getLeastSignificantBits());
    }
    
    public static void writeBytes(ByteBuf buf, byte[] bytes)
    {
    	ByteBufUtils.writeVarInt(buf, bytes.length);
    	buf.writeBytes(bytes);
    }
    
    public static long writeChunkedByteArray(ByteBuf buf, ChunkedByteArray array, long seed)
    {
    	ByteBufUtils.writeVarInt(buf, Math.toIntExact(array.size64())); //If its bigger than int, there's a problem
    	
    	return ByteBufUtils.chunkedByteArrayToByteBuf(buf, array, seed);
    }
    
    //Used on tests
    public static void chunkedByteArrayToByteBuf(ByteBuf buf, ChunkedByteArray array)
    {
    	ByteBufUtils.chunkedByteArrayToByteBuf(buf, array, 0);
    }
    
    public static long chunkedByteArrayToByteBuf(ByteBuf buf, ChunkedByteArray array, long seed)
    {
    	StreamingXXHash64 hashing = ByteBufUtils.getHashing(seed);
    	
    	long left = array.size64();
		for(byte[] element : array.array())
		{
			if (element == null)
			{
				break;
			}
			
			int remaining = Math.toIntExact(Math.min(ChunkedByteArray.CHUNK_SIZE, left)); //Arrays work on int's
			
			buf.writeBytes(element, 0, remaining);
			hashing.update(element, 0, remaining);
			
			left -= ChunkedByteArray.CHUNK_SIZE;
		}
		
		return hashing.getValue();
    }
    
    private static StreamingXXHash64 getHashing(long seed)
    {
    	return ByteBufUtils.xxHashFactory.newStreamingHash64(seed);
    }
}
