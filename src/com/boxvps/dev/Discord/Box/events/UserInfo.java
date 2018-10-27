package com.boxvps.dev.Discord.Box.events;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import com.boxvps.dev.Discord.Box.Info;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class UserInfo extends ListenerAdapter {

    Connection sqlCon;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split(" ");
        
        if(args[0].equalsIgnoreCase(Info.PREFIX + "userinfo") || args[0].equalsIgnoreCase(Info.PREFIX + "ui")){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                sqlCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/box", "root", "PUT PASSWORD HERE");
                Statement sqlStatement = sqlCon.createStatement();
                ResultSet sqlResult = sqlStatement.executeQuery("SELECT * FROM `" + event.getAuthor().getId() + "`");
                
                EmbedBuilder eb = new EmbedBuilder();

                eb.setTitle(":clipboard: User Information");
                eb.setColor(Info.SUNSET);
                while(sqlResult.next())eb.setDescription(":white_small_square: User Information for " + event.getAuthor().getAsMention() + "\n :white_small_square:  User Name: " + sqlResult.getString(2) + "\n :white_small_square: User ID: " + sqlResult.getInt(1) + "\n :white_small_square: User Age: " + sqlResult.getString(3));
                eb.setFooter("Box User Information", Info.LOGO);

                event.getAuthor().openPrivateChannel().queue((channel) -> {
                    channel.sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(60, TimeUnit.SECONDS);
                    });
                });

                sqlCon.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

}