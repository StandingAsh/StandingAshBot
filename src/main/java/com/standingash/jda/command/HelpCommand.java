package com.standingash.jda.command;

import com.standingash.jda.core.Command;
import com.standingash.jda.core.CommandRegistry;
import com.standingash.jda.core.NonCommand;
import com.standingash.jda.core.NonCommandRegistry;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelpCommand implements Command {

    private final CommandRegistry commandRegistry;
    private final NonCommandRegistry nonCommandRegistry;

    public HelpCommand(CommandRegistry commandRegistry, NonCommandRegistry nonCommandRegistry) {

        this.commandRegistry = commandRegistry;
        this.nonCommandRegistry = nonCommandRegistry;
    }

    @Override
    public List<String> getAlias() {

        List<String> aliasList = new ArrayList<>(Arrays.asList(
                "help", "h", "도움", "도움말"
        ));
        return aliasList;
    }

    @Override
    public String getDescription() {
        return "`help` - 명령어 목록을 보여줍니다. `##h, ##도움, ##도움말` 로도 사용 가능";
    }

    @Override
    public void execute(Message message) {

        MessageAction replyAction = message.reply("StandingAsh 의 명령어:\n\n");
        for (Command s : commandRegistry.getRegisteredCommands())
            replyAction.append(s.getDescription()).append('\n');

        replyAction.append("\n패시브 기능:\n");
        for (NonCommand n : this.nonCommandRegistry.getRegisteredNonCommands())
            replyAction.append(n.getDescription()).append('\n');
        replyAction.queue();
    }
}
