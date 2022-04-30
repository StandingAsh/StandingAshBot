package com.standingash.jda.core;

import java.util.List;

public interface CommandRegistry {

    void register(Command command);
    List<Command> getRegisteredCommands();
}
