package com.boxvps.dev.Discord.Box.events.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import com.boxvps.dev.Discord.Box.Info;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Checkin extends ListenerAdapter {

    private String reason = "";
    private String supportRequestType = "";
    private Integer newID;
    private Integer oldID;
    private String boxID;
    private String boxUsername;
    private Connection sqlCon;

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "checkin") || args[0].equalsIgnoreCase(Info.PREFIX + "ci")) {

            if (args.length < 2) {
                EmbedBuilder nullArgs1 = new EmbedBuilder();

                nullArgs1.setColor(Info.ERROR_RED);
                nullArgs1.setDescription("You didn't specify the problem type you are experiencing!");
                nullArgs1.setFooter("Box Checkin Error", Info.LOGO);
                event.getChannel().sendMessage(nullArgs1.build()).queue((message) -> {
                    message.delete().queueAfter(15, TimeUnit.SECONDS);
                    nullArgs1.clear();
                });
            } else if (args.length < 3) {
                EmbedBuilder nullArgs2 = new EmbedBuilder();

                nullArgs2.setColor(Info.ERROR_RED);
                nullArgs2.setDescription("You didn't detail the problem you are experiencing!");
                nullArgs2.setFooter("Box Checkin Error", Info.LOGO);
                event.getChannel().sendMessage(nullArgs2.build()).queue((message) -> {
                    message.delete().queueAfter(15, TimeUnit.SECONDS);
                    nullArgs2.clear();
                });
            } else {
                if (event.getChannelType().equals(ChannelType.PRIVATE)) {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        sqlCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/Box", "root",
								"PUT PASSWORD HERE");
                        Statement sqlStatement = sqlCon.createStatement();
                        ResultSet sqlResultAccount = sqlStatement
                                .executeQuery("SELECT `box_id` FROM `accounts` WHERE `discord_id`='"
                                        + event.getAuthor().getId().toString() + "'");
                        while (sqlResultAccount.next())
                            boxID = sqlResultAccount.getString(1);

                        ResultSet sqlResultName = sqlStatement
                                .executeQuery("SELECT `username` FROM `accounts` WHERE `discord_id`='"
                                        + event.getAuthor().getId().toString() + "'");
                        while (sqlResultName.next())
                            boxUsername = sqlResultName.getString(1);

                        ResultSet sqlResultIssue = sqlStatement.executeQuery("SELECT * FROM `issues`");
                        while (sqlResultIssue.next())
                            oldID = sqlResultIssue.getInt(8);

                        if (oldID == null) {
                            oldID = 0;
                        }

                        newID = oldID + 1;

                        supportRequestType = args[1];
                        for (int i = 2; i < args.length; i++) {
                            reason = reason + args[i] + " ";
                        }

                        sqlStatement.execute(
                                "INSERT INTO `issues`(`discord_id`,`discord_username`,`box_id`,`box_username`,`issue_type`,`issue_details`,`issue_id`)"
                                        + "VALUES('" + event.getAuthor().getId() + "','"
                                        + event.getAuthor().getName().toString() + "#"
                                        + event.getAuthor().getDiscriminator().toString() + "','" + boxID + "','"
                                        + boxUsername + "','" + supportRequestType + "','" + reason + "','" + newID
                                        + "')");

                        EmbedBuilder eb = new EmbedBuilder();

                        eb.setTitle("Ticket Submitted");
                        eb.setColor(Info.LIME_GREEN);
                        eb.setDescription("Your ticket number is **" + newID.toString() + "**");
                        eb.setFooter("Box Ticket Submitted Successfully", Info.LOGO);

                        event.getAuthor().openPrivateChannel().queue((channel) -> {
                            channel.sendMessage(eb.build()).queue((message) -> {
                                message.delete().queueAfter(60, TimeUnit.SECONDS);
                            });
                        });

                        EmbedBuilder newTicket = new EmbedBuilder();

                        newTicket.setAuthor("New Issue", "https://boxvps.tk/issues/issue/" + newID, event.getAuthor().getAvatarUrl());
                        newTicket.setColor(Info.YELLOW);
                        newTicket.setDescription("New Issue ID: " + newID);
                        newTicket.setFooter("Box New Ticket Notification", Info.LOGO);

                        event.getJDA().getGuildById("470401683504103424").getTextChannelById("508911812645879809")
                                .sendMessage(newTicket.build()).queue();

                        reason = "";
                        supportRequestType = "";
                        sqlCon.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (event.getChannelType().equals(ChannelType.TEXT)) {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        sqlCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/Box", "root",
                                "PUT PASSWORD HERE");
                        Statement sqlStatement = sqlCon.createStatement();
                        ResultSet sqlResultAccount = sqlStatement
                                .executeQuery("SELECT `box_id`, `username` FROM `accounts` WHERE `discord_id`='"
                                        + event.getAuthor().getId().toString() + "'");
                        while (sqlResultAccount.next())
                            boxID = sqlResultAccount.getString(1);

                        while (sqlResultAccount.next())
                            boxUsername = sqlResultAccount.getString(2);

                        ResultSet sqlResultIssue = sqlStatement.executeQuery("SELECT * FROM `issues`");
                        while (sqlResultIssue.next())
                            oldID = sqlResultIssue.getInt(8);

                        if (oldID == null) {
                            oldID = 0;
                        }

                        newID = oldID + 1;

                        supportRequestType = args[1];
                        for (int i = 2; i < args.length; i++) {
                            reason = reason + args[i] + " ";
                        }

                        sqlStatement.execute(
                                "INSERT INTO `issues`(`discord_id`,`discord_username`,`box_id`,`box_username`,`issue_type`,`issue_details`,`issue_id`)"
                                        + "VALUES('" + event.getAuthor().getId() + "','"
                                        + event.getAuthor().getName().toString() + "#"
                                        + event.getAuthor().getDiscriminator().toString() + "','" + boxID + "','"
                                        + boxUsername + "','" + supportRequestType + "','" + reason + "','" + newID
                                        + "')");

                        EmbedBuilder eb = new EmbedBuilder();

                        eb.setTitle("Ticket Submitted");
                        eb.setColor(Info.LIME_GREEN);
                        eb.setDescription("Your ticket number is **" + newID.toString() + "**");

                        event.getAuthor().openPrivateChannel().queue((channel) -> {
                            channel.sendMessage(eb.build()).queue((message) -> {
                                message.delete().queueAfter(60, TimeUnit.SECONDS);
                            });
                        });

                        EmbedBuilder newTicket = new EmbedBuilder();

                        newTicket.setAuthor("New Issue", "https://boxvps.tk/issues/issue/" + newID, event.getAuthor().getAvatarUrl());
                        newTicket.setColor(Info.YELLOW);
                        newTicket.setDescription("New Issue ID: " + newID);
                        newTicket.setFooter("Box New Ticket Notification", Info.LOGO);

                        event.getGuild().getTextChannelById("508911812645879809")
                                .sendMessage(newTicket.build()).queue();

                        reason = "";
                        supportRequestType = "";
                        sqlCon.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
