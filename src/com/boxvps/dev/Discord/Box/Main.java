package com.boxvps.dev.Discord.Box;

import com.boxvps.dev.Discord.Box.events.*;
import com.boxvps.dev.Discord.Box.events.sql.*;
import com.boxvps.dev.Discord.Box.events.support.*;
import com.boxvps.dev.Discord.Box.events.moderation.*;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

public class Main {

	// Print to the console prompting the user to insert the bot's token
	private static String token = System.console().readLine("Insert Token  \n");
	// Define a JDA instance
	private static JDA jda;

	// Build the JDA Instance with the token from the previous step
	public static void main(String[] args) throws Exception {
		jda = new JDABuilder(AccountType.BOT).setToken(token).build();

		// Set the bots presence
		jda.getPresence().setPresence(OnlineStatus.ONLINE, Game.playing("on the development stream server"));

		// Add event listeners to add functionality to the bot
		jda.addEventListener(new Aliases());
		jda.addEventListener(new Ban());
		jda.addEventListener(new BotStatus());
		jda.addEventListener(new Checkin());
		jda.addEventListener(new Clear());
		jda.addEventListener(new CloseIssue());
		jda.addEventListener(new CreateUserCMD());
		jda.addEventListener(new CreateUserInTable());
		jda.addEventListener(new GiveStamps());
		jda.addEventListener(new Help());
		jda.addEventListener(new IssueLookup());
		jda.addEventListener(new Kick());
		jda.addEventListener(new Shutdown());
		jda.addEventListener(new StatusCMD());
		jda.addEventListener(new SupportVoiceCheck());
		jda.addEventListener(new Unban());

		// Print to the console the bot has finished loading
		System.out.println("Signed Sealed and Shipped!");
	}
}