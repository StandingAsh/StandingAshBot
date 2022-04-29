package com.standingash.jda.handler.command;

import com.standingash.jda.valueobject.CommandSignature;

import java.util.List;

public interface CommandRegistry {

    void register(Command command);
    List<CommandSignature> getRegisteredSignatures();
}
