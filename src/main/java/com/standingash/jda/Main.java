package com.standingash.jda;

import com.standingash.jda.handler.command.Command;
import com.standingash.jda.handler.command.CommandRegistry;
import com.standingash.jda.handler.command.EchoCommand;
import com.standingash.jda.handler.command.HelpCommand;
import com.standingash.jda.handler.noncommand.BasicHandler;
import com.standingash.jda.handler.noncommand.GrammarHandler;
import com.standingash.jda.handler.noncommand.NonCommandRegistry;
import jdk.nashorn.internal.scripts.JD;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception {
        JDA api = buildJDA();
        MessageReceivedListener listener = new MessageReceivedListener();
        addCommands(listener);
        addNonCommands(listener);
        api.addEventListener();
    }

    private static JDA buildJDA() throws Exception {
        JDA api = JDABuilder.createDefault(
                "OTY4MDM4MDgzNTExNTk5MTE0.YmZBuA.n6R4BZzdZxFZwO2E-u1Lzci-Yeg"
        ).build();
        api.getPresence().setActivity(Activity.listening("//help 로 도움말"));
        api.setAutoReconnect(true);
        return api;
    }


    private static void addCommands(MessageReceivedListener registry) {
        registry.register(new HelpCommand(registry, registry));
        registry.register(new EchoCommand());
    }

    private static void addNonCommands(MessageReceivedListener registry) {
        registry.register(new BasicHandler());
        registry.register(new GrammarHandler());
    }
}
