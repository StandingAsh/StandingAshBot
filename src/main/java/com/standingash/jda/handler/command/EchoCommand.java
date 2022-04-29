package com.standingash.jda.handler.command;

import com.standingash.jda.valueobject.CommandSignature;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public class EchoCommand implements Command {

    @Override
    public String getLabel() {
        return "echo";
    }

    @Override
    public String getDescription() {
        return "`echo 옵션 | <reverse> <mix>` - 입력한 메시지를 따라합니다.\n";
    }

    @Override
    public void execute(Message message, CommandRegistry registry) {

        String content = message.getContentRaw();
        String[] contentField = content.substring(2).split(" ");

        if (contentField.length == 1)
            message.reply("Nothing to echo").queue();
        else if (contentField[1].equals("reverse"))
            handleReverse(message, contentField);
        else if (contentField[1].equals("mix"))
            handleMix(message, contentField);
        else {

            MessageAction messageAction = message.reply(contentField[1]);
            for (int i = 2; i < contentField.length; i++)
                messageAction.append(" " + contentField[i]);
            messageAction.queue();
        }
    }

    // 문자열 뒤집기
    private void handleReverse(Message message, String[] contentField){

        StringBuilder stringBuilder = new StringBuilder(contentField[2]);

        for (int i = 3; i < contentField.length; i++)
            stringBuilder.append(" " + contentField[i]);

        String reverseString = stringBuilder.reverse().toString();
        message.reply(reverseString).queue();
    }

    // 문자열 교차 섞기
    private void handleMix(Message message, String[] contentField){

        if (contentField.length != 4)
            message.reply("`echo <mix> <단어1> <단어2>` 의 형식을 지켜주세요.").queue();
        else {
            int length1 = contentField[2].length();
            int length2 = contentField[3].length();

            String result = length1 >= length2 ?
                    mixString(contentField[2], contentField[3])
                    : mixString(contentField[3], contentField[2]);

            message.reply(result).queue();
        }
    }

    private String mixString(String string1, String string2) {

        StringBuilder result = new StringBuilder("");

        for (int i = 0; i < string2.length(); i++) {
            result.append(string1.charAt(i));
            result.append(string2.charAt(i));
        }

        for (int i = string2.length(); i < string1.length(); i++)
            result.append(string1.charAt(i));

        return result.toString();
    }
}
