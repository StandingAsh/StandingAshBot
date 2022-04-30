package com.standingash.jda.core;

import net.dv8tion.jda.api.entities.Message;

public interface Command {

    String getLabel();
    String getDescription();
    void execute(Message message);
}
