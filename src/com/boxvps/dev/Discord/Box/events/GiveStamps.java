package com.boxvps.dev.Discord.Box.events;

import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.boxvps.dev.Discord.Box.Info;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class GiveStamps extends ListenerAdapter {
    
    Integer oldCount;
    Integer newCount;
    Connection sqlCon;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args.length >= 15){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                sqlCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/Box", "root", "PUT PASSWORD HERE");
                Statement sqlStatement = sqlCon.createStatement();

                ResultSet sqlResult = sqlStatement.executeQuery("SELECT `stamps` FROM `accounts` WHERE `discord_id`='" + event.getMember().getUser().getId().toString() + "'");

                while(sqlResult.next())oldCount = sqlResult.getInt(1);
                
                newCount = oldCount + 1;

                sqlStatement.execute("UPDATE `accounts` SET `statmps_amount`='" + newCount + "', `discord`='" + event.getMember().getUser().getName().toString() + "#" + event.getMember().getUser().getDiscriminator().toString() + "' WHERE `discord_id`='" + event.getMember().getUser().getId().toString() + "'");
                sqlCon.close();

                EmbedBuilder eb = new EmbedBuilder();

                eb.setAuthor("New Stamp!", event.getMember().getUser().getAvatarUrl() , event.getMember().getUser().getAvatarUrl());
                eb.setColor(Info.LIME_GREEN);
                eb.setDescription("Congratulations " + event.getMember().getAsMention() + ", you just gained a stamp! \n Your current stamp count is **" + newCount + "**");
                eb.setFooter("Box Stamp Counter", Info.LOGO);

               //event.getChannel().sendTyping().queue( ( channel ) -> {
                    event.getChannel().sendMessage(eb.build()).queue();
               // });


            }catch(Exception e) {
                System.out.println(e);
            }
        }


    }

}