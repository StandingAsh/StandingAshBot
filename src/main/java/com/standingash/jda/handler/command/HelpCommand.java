package com.standingash.jda.handler.command;

import com.standingash.jda.valueobject.CommandSignature;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public class HelpCommand implements Command {

    private final CommandSignature helpSignature =
            new CommandSignature("help", "`help` - 명령어 목록을 보여줍니다.\n");

    @Override
    public CommandSignature getSignature() {

        return helpSignature;
    }

    @Override
    public void execute(Message message, CommandRegistry registry) {

        MessageAction replyAction =
                message.reply("StandingAsh 의 명령어:\n\n");

        for (CommandSignature s : registry.getRegisteredSignatures())
            replyAction.append(s.getDescription());
        replyAction.append("\n패시브 기능:\n`되, 돼, 됬 만큼은 칼같이 잡아줍니다.`");
        replyAction.queue();
    }
}
