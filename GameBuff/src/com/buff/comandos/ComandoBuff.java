package com.buff.comandos;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.buff.utils.BuffCooldown;
import com.buff.utils.ConfigManager;
import com.buff.utils.MessageManager;
import com.gsoares.buff.Main;

public class ComandoBuff implements CommandExecutor{

	FileConfiguration config = ConfigManager.getConfig("settings");
	int cooldownTime = config.getInt("Configuracao.Cooldown");
	
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("buff")) {
			if(sender instanceof Player) {
				Player p = (Player)sender;
				if(p.hasPermission("buff.use")) {
					UUID uuid = p.getUniqueId();
					if(BuffCooldown.verifyPlayer(uuid)) {
						int cooldownInt = BuffCooldown.getCooldown(uuid);
						String cooldownString = cooldownInt + "";
						p.sendMessage(MessageManager.getMensagem("TempoCooldown").replace("$cooldown", cooldownString));				
					} else {
						int valorBuff = config.getInt("Configuracao.Valor");
						if(!Main.getEconomy().hasAccount(p)) return false;
						if(Main.getEconomy().getBalance(p) >= valorBuff) {
							removePotionEffects(p);
							p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 120*20, 0));
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30*20, 4));
							p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300*20, 0));
							p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 300*20, 0));
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 90*20, 1));
							p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 90*20, 1));
							
							Main.getEconomy().withdrawPlayer(p, valorBuff);
							BuffCooldown.setCooldown(uuid, cooldownTime);
							
							p.sendMessage(MessageManager.getMensagem("BuffAplicado").replace("$valor", valorBuff + ""));
							
							if(config.getString("Servidor.AnunciarAoAplicarBuff").equalsIgnoreCase("ativado")) {
								for(Player ps : Bukkit.getOnlinePlayers()) {
									if(ps != p) {
										ps.sendMessage(MessageManager.getMensagem("AnuncioGlobal").replace("$nick", p.getDisplayName()));
									}
								}
							}
						} else {
							p.sendMessage(MessageManager.getMensagem("SemDinheiro").replace("$valor", valorBuff + ""));
						}
					}
				} else {
					p.sendMessage(MessageManager.getMensagem("SemPermissao"));
				}
			}
		}
		return false;
	}
	
	public void removePotionEffects(Player player) {
	    for (PotionEffect effect : player.getActivePotionEffects()) {
	        player.removePotionEffect(effect.getType());
	    }
	}
}
