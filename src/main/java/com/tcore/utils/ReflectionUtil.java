package com.tcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @author Mr_Replete
 */
public class ReflectionUtil {

    /**
     * Gets net.minecraft.server... class
     * @param name of the class
     * @return Class
     */
    public static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets net.minecraft.server... class
     * @param name of the cb class
     * @return Class
     */
    public static Class<?> getCraftBukkitClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get class constructor by classname and ClassTypes parms
     * @param className
     * @param parms
     * @return
     * @throws NoSuchMethodException
     */
    public static Constructor<?> getConstructor(Class<?> className, Class<?>... parms) throws NoSuchMethodException {
        return className.getConstructor(parms);
    }

    /**
     * Get new Instance class by constructor and ClassTypes of parms
     * @param constructor
     * @param parms
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static Object newConstructorInstance(Constructor<?> constructor, Object... parms) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException {
        return constructor.newInstance(parms);
    }

    /**
     * Get new Instance class by empty constructor
     * @param constructor
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @see #newConstructorInstance(Constructor, Object...)
     */
    public static Object newConstructorInstance(Constructor<?> constructor) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException {
        return constructor.newInstance();
    }

    /**
     * Get Method by className, methodName and ClassTypes of parms
     * @param className
     * @param name
     * @param parms
     * @return
     * @throws NoSuchMethodException
     */
    public static Method getMethod(Class<?> className, String name, Class<?>... parms) throws NoSuchMethodException {
        return className.getMethod(name,parms);
    }

    /**
     * Get Method by className, methodName and ClassTypes of parms
     * @param className
     * @param name
     * @return
     * @throws NoSuchMethodException
     * @see #getConstructor(Class, Class[])
     */
    public static Method getMethod(Class<?> className, String name) throws NoSuchMethodException {
        return className.getMethod(name);
    }

    /**
     * Get Object of invoking method given by Class Instance object, method, and ObjectTypes parms
     * @param instance
     * @param method
     * @param parms
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static Object invokeMethod(Object instance, Method method, Object... parms) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return method.invoke(instance,parms);
    }

    /**
     * Get Object of invoking method given by Class Instance object, method
     * @param instance
     * @param method
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @see #invokeMethod(Object, Method, Object...)
     */
    public static Object invokeMethod(Object instance, Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return method.invoke(instance);
    }

    /**
     * Get class's field by className and fieldMethod
     * @param className
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     */
    public static Field getField(Object className, String fieldName) throws NoSuchFieldException {
        Field field = className.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    /**
     * Get class's field value by Object classname, and field name with given Field
     * @param className
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @see #getField(Object, String)
     */
    public static Object getFieldValue(Object className, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getField(className,fieldName).get(className);
    }

    /**
     * Set field value in class by field name, classname and newValue
     * @param field
     * @param className
     * @param value
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @see #getField(Object, String)
     */
    public static void setFieldValue(String field, Object className, Object value) throws NoSuchFieldException, IllegalAccessException {
        getField(className,field).set(className, value);
    }

    /**
     * Send packet to single player
     * @param packet
     * @param player
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     */
    public static void sendPacket(Object packet, Player player)  throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Object playerConnection = getFieldValue(getHandle(player),"playerConnection");
        invokeMethod(playerConnection,getMethod(playerConnection.getClass(),"sendPacket", getNMSClass("Packet")),packet);
    }

    /**
     * Get EntityPlayer class
     * @param player
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static Object getHandle(Player player) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return invokeMethod(player, getMethod(player.getClass(),"getHandle"));
    }


}