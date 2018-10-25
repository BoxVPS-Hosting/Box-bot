package com.boxvps.dev.Discord.Box.events;

import com.boxvps.dev.Discord.Box.Info;
//import com.boxvps.dev.Discord.Box.Main;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class SQLTest extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(Info.PREFIX + "test")){
            event.getChannel().sendMessage("NO").queue();
            //Main.mysqlConnection("SELECT ");
        }
    }

}