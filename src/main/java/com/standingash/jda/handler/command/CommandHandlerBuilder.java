package com.standingash.jda.handler.command;

import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class CommandHandlerBuilder {

    List<Command> handlers = new ArrayList<>();

    public CommandHandlerBuilder(String[] contentField, Message message) {
    }

    public List<Command> getHandlers() {

        return handlers;
    }
}
