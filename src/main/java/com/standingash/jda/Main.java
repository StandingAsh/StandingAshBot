package com.standingash.jda;

import com.standingash.jda.command.WeatherCommand;
import com.standingash.jda.core.MessageReceivedListener;
import com.standingash.jda.command.EchoCommand;
import com.standingash.jda.command.HelpCommand;
import com.standingash.jda.noncommand.BasicHandler;
import com.standingash.jda.noncommand.GrammarHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Main {

    public static void main(String[] args) throws Exception {

        JDA api = buildJDA();
        MessageReceivedListener listener = new MessageReceivedListener();

        addCommands(listener);
        addNonCommands(listener);
        api.addEventListener(listener);
    }

    private static JDA buildJDA() throws Exception {

        JDA api = JDABuilder.createDefault(
                System.getenv("STANDING_ASH_TOKEN")
        ).build();
        api.getPresence().setActivity(Activity.listening("##help 로 도움말"));
        api.setAutoReconnect(true);
        return api;
    }


    private static void addCommands(MessageReceivedListener registry) {

        registry.register(new HelpCommand(registry, registry));
        registry.register(new EchoCommand());
        registry.register(new WeatherCommand());
    }

    private static void addNonCommands(MessageReceivedListener registry) {

        registry.register(new BasicHandler());
        registry.register(new GrammarHandler());
    }
}
