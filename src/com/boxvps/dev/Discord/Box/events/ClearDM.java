package com.boxvps.dev.Discord.Box.events;

import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ClearDM extends ListenerAdapter {
    public Integer messageDelete;
	public String messageDeleteS;

    public void onMessageReactionAdd(MessageReactionAddEvent event) {
		
		try{
		if(event.getReactionEmote().getEmote().getName().equals("❌")){
			String messageToDelete = event.getMessageId();

			event.getChannel().deleteMessageById(messageToDelete).queue();
		}
	}catch(Exception e){
		System.out.println(e);
	}
    }
}