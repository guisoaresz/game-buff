package com.buff.utils;

import org.bukkit.configuration.file.FileConfiguration;

public class MessageManager {
	
	static FileConfiguration config = ConfigManager.getConfig("mensagens");
	
	public static String getMensagem(String titulo) {
		String mensagem = "";
		if(titulo == "BuffAplicado") {
			mensagem = config.getString("Mensagens.BuffAplicado").replace("&", "§").replace("<br>", "\n");
		} else if(titulo == "AnuncioGlobal") {
			mensagem = config.getString("Mensagens.AnuncioGlobal").replace("&", "§").replace("<br>", "\n");
		} else if(titulo == "TempoCooldown") {
			mensagem = config.getString("Mensagens.TempoCooldown").replace("&", "§").replace("<br>", "\n");
		} else if(titulo == "SemDinheiro") {
			mensagem = config.getString("Mensagens.SemDinheiro").replace("&", "§").replace("<br>", "\n");
		} else if(titulo == "SemPermissao") {
			mensagem = config.getString("Mensagens.SemPermissao").replace("&", "§").replace("<br>", "\n");
		}
		return mensagem;
	}
}
