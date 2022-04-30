package com.standingash.jda.handler.noncommand;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class BasicHandler implements NonCommand {
    @Override
    public void execute(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        User user = event.getAuthor();
        switch (content) {
            case "Hello":
                message.reply("Hello " + user.getAsMention()).queue();
                break;
            case "사랑해":
                message.reply("나도!").queue();
                break;
            case "말?":
                message.reply("히히힝~").queue();
                break;
        }
    }

    @Override
    public String getDescription() {
        return "\n패시브 기능:\n`Hello, 사랑해, 말? 에 대해 대답합니다.`";
    }
}
