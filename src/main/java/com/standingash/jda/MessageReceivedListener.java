package com.standingash.jda;

import com.standingash.jda.handler.command.Command;
import com.standingash.jda.handler.command.CommandRegistry;
import com.standingash.jda.handler.noncommand.BasicHandler;
import com.standingash.jda.handler.noncommand.GrammarHandler;
import com.standingash.jda.valueobject.CommandSignature;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MessageReceivedListener extends ListenerAdapter implements CommandRegistry {

    // command keyword prefix
    private String prefix = "//";
    private List<Command> handlers;

    public MessageReceivedListener(List<Command> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void register(Command command) {
        handlers.add(command);
    }

    @Override
    public List<Command> getRegisteredCommands() {
        return handlers;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        User user = event.getAuthor();
        if (user.isBot())
            return;

        Message message = event.getMessage();
        String content = message.getContentRaw();

        if (content.startsWith(prefix))
            commandContent(event);
        else
            nonCommandContent(event);
    }

    // 명령어 처리
    private void commandContent(MessageReceivedEvent event) {

        Message message = event.getMessage();
        String content = message.getContentRaw();
        String[] contentField = content.substring(2).split(" ");

        // handle commands
        for (Command handler : handlers)
            if (handler.getLabel().equals(contentField[0]))
                handler.execute(message, this);
    }

    // 비명령어 처리
    private void nonCommandContent(MessageReceivedEvent event) {

        new BasicHandler(event).execute();
        new GrammarHandler(event).execute();
    }
}
