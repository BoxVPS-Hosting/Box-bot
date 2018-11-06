package com.boxvps.dev.Discord.Box.events;

import com.boxvps.dev.Discord.Box.Info;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class StatusCMD extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(Info.PREFIX + "status") || args[0].equalsIgnoreCase(Info.PREFIX + "stat")){
            
        }
        
    }

}