package com.tcore.commands;

import com.google.common.reflect.ClassPath;
import com.tcore.TCore;
import com.tcore.api.TManager;
import com.tcore.managers.TitansManager;
import com.tcore.utils.ReflectionUtil;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class CommandManager extends TitansManager {

    private Plugin plugin;

    public CommandManager(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void disable() {

    }

    public void enable(){
        try {
            ClassPath path = ClassPath.from(plugin.getClass().getClassLoader());
            for (ClassPath.ClassInfo info : path.getTopLevelClassesRecursive("com.tcore.commands")) {
                Class classz = Class.forName(info.getName(), true, plugin.getClass().getClassLoader());
                if (classz != null && !classz.isAnnotation() && !classz.isEnum() && FineCommand.class.isAssignableFrom(classz) && !classz.equals(FineCommand.class)) {
                    Constructor<?> constructor = ReflectionUtil.getConstructor(classz, TCore.class);
                    if (constructor != null) {
                        ReflectionUtil.newConstructorInstance(constructor, plugin);
                    }
                }
            }
        } catch (Exception ew) {
            ew.printStackTrace();
        }
    }


    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList();
        while (resources.hasMoreElements()) {
            URL resource = (URL) resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList classes = new ArrayList();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return (Class[]) classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List findClasses(File directory, String packageName) throws ClassNotFoundException {
        List classes = new ArrayList();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public enum CommandType {
        PLAYER, CONSOLE, ALL
    }

}
