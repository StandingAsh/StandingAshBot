package com.standingash.jda.handler.command;

import com.standingash.jda.valueobject.CommandSignature;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public class HelpCommand implements Command {

    private final CommandRegistry commandRegistry;

    public HelpCommand(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    @Override
    public String getLabel() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "`help` - 명령어 목록을 보여줍니다.\n";
    }

    @Override
    public void execute(Message message) {
        MessageAction replyAction =
                message.reply("StandingAsh 의 명령어:\n\n");
        for (Command s : commandRegistry.getRegisteredCommands())
            replyAction.append(s.getDescription());
        replyAction.append("\n패시브 기능:\n`되, 돼, 됬 만큼은 칼같이 잡아줍니다.`");
        replyAction.queue();
    }
}
