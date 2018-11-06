package com.boxvps.dev.Discord.Box.events.support;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

import com.boxvps.dev.Discord.Box.Info;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CloseIssue extends ListenerAdapter {

    // Define a SQL connection type
    Connection sqlCon;

    // Run code on Guild Message Event
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        // Make a string array of the entire message splitting at the spaces
        String[] args = event.getMessage().getContentRaw().split(" ");

        // Check if the first argument in the message is b,closeissue or b,cli
        if (args[0].equalsIgnoreCase(Info.PREFIX + "closeissue") || args[0].equalsIgnoreCase(Info.PREFIX + "cli")) {
            // Check if the channel the message is being sent in is the channel #support-controls
            if (event.getChannel().getId().equals("507745426091278359")) {
                try {
                    // Set class for the MySQL Connection
                    Class.forName("com.mysql.jdbc.Driver");
                    // Use the previously defined SQL connection type and connect to the database and use the database "Box"
                    sqlCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/Box", "root", "PUT PASSWORD HERE");
                    // Create a MySQL query statement
                    Statement sqlStatement = sqlCon.createStatement();

                    // Check if the message is less then 2 arguments long 
                    // If message is less then 2 arguments send an error Embed saying they didn't specify which ID to close
                    if (args.length < 2) {
                        EmbedBuilder nullArgs1 = new EmbedBuilder();

                        nullArgs1.setColor(Info.ERROR_RED);
                        nullArgs1.setDescription("You didn't specify which issue to close! \n Proper Formats: \n"
                                + Info.PREFIX + "closeissue [issue_id] \n " + Info.PREFIX + "cli [issue_id]");

                        event.getChannel().sendMessage(nullArgs1.build()).queue((message) -> {
                            message.delete().queueAfter(15, TimeUnit.SECONDS);
                        });
                        // If message is 2 arguments long send success embed and delete the issue from the database
                    } else {
                        event.getMessage().delete().queue();
                        Integer idToClose = Integer.parseInt(args[1]);
                        ResultSet sqlResultIssueID = sqlStatement
                                .executeQuery("SELECT * FROM `issues` WHERE `issue_id`='" + idToClose + "'");

                                // Check if the SELECT query was successful with the specified ID 
                                // If the SELECT query was successful delete the issue and send success embed
                        if (sqlResultIssueID.next() == true) {

                            sqlStatement.execute("DELETE FROM `issues` WHERE `issue_id`='" + idToClose + "'");
                            EmbedBuilder eb = new EmbedBuilder();

                            eb.setTitle(":white_check_mark: Issue Resolved");
                            eb.setColor(Info.LIME_GREEN);
                            eb.setDescription("Closed Issue ID: **" + idToClose + "**");

                            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                // Delete the message from the discord channel after 15 seconds
                                message.delete().queueAfter(15, TimeUnit.SECONDS);
                            });

                            // If the SElECT query was not successful send error saying the specified ID was not found in the database
                        } else if (sqlResultIssueID.next() == false) {
                            EmbedBuilder nullIssueID = new EmbedBuilder();

                            nullIssueID.setTitle(":x: Invalid Issue ID");
                            nullIssueID.setColor(Info.ERROR_RED);
                            nullIssueID.setDescription("**" + idToClose + "** is not a valid issue id");

                            event.getChannel().sendMessage(nullIssueID.build()).queue((message) -> {
                                // Delete the message from the discord channel after 15 seconds
                                message.delete().queueAfter(15, TimeUnit.SECONDS);
                            });
                        }
                    }
                    sqlCon.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

}