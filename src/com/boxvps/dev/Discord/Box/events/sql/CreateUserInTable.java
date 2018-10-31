package com.boxvps.dev.Discord.Box.events.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CreateUserInTable extends ListenerAdapter{

    Connection sqlCon;
    
    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        
        try{
        Class.forName("com.mysql.jdbc.Driver");
                sqlCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/Box", "root", "PUT PASSWORD HERE");
                Statement sqlStatement = sqlCon.createStatement();

                sqlStatement.execute("INSERT INTO `accounts`(`stamps_amount` , `discord` , `discord_id`) VALUES('0', '" + event.getMember().getUser().getName().toString() + "#" + event.getMember().getUser().getDiscriminator().toString() + "', '" + event.getMember().getUser().getId().toString()+ "')");

        }catch(Exception e){
            System.out.println(e);
        }
    }

}