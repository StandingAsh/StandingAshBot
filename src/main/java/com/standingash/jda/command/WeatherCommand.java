package com.standingash.jda.command;

import com.standingash.jda.weather.URLGenerator;
import com.standingash.jda.weather.WeatherInfo;
import com.standingash.jda.core.Command;
import net.dv8tion.jda.api.entities.Message;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeatherCommand implements Command {

    @Override
    public List<String> getAlias() {
        return new ArrayList<>(Arrays.asList(
                "weather", "w", "날씨"
        ));
    }

    @Override
    public String getDescription() {
        return "`weather` - 날씨 정보를 보여줍니다. `##w, ##날씨` 로도 사용 가능.";
    }

    @Override
    public void execute(Message message) throws IOException, ParseException {

        String content = message.getContentRaw();
        String[] contentField = content.substring(2).split(" ");

        if (contentField.length == 1) {
            URL url = makeURL();
            WeatherInfo weather = new WeatherInfo(url);

            message.reply(weather.toString()).queue();
        } else {
            message.reply("아직 지역별 날씨 구현 안함 ㅎ").queue();
        }
    }

    private URL makeURL() throws UnsupportedEncodingException, MalformedURLException {

        final String URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
        final String SERVICE_KEY = System.getenv("WEATHER_SERVICE_KEY");

        String nx = "59";
        String ny = "125";
        String date = (new SimpleDateFormat("yyyyMMdd")).format(System.currentTimeMillis());
        String time = (new SimpleDateFormat("HH")).format(System.currentTimeMillis()) + "00";

        int hour = Integer.parseInt(time) / 100;
        if (hour < 2) {
            time = "2300";
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, -1);
            date = (new SimpleDateFormat("yyyyMMdd")).format(calendar.getTime());
        } else if (hour % 3 == 2)
            time = time + "00";
        else {
            hour -= ((hour + 1) % 3);
            time = hour + "00";

            if (hour < 10)
                time = "0" + time;
        }

        return new URLGenerator(nx, ny, date, time).getURL();
    }
}
