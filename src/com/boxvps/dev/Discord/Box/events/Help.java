package com.boxvps.dev.Discord.Box.events;

import java.util.concurrent.TimeUnit;

import com.boxvps.dev.Discord.Box.Info;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Help extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "help")) {
            EmbedBuilder eb = new EmbedBuilder();
            // Setup Owner Help Command
            if (event.getAuthor() == event.getGuild().getMemberById("796931844179312604") || event.getAuthor() == event.getGuild().getMemberById("237768953739476993")) {
                eb.setTitle(":wrench: Owner Help");
                eb.setColor(Info.FLAMINGO);
                eb.setDescription("");

                event.getAuthor().openPrivateChannel().queue((channel) -> {
                    channel.sendMessage(eb.build()).completeAfter(2, TimeUnit.SECONDS);
                });
            }
            // Setup Support Staff Help Command
            else if (event.getMember().getRoles().contains(event.getGuild().getRoleById(""))) {

            }
            // Cancel the event if the author is a BOT or a Fake Account <- Self-bots(Bots using a normal user account to operate)
            // Preventing spam from bot attacks
            else if (event.getAuthor().isBot() || event.getAuthor().isFake()) {
                return;
            }

            event.getChannel().sendMessage(eb.build()).queue();
        }

    }

}