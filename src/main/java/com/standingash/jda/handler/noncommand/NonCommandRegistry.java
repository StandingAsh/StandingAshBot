package com.standingash.jda.handler.noncommand;

import java.util.List;

public interface NonCommandRegistry {
    void register(NonCommand command);
    List<NonCommand> getRegisteredNonCommands();
}
