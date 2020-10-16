package net.goldtreeservers.cheatdetectionplugin.common.utils;

public class ChunkedByteArrayUtils
{
    public static void writeVarInt(ChunkedByteArray array, int value)
    {
        while (true)
        {
        	int part = value & 0x7F;

            value >>>= 7;
            if  (value != 0)
            {
                part |= 0x80;
            }

            array.write((byte)part);

            if (value == 0)
            {
                break;
            }
        }
    }
}
