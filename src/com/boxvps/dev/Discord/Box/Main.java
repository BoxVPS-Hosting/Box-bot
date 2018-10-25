package com.boxvps.dev.Discord.Box;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.boxvps.dev.Discord.Box.events.*;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

public class Main {

	private static String token = System.console().readLine("Insert Token  \n");
	private static String sqlPass = System.console().readLine("MySQL Root Password \n");
	private static JDA jda;
	private static Connection sqlCon;

	public static void main(String[] args) throws Exception {
			jda = new JDABuilder(AccountType.BOT).setToken(token).build();
		    jda.getPresence().setPresence(OnlineStatus.ONLINE, Game.playing("on the development stream server!"));

	     	jda.addEventListener(new Help());
		    jda.addEventListener(new Status());
	      	jda.addEventListener(new SQLTest());
	    	//mysqlConnection(null);
	}

	public static void mysqlConnection(String[] args) throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			sqlCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/box", "root", sqlPass);
			Statement sqlStatement = sqlCon.createStatement();
			ResultSet sqlResult = sqlStatement.executeQuery("SELECT * from test");
			while (sqlResult.next())
				System.out.println(sqlResult.getInt(1) + "  " + sqlResult.getString(2) + "  " + sqlResult.getString(3));
			sqlCon.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}