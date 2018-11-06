package com.boxvps.dev.Discord.Box.events;

import java.util.concurrent.TimeUnit;

import com.boxvps.dev.Discord.Box.Info;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Aliases extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "aliases") || args[0].equalsIgnoreCase(Info.PREFIX + "a")) {

            EmbedBuilder eb = new EmbedBuilder();

            eb.setTitle("Aliases");
            eb.setColor(Info.FLAMINGO);
            eb.addField(Info.PREFIX + "aliases","Aliases: `"+ Info.PREFIX + "a`", true);
            eb.addField(Info.PREFIX + "botstatus"," Aliases: `" + Info.PREFIX + "bts`" , true);
            eb.addField(Info.PREFIX + "checkin", "Aliases `" + Info.PREFIX + "ci`", true);
            eb.addField(Info.PREFIX + "help", "Aliases `" + Info.PREFIX + "h` , `" + Info.PREFIX + "hep`" , true);
            eb.addField(Info.PREFIX + "shutdown", "Aliases `" + Info.PREFIX + "sd`", true);
            eb.addField(Info.PREFIX + "status", "Aliases `" + Info.PREFIX + "stat`", true);

            if (args.length > 1) {
                if (args[1].equalsIgnoreCase("-dm") || args[1].equalsIgnoreCase("dm")) {
                    event.getMember().getUser().openPrivateChannel().queue((channel) -> {
                        channel.sendMessage(eb.build()).queue((message) -> {
                            message.delete().queueAfter(30, TimeUnit.SECONDS);
                        });
                    });
                }
            } else {
                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    message.delete().queueAfter(30 , TimeUnit.SECONDS);
                });
            }
        }
    }

}