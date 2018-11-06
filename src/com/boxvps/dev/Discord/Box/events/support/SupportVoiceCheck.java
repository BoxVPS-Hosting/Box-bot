package com.boxvps.dev.Discord.Box.events.support;

import java.util.concurrent.TimeUnit;

import com.boxvps.dev.Discord.Box.Info;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class SupportVoiceCheck extends ListenerAdapter {

    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {

        if (event.getChannelJoined().getId().equals("472991019839913984")) {
            try {
                User supportee = event.getMember().getUser();

                EmbedBuilder eb = new EmbedBuilder();

                eb.setTitle(":clipboard: Checkin request");
                eb.setColor(Info.SNOW);
                eb.setDescription("Use `" + Info.PREFIX
                        + "checkin`. Here in the Private Messages between you and me to checkin \n Format: ```\n"
                        + Info.PREFIX
                        + "checkin type[ACCOUNT|SOFTWARE|HARDWARE] details be as specific as you can \n```");

                supportee.openPrivateChannel().queue((channel) -> {
                    channel.sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(15, TimeUnit.SECONDS);
                    });
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}