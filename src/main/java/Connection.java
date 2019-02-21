
import dayTemperature.DayTemperatureRP5;

import java.net.*;

public class Connection {


    public static void main(String[] args) {
        String urlString = "https://rp5.ru/%D0%90%D1%80%D1%85%D0%B8%D0%B2_%D0%BF%D0%BE%D0%B3%D0%BE%D0%B4%D1%8B_%D0%B2_%D0%9F%D1%83%D0%BB%D0%BA%D0%BE%D0%B2%D0%BE_(%D0%B0%D1%8D%D1%80%D0%BE%D0%BF%D0%BE%D1%80%D1%82),_METAR";
        CookieHandler.setDefault(new CookieManager());
        ConnectionObject connectionObject = new ConnectionObject(urlString);
        connectionObject.connection();
        String pageContent = connectionObject.getPageContent();

        DayTemperatureRP5 dayTemperature = new DayTemperatureRP5(connectionObject.getDate());
        dayTemperature.doListTemperature(pageContent);
//save temperature of yesterday
        dayTemperature.saveDayTempObj();
//load all saved days
        System.out.println(DayTemperatureRP5.loadDayTempObj().keySet());
    }
    }


