package com.standingash.jda.command;

import com.standingash.jda.WeatherInfo;
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

        StringBuilder urlBuilder = new StringBuilder(URL);

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

        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
                + "=" + SERVICE_KEY);                                       // 서비스 키
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8")
                + "=" + URLEncoder.encode("1", "UTF-8"));            // 페이지 수
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8")
                + "=" + URLEncoder.encode("12", "UTF-8"));          // 한 페이지 결과 수
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8")
                + "=" + URLEncoder.encode("JSON", "UTF-8"));         // 타입
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8")
                + "=" + URLEncoder.encode(date, "UTF-8"));              // 날짜
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8")
                + "=" + URLEncoder.encode(time, "UTF-8"));              // 시간 AM 02시부터 3시간 단위
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8")
                + "=" + URLEncoder.encode(nx, "UTF-8"));                // 경도
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8")
                + "=" + URLEncoder.encode(ny, "UTF-8"));                // 위도

        return new URL(urlBuilder.toString());
    }
}
