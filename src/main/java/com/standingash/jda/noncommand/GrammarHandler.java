package com.standingash.jda.noncommand;

import com.standingash.jda.core.NonCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GrammarHandler implements NonCommand {

    @Override
    public void execute(MessageReceivedEvent event) {

        Message message = event.getMessage();
        String content = message.getContentRaw();
        User user = event.getAuthor();

        // 맞춤법 검사
        if (content.contains("됬"))
            message.reply(
                    "됬 이란 글자는 한글에 없단다,,," + user.getAsMention()
            ).queue();

        if (content.contains("되") && content.length() != 1) {

            int index = content.indexOf('되');
            while (index != -1) {
                if (index == content.length() - 1 || isWrong(content.charAt(index + 1))) {
                    message.reply(
                            "되가 아니라 돼 란다,,," + user.getAsMention()).queue();
                    break;
                }
                index = content.indexOf("되", index + 1);
            }
        }
    }

    @Override
    public String getDescription() {
        return "`되, 돼, 됬 만큼은 칼같이 잡아줍니다.`";
    }

    // 되, 돼 맞춤법 검사
    private boolean isWrong(char behind) {

        return behind == '.' || behind == '?' || behind == ','
                || behind == '!' || behind == '서' || behind == '야'
                || behind == '도' || behind == '가' || behind == '요';
    }
}
