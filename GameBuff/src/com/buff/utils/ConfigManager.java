package com.buff.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.gsoares.buff.Main;

public class ConfigManager {
	
	static Plugin plugin = Main.getPlugin(Main.class);
	
	public static void createConfig(String file) {
		if (!new File(plugin.getDataFolder(), file + ".yml").exists()) {
			plugin.saveResource(file + ".yml", false); 
		}
	}
	
	public static FileConfiguration getConfig(String file) {
      	try {
      		File arquivo = new File(plugin.getDataFolder() + File.separator + file + ".yml");
			InputStreamReader arquivoStream = new InputStreamReader(new FileInputStream(arquivo), Charset.forName("UTF-8").name());
			FileConfiguration config = (FileConfiguration)YamlConfiguration.loadConfiguration(arquivoStream);
			return config;
		} catch (Throwable e) {
			e.printStackTrace();
		} 
      	return null;
	}
}
