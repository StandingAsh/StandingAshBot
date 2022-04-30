package com.standingash.jda.handler.command;

import com.standingash.jda.handler.noncommand.NonCommand;
import com.standingash.jda.handler.noncommand.NonCommandRegistry;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public class HelpCommand implements Command {

    private final CommandRegistry commandRegistry;
    private final NonCommandRegistry nonCommandRegistry;

    public HelpCommand(CommandRegistry commandRegistry, NonCommandRegistry nonCommandRegistry) {
        this.commandRegistry = commandRegistry;
        this.nonCommandRegistry = nonCommandRegistry;
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
        MessageAction replyAction = message.reply("StandingAsh 의 명령어:\n\n");
        for (Command s : commandRegistry.getRegisteredCommands())
            replyAction.append(s.getDescription());

        replyAction.append("\n패시브 기능:\n)");
        for (NonCommand n : this.nonCommandRegistry.getRegisteredNonCommands())
            replyAction.append(n.getDescription());
        replyAction.queue();
    }
}
