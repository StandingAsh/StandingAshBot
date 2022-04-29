package com.standingash.jda.valueobject;

public class CommandSignature {

    // list of commands
    private final String label;

    // list of command information
    private final String description;

    public CommandSignature(String label, String description) {

        this.label = label;
        this.description = description;
    }

    public String getLabel() {

        return label;
    }

    public String getDescription() {

        return description;
    }
}
