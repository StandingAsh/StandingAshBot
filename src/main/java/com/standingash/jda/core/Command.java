package com.standingash.jda.core;

import net.dv8tion.jda.api.entities.Message;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface Command {

    List<String> getAlias();
    String getDescription();
    void execute(Message message) throws IOException, ParseException;
}
