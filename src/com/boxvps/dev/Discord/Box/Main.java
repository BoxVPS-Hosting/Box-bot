package com.boxvps.dev.Discord.Box;

import com.boxvps.dev.Discord.Box.events.*;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

public class Main {
	
	private static String token = System.console().readLine("Insert Token  \n");
	private static JDA jda;
	
	public static void main(String[] args) throws Exception {
		jda = new JDABuilder(AccountType.BOT).setToken(token).build();
		jda.getPresence().setPresence(OnlineStatus.ONLINE, Game.playing("on the development stream server!"));

		jda.addEventListener(new Help());
	}

}
