import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnectionObject {
private String url;
private URL webURL=null;
private HttpURLConnection connection;
private  String date = null;

    public String getDate() {
        return date;
    }



public ConnectionObject(String url) {
        this.url = url;
        try {
            webURL = new URL(this.url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public void connection(){
        try {
            connection = (HttpURLConnection) webURL.openConnection();
            this.connection.setRequestMethod("POST");
            this.connection.setUseCaches(true);
            this.connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            this.connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            this.connection.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
            this.connection.setRequestProperty("Cache-Control", "max-age=0");
            this.connection.setRequestProperty("Connection", "keep-alive");
            this.connection.setRequestProperty("Content-Length", "48");
            this.connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            this.connection.setRequestProperty("Cookie", "PHPSESSID=b37114f5fb716e0ab4cf0bc0d3098924; format=xls; f_enc=ansi; __utmc=66441069; __utmz=66441069.1550414192.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); located=1; tab_metar=1; __utma=66441069.208786158.1550414192.1550426300.1550428101.5; __utmt=1; __utmb=66441069.2.10.1550428101; lang=ru");
            this.connection.setRequestProperty("Host", "rp5.ru");
            this.connection.setRequestProperty("Origin", "https://rp5.ru");
            this.connection.setRequestProperty("Referer", "https://rp5.ru/");
            this.connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
            this.connection.setRequestProperty("User Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
            this.connection.setDoOutput(true);
            this.sendRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest() throws IOException {
        String yesterday = getYesterdayDate();
        DataOutputStream ops = new DataOutputStream(this.connection.getOutputStream());
        String formD = String.format("ArchDate=%1$s&pe=1&lang=ru&time_zone_add=3", yesterday);
        byte[] formDataByte = formD.getBytes("UTF-8");
        ops.write(formDataByte);
        ops.flush();
        ops.close();
    }

    private String getYesterdayDate() {
        Date date = new Date();
        long yesterDay = (date.getTime() - (long)(24*60*60*1000));
        date = new Date(yesterDay);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateToString = simpleDateFormat.format(date);
        this.date = dateToString;
        return dateToString;
    }

    public String getPageContent(){
        String appendString = "";
        StringBuilder stringBuilder = new StringBuilder(appendString);

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.connection.getInputStream()))){
            while ((appendString = reader.readLine()) != null){
            stringBuilder.append(appendString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } return stringBuilder.toString();
    }





}
