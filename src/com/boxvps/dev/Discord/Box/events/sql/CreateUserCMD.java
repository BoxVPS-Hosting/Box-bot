package com.boxvps.dev.Discord.Box.events.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.boxvps.dev.Discord.Box.Info;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CreateUserCMD extends ListenerAdapter {
	
	Connection sqlCon;
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		if(args[0].equalsIgnoreCase(Info.PREFIX + "createuser") ||args[0].equalsIgnoreCase(Info.PREFIX + "cu")) {
			if(event.getMember().getUser().getId().equals("79693184417931264") || event.getMember().getUser().getId().equals("237768953739476993")) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
	                sqlCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/Box", "root", "PUT PASSWORD HERE");
	                Statement sqlStatement = sqlCon.createStatement();

	                sqlStatement.execute("INSERT INTO `accounts`(`stamps_amount` , `discord` , `discord_id`) VALUES('0', '" + event.getMessage().getMentionedMembers().get(0).getUser().getName().toString() + "#" + event.getMessage().getMentionedMembers().get(0).getUser().getDiscriminator().toString() + "', '" + event.getMessage().getMentionedMembers().get(0).getUser().getId().toString()+ "')");
				}catch(Exception e) {
					System.out.println(e);
				}
			}
		}
	}

}
