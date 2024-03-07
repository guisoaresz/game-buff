package com.buff.utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import com.gsoares.buff.Main;

public class BuffCooldown {
	
	private static HashMap<UUID, Integer> cooldown = new HashMap<UUID, Integer>();
	private static int cooldownTime = 0;
	
	public static boolean verifyPlayer(UUID uuid) {
		if(cooldown.containsKey(uuid)) {
			return true;
		}
		return false;
	}
	
	public static int getCooldown(UUID uuid) {
		if(cooldown.containsKey(uuid)) {
			cooldownTime = cooldown.get(uuid);
		}
		return cooldownTime;
	}

	public static void setCooldown(UUID uuid, int tempo) {
		cooldown.put(uuid, tempo);
	}
	
	public static void initCooldown() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(cooldown.isEmpty()) {
					return;
				}
				for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
					UUID uuid = p.getUniqueId();
					if(verifyPlayer(uuid)) {
						if(getCooldown(uuid) >= 1) {
							setCooldown(uuid, (getCooldown(uuid) - 1));
						} else {
							cooldown.remove(uuid);
						}
					}
				}
			}
		}.runTaskTimer(Main.getInstance(), 0, 20);
	}
}
