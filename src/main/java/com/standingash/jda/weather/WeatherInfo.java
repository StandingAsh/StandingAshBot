package com.standingash.jda.weather;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherInfo {

    private final String resultString;
    private String POP, PTY, PCP, REH, SNO, SKY, TMP, TMN, TMX, UUU, VVV, WAV, VEC, WSD;

    public WeatherInfo(URL url) throws IOException, ParseException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-type", "application/json");
        //System.out.println("Response code: " + connection.getResponseCode());

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

    private void setResultInfo(JSONObject weather, String category) {

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
