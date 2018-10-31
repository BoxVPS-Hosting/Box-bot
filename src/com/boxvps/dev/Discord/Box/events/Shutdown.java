package com.boxvps.dev.Discord.Box.events;

import java.util.Timer;

import com.boxvps.dev.Discord.Box.Info;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Shutdown extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "shutdown")) {
            try {
                if (event.getMember().getUser().getId().equals("79693184417931264")
                        || event.getMember().getUser().getId().equals("237768953739476993")) {
                    if (args[1].equalsIgnoreCase("now") || args[1].equalsIgnoreCase("-n")) {
                        event.getJDA().shutdownNow();
                        System.exit(0);
                    } else {
                        event.getJDA().shutdown();
                        new Timer().schedule(new java.util.TimerTask() {
                            @Override
                            public void run() {
                                System.exit(0);
                            }
                        }, 10000);
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
}