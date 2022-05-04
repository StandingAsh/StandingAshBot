package com.standingash.jda.core;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageReceivedListener extends ListenerAdapter implements CommandRegistry, NonCommandRegistry {

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
        if (content.startsWith(COMMAND_PREFIX)) {
            try {
                commandContent(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            nonCommandContent(event);
    }

    private void commandContent(MessageReceivedEvent event) throws IOException {

        Message message = event.getMessage();
        String content = message.getContentRaw();

        //커맨드는 '//' 로 시작하므로 앞 두자리를 제거
        String[] contentField = content.substring(2).split(" ");

        for (Command handler : handlers)
            if (handler.getAlias().contains(contentField[0])) //contentField[0] := 명령어
                handler.execute(message);
    }


    private void nonCommandContent(MessageReceivedEvent event) {

        for (NonCommand nonCommand : this.nonCommandHandlers)
            nonCommand.execute(event);
    }
}
