package net.goldtreeservers.cheatdetectionplugin.bungee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.yaml.snakeyaml.Yaml;

import com.google.common.base.Preconditions;

import net.goldtreeservers.cheatdetectionplugin.bungee.commands.CheatDetectionCommand;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginClassloader;
import net.md_5.bungee.api.plugin.PluginDescription;
import net.md_5.bungee.api.plugin.PluginManager;

public class BungeeCheatDetectionPluginLoader extends Plugin
{
	private BungeeCheatDetectionPlugin plugin;
	
	public BungeeCheatDetectionPluginLoader()
	{
		this.plugin = new BungeeCheatDetectionPlugin(this);
	}
	
	@Override
	public void onLoad()
	{
		if (this.plugin != null)
		{
			this.plugin.onLoad();
		}
	}
	
	@Override
	public void onEnable()
	{
		this.registerCommands(this.plugin);
	}
	
	public void registerCommands(BungeeCheatDetectionPlugin plugin)
	{
		this.getProxy().getPluginManager().registerCommand(plugin.getLoader(), new CheatDetectionCommand(plugin));
	}
	
	@Override
	public void onDisable()
	{
		this.getProxy().getPluginManager().unregisterListeners(this);
		this.getProxy().getPluginManager().unregisterCommands(this);
		
		this.getProxy().getScheduler().cancel(this);

		if (this.plugin != null)
		{
			this.plugin.onDisable();
		
			this.plugin = null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void unloadAndDisable()
	{
		URLClassLoader classLoader = (URLClassLoader)this.getClass().getClassLoader();
		
		try
		{
			classLoader.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException("Failed to unload the plugin", e);
		}
		
		try
		{
			Field field = PluginClassloader.class.getDeclaredField("allLoaders");
			field.setAccessible(true);
			
			Set<PluginClassloader> loaders = (Set<PluginClassloader>)field.get(null);
			
			loaders.remove(classLoader);
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
		{
		}
		
		this.onDisable();
	}
	
	@SuppressWarnings("unchecked")
	public void loadPlugin()
	{
		File file = this.getFile();
		
		Yaml yaml;
		Map<String, Plugin> plugins;
		try
		{
			Field field = PluginManager.class.getDeclaredField("yaml");
			field.setAccessible(true);
			
			yaml = (Yaml)field.get(this.getProxy().getPluginManager());
			
			Field pluginsField = PluginManager.class.getDeclaredField("plugins");
			pluginsField.setAccessible(true);
			
			plugins = (Map<String, Plugin>)pluginsField.get(this.getProxy().getPluginManager());
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		
		Method initField;
		try
		{
			initField = Plugin.class.getDeclaredMethod("init", ProxyServer.class, PluginDescription.class);
			initField.setAccessible(true);
		}
		catch (SecurityException | NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		}
		
		try (JarFile jar = new JarFile(file))
        {
            JarEntry bungeeEntry = jar.getJarEntry("bungee.yml");

            Preconditions.checkNotNull(bungeeEntry, "Plugin is missing bungee.yml");

            try (InputStream in = jar.getInputStream(bungeeEntry))
            {
                PluginDescription desc = yaml.loadAs(in, PluginDescription.class);
                
                Preconditions.checkNotNull(desc.getName(), "Plugin is missing name");
                Preconditions.checkNotNull(desc.getMain(), "Plugin is missing main", file);

                desc.setFile(file);
                
                PluginClassloader loader = new PluginClassloader(new URL[]
                {
                    file.toURI().toURL()
                });
                
                try
                {
	                Class<?> main = loader.loadClass(desc.getMain());
	                
	                Plugin clazz = (Plugin)main.getDeclaredConstructor().newInstance();
	
	                initField.invoke(clazz, this.getProxy(), desc);
	                
	                plugins.put(desc.getName(), clazz);
	                
	                clazz.onLoad();
	                clazz.onEnable();
	        		
	    			Field field = PluginClassloader.class.getDeclaredField("allLoaders");
	    			field.setAccessible(true);
	    			
	    			Set<PluginClassloader> loaders = (Set<PluginClassloader>)field.get(null);
	    			
	    			loaders.add(loader);
                }
                catch(Throwable e)
                {
                	loader.close();

        			throw new RuntimeException("Failed to enable the plugin", e);
                }
            }
        }
		catch (Exception e)
        {
			throw new RuntimeException("Failed to load the plugin", e);
        }
	}
}
