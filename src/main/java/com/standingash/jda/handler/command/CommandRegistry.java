package com.standingash.jda.handler.command;

import java.util.List;

public interface CommandRegistry {

    void register(Command command);
    List<Command> getRegisteredCommands();
}
