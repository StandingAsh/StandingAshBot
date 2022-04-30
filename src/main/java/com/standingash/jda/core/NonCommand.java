package com.standingash.jda.core;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface NonCommand {

    void execute(MessageReceivedEvent event);
    String getDescription();
}
