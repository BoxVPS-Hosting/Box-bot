package com.boxvps.dev.Discord.Box.events;

import java.util.concurrent.TimeUnit;

import com.boxvps.dev.Discord.Box.Info;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BotStatus extends ListenerAdapter {

    long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024 / 1024;
    long usedMemory = Runtime.getRuntime().freeMemory() / 1024 / 1024 ;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        
        EmbedBuilder eb = new EmbedBuilder();

        if (args[0].equalsIgnoreCase(Info.PREFIX + "botstatus") || args[0].equalsIgnoreCase(Info.PREFIX + "bts")) {
            if(event.getMember().getUser().getId().equals("79693184417931264") || event.getMember().getUser().getId().equals("237768953739476993")){
                eb.setTitle(":bar_chart: Box Bot Status");
                eb.setColor(Info.SUNRISE);
                eb.addField("RAM Usage","Free: " + usedMemory + " MB / Max: " + maxMemory + " GB", false);
                eb.addField("Operating System", System.getProperty("os.name"),false);
                eb.addField("Operating System Version", System.getProperty("os.version"),false);
                eb.addField("Operating System Architecture", System.getProperty("os.arch"),false);

                event.getAuthor().openPrivateChannel().queue((channel) -> {
                    channel.sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(30, TimeUnit.SECONDS);
                    });
                    eb.clearFields();
                });                
            }else {
                eb.setTitle("Insufficient Permissions");
                eb.setDescription(event.getAuthor().getAsMention() + ", you do not have sufficient permissions to use this command!");
                eb.setColor(Info.ERROR_RED);
                eb.setFooter("Box Insufficient Permission", Info.LOGO);

                event.getAuthor().openPrivateChannel().queue((channel) -> {
                    channel.sendMessage(eb.build()).complete().delete().queueAfter(10,TimeUnit.SECONDS);
                    eb.clear();
                });
            }
        }
    }
}