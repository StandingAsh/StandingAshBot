package com.standingash.jda.handler.noncommand;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class BasicHandler implements NonCommandHandler{

    private Message message;
    private String content;
    private User user;

    public BasicHandler(MessageReceivedEvent event) {

        this.message = event.getMessage();
        this.content = message.getContentRaw();
        this.user = event.getAuthor();
    }

    @Override
    public void execute() {

        if ("Hello".equals(content))
            message.reply("Hello " + user.getAsMention()).queue();
        else if ("사랑해".equals(content))
            message.reply("나도!").queue();
        else if ("말?".equals(content))
            message.reply("히히힝~").queue();
        else {
            // empty else
        }
    }
}
