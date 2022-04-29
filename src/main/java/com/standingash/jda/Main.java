package com.standingash.jda;

import com.standingash.jda.handler.command.Command;
import com.standingash.jda.handler.command.EchoCommand;
import com.standingash.jda.handler.command.HelpCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args)
            throws LoginException, InterruptedException {

        JDA api = JDABuilder.createDefault(
                "OTY4MDM4MDgzNTExNTk5MTE0.YmZBuA.n6R4BZzdZxFZwO2E-u1Lzci-Yeg"
        ).build();
        api.getPresence().setActivity(Activity.listening("//help 로 도움말"));

        api.setAutoReconnect(true);

        Set<Command> handlers = new HashSet<>();
        handlers.add(new HelpCommand());
        handlers.add(new EchoCommand());

        api.addEventListener(new MessageReceivedListener(handlers));
    }
}
