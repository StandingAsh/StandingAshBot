package com.standingash.jda.handler.command;

import com.standingash.jda.valueobject.CommandSignature;
import net.dv8tion.jda.api.entities.Message;

public interface Command {

    CommandSignature getSignature();
    void execute(Message message, CommandRegistry registry);
}
