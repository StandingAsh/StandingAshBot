package com.standingash.jda.core;

import java.util.List;

public interface NonCommandRegistry {
    void register(NonCommand command);
    List<NonCommand> getRegisteredNonCommands();
}
