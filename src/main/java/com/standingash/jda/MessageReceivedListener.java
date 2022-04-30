package com.standingash.jda;

import com.standingash.jda.handler.command.Command;
import com.standingash.jda.handler.command.CommandRegistry;
import com.standingash.jda.handler.noncommand.BasicHandler;
import com.standingash.jda.handler.noncommand.GrammarHandler;
import com.standingash.jda.handler.noncommand.NonCommand;
import com.standingash.jda.handler.noncommand.NonCommandRegistry;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageReceivedListener extends ListenerAdapter implements CommandRegistry, NonCommandRegistry {

    // command keyword prefix
    private final String COMMAND_PREFIX = "//";
    private final List<Command> handlers = new ArrayList<>();
    private final List<NonCommand> nonCommandHandlers = new ArrayList<>();

    @Override
    public void register(Command command) {
        handlers.add(command);
    }

    @Override
    public List<Command> getRegisteredCommands() {
        return handlers;
    }

    @Override
    public void register(NonCommand command) {
        nonCommandHandlers.add(command);
    }

    @Override
    public List<NonCommand> getRegisteredNonCommands() {
        return nonCommandHandlers;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        if (user.isBot())
            return;

        String content = event.getMessage().getContentRaw();
        if (content.startsWith(COMMAND_PREFIX))
            commandContent(event);
        else
            nonCommandContent(event);
    }

    // 명령어 처리
    private void commandContent(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        String[] contentField = content.substring(2).split(" "); //커맨드는 '//' 로 시작하므로 앞 두자리를 제거

        // handle commands
        for (Command handler : handlers)
            if (handler.getLabel().equals(contentField[0]))
                handler.execute(message);
    }

    // 비명령어 처리
    public void nonCommandContent(MessageReceivedEvent event) {
        for (NonCommand nonCommand : this.nonCommandHandlers)
            nonCommand.execute(event);
    }
}
