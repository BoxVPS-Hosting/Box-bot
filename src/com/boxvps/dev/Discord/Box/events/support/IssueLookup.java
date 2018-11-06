package com.boxvps.dev.Discord.Box.events.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import com.boxvps.dev.Discord.Box.Info;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class IssueLookup extends ListenerAdapter {

    Connection sqlCon;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "lookupissue") || args[0].equalsIgnoreCase(Info.PREFIX + "li")) {
            if (event.getChannel().getId().equals("507745426091278359")) {
                if (args.length < 2) {
                    EmbedBuilder nullArgs1 = new EmbedBuilder();

                    nullArgs1.setColor(Info.ERROR_RED);
                    nullArgs1.setDescription("You didn't specify which issue to lookup! \n Proper Formats: \n"
                            + Info.PREFIX + "lookupissue [issue_id] \n " + Info.PREFIX + "li [issue_id]");

                    event.getChannel().sendMessage(nullArgs1.build()).queue((message) -> {
                        message.delete().queueAfter(15, TimeUnit.SECONDS);
                    });
                } else {
                    
                    Integer idToLookup = Integer.parseInt(args[1]);

                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        sqlCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/Box", "root",
                                "PUT PASSWORD HERE");
                        Statement sqlStatement = sqlCon.createStatement();

                        ResultSet sqlResult = sqlStatement
                                .executeQuery("SELECT * FROM `issues` WHERE `issue_id`=" + idToLookup);
                        
                        if(sqlResult.next() == true){
                            
                            Integer boxID = sqlResult.getInt(4);
                            String boxUsername = sqlResult.getString(5);
                            String  discordID = sqlResult.getString(2);
                            String discordUsername = sqlResult.getString(3);
                            String issueType = sqlResult.getString(6);
                            String issueDetails = sqlResult.getString(7);
                            issueDetails = issueDetails.substring(0, 2048);


                            EmbedBuilder eb = new EmbedBuilder();
                            eb.setAuthor("Issue Details | ID:" + idToLookup, "https://boxvps.tk/issues/issue/" + idToLookup, event.getGuild().getMemberById(discordID).getUser().getAvatarUrl());
                            eb.addField("Username:" , boxUsername , true);
                            eb.addField("Customer ID:" , "" + boxID, true);
                            eb.addField("Discord Username:", discordUsername , true);
                            eb.addField("Discord ID:", discordID , true);
                            eb.addField("Support Request Type" , issueType , false);
                            eb.addField("Issue Details" , issueDetails , false);

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        }

    }
}