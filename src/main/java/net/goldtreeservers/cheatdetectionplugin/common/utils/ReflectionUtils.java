package net.goldtreeservers.cheatdetectionplugin.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionUtils
{
	public static void setFinalField(Field field, Object value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		ReflectionUtils.setFinalField(field, null, value);
	}
	
	public static void setFinalField(Field field, Object target, Object value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		
		field.set(target, value);
	}
}
