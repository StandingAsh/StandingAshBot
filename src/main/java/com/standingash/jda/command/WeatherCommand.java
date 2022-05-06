package com.standingash.jda.command;

import com.standingash.jda.core.Command;
import net.dv8tion.jda.api.entities.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeatherCommand implements Command {

    private static class WeatherInfo {

        private final String resultString;
        private String POP, PTY, PCP, REH, SNO, SKY, TMP, TMN, TMX, UUU, VVV, WAV, VEC, WSD;

        public WeatherInfo(URL url) throws IOException, ParseException {

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + connection.getResponseCode());

            BufferedReader rd;
            if (connection.getResponseCode() >= 200 && connection.getResponseCode() <= 300)
                rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            else
                rd = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = rd.readLine()) != null)
                sb.append(line);
            rd.close();
            connection.disconnect();

            this.resultString = sb.toString();
            parseResultByJSON();
        }

        private void parseResultByJSON() throws ParseException {

            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(this.resultString);

            JSONObject response = (JSONObject) object.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");

            JSONArray itemList = (JSONArray) items.get("item");
            String category;
            JSONObject weather;

            for (Object o : itemList) {
                weather = (JSONObject) o;
                category = (String) weather.get("category");
                setResultInfo(weather, category);
            }
        }

        public void setResultInfo(JSONObject weather, String category) {

            switch (category) {
                case "POP":
                    this.POP = (String) weather.get("fcstValue") + "%";
                    break;
                case "PTY":
                    switch (Integer.parseInt((String) weather.get("fcstValue"))) {
                        case 0:
                            this.PTY = "없음";
                            break;
                        case 1:
                            this.PTY = "비";
                            break;
                        case 2:
                            this.PTY = "비 & 눈";
                            break;
                        case 3:
                            this.PTY = "눈";
                            break;
                        case 4:
                            this.PTY = "소나기";
                            break;
                    }
                    break;
                case "PCP":
                    this.PCP = (String) weather.get("fcstValue");
                    break;
                case "REH":
                    this.REH = (String) weather.get("fcstValue") + "%";
                    break;
                case "SNO":
                    this.SNO = (String) weather.get("fcstValue");
                    break;
                case "SKY":
                    switch (Integer.parseInt((String) weather.get("fcstValue"))) {
                        case 1:
                            this.SKY = "맑음";
                            break;
                        case 3:
                            this.SKY = "구름 많음";
                            break;
                        case 4:
                            this.SKY = "흐림";
                            break;
                    }
                    break;
                case "TMP":
                    this.TMP = (String) weather.get("fcstValue") + "°C";
                    break;
                case "TMN":
                    this.TMN = (String) weather.get("fcstValue") + "°C";
                    break;
                case "TMX":
                    this.TMX = (String) weather.get("fcstValue") + "°C";
                    break;
                case "UUU":
                    this.UUU = (String) weather.get("fcstValue");
                    break;
                case "VVV":
                    this.VVV = (String) weather.get("fcstValue");
                    break;
                case "WAV":
                    this.WAV = (String) weather.get("fcstValue");
                    break;
                case "VEC":
                    this.VEC = (String) weather.get("fcstValue");
                    break;
                case "WSD":
                    this.WSD = (String) weather.get("fcstValue");
                    break;
            }
        }

        @Override
        public String toString() {
            return "`관악구 조원동`\n날씨 : `" + SKY + "`\n"
                    + "강수확률 : `" + POP + "`\n"
                    + "강수형태 : `" + PTY + "`\n"
                    + "현재기온 : `" + TMP + "`\n"
                    + "습도 : `" + REH + "`\n";
        }
    }

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

        if (contentField.length == 1)
            message.reply("지역을 입력해주세요. `##weather|w|날씨 <지역이름>`").queue();
        else {

            URL url = makeURL();
            WeatherInfo weather = new WeatherInfo(url);

            message.reply(weather.toString()).queue();
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
