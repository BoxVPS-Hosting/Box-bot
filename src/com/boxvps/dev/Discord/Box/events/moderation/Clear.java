package com.boxvps.dev.Discord.Box.events.moderation;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.boxvps.dev.Discord.Box.Info;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Clear extends ListenerAdapter {
                
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(Info.PREFIX + "clear") || args[0].equalsIgnoreCase(Info.PREFIX + "c")) {
            if (event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {

                String messageDeleteS = args[1];
                Integer messageDelete = Integer.parseInt(messageDeleteS);

                if(args.length < 2) {
                    EmbedBuilder nullArgs1 = new EmbedBuilder();

                    nullArgs1.setTitle(":x: Unspecified amount");
                    nullArgs1.setDescription("You didn't specify the amount of messages to delete!");
                    nullArgs1.setColor(Info.ERROR_RED);
                    nullArgs1.setFooter("Box Delete", Info.LOGO);

                    event.getChannel().sendMessage(nullArgs1.build()).queue((message) -> {
                        message.delete().queueAfter(15, TimeUnit.SECONDS);
                    });
                }
                if(messageDelete > 100){
                    EmbedBuilder invalidAmount = new EmbedBuilder();

                    invalidAmount.setTitle(":x: Invalid Amount");
                    invalidAmount.setColor(Info.ERROR_RED);
                    invalidAmount.setDescription(messageDelete + " is too many messages to delete at once. Max is **100**");
                    invalidAmount.setFooter("Box Delete", Info.LOGO);

                    event.getChannel().sendMessage(invalidAmount.build()).queue((message) -> {
                        message.delete().queueAfter(15, TimeUnit.SECONDS);
                    });
                }
                else{
                    List<Message> msg = event.getChannel().getHistory().retrievePast(messageDelete).complete();
                    event.getChannel().deleteMessages(msg).queue();

                    EmbedBuilder eb = new EmbedBuilder();

                    eb.setTitle(":wastebasket: Success!");
                    eb.setColor(Info.LIME_GREEN);
                    eb.setDescription("Successfully deleted **" + messageDelete.toString() + "** messages");
                    eb.setFooter("Box Delete", Info.LOGO);

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(15, TimeUnit.SECONDS);
                    });
                }

            }
        
        }

    }

}