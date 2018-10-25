package com.boxvps.dev.Discord.Box.events;

import com.boxvps.dev.Discord.Box.Info;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Status extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        
        EmbedBuilder eb = new EmbedBuilder();

        if (args[0].equalsIgnoreCase(Info.PREFIX + "status")) {
            if(event.getMember().getUser().getId().equals("79693184417931264") || event.getMember().getUser().getId().equals("237768953739476993")){
                eb.setTitle("Box Bot Status");
                

                event.getAuthor().openPrivateChannel().queue((channel) -> {
                    channel.sendMessage(eb.build()).queue();
                    eb.clear();
                });                
            }else {
                eb.setTitle("Insufficient Permissions");
                eb.setDescription(event.getAuthor().getAsMention() + ", you do not have sufficient permissions to use this command!");
                eb.setColor(Info.ERROR_RED);
                eb.setFooter("Box Insufficient Permission", Info.LOGO);

                event.getAuthor().openPrivateChannel().queue((channel) -> {
                    channel.sendMessage(eb.build()).queue();
                    eb.clear();
                });
            }
        }
    }
}