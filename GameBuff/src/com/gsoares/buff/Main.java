package com.gsoares.buff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.buff.comandos.ComandoBuff;
import com.buff.utils.BuffCooldown;
import com.buff.utils.ConfigManager;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin{
	
	public static Main main;
	private static Economy econ = null;
	ConsoleCommandSender cs = Bukkit.getConsoleSender();
	
	public void onEnable() {
        if (!setupEconomy() ) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		
		main = this;
		CriarArquivos();
		IniciarSistemas();
		Comandos();
		setupEconomy();
		
		cs.sendMessage(ChatColor.GREEN + "[GameBuff] Plugin foi habilitado com sucesso.");
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	public void Comandos() {
		Bukkit.getPluginCommand("buff").setExecutor(new ComandoBuff());
	}
	
	public void CriarArquivos() {
		ConfigManager.createConfig("mensagens");
		ConfigManager.createConfig("settings");
	}
	
	public void IniciarSistemas() {
		BuffCooldown.initCooldown();
	}
	
    public static Economy getEconomy() {
        return econ;
    }
	
	public static Main getInstance() {
		return main;
	}
}
