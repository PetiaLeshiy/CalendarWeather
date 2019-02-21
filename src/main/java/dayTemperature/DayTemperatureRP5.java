package dayTemperature;

import data.DataDate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayTemperatureRP5 extends DayTemperatureABS{


    public DayTemperatureRP5(String date) {
        super(date, SourceInformation.RP5);
    }

//    public ArrayList<Integer> getListTemperature() {
//        return listTemperature;
//    }
//
//    public void setListTemperature(ArrayList<Integer> listTemperature) {
//        this.listTemperature = listTemperature;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }

    public void doListTemperature (String str) {

        Document document = Jsoup.parse(str);
        Element table = document.select("table").get(7);
        Elements tableLines = table.select("tr");
        Element cell = null;
        Element rows = null;
        Elements columns = null;
        String temp = null;
        int temperature = 0;
        for (int i = 1; i < tableLines.size()-1; i ++) {
            rows = tableLines.get(i);
            columns = rows.select("td");

            if (i == 1){
                cell = columns.get(2);}
            else {
                cell = columns.get(1);}

            temp = cell.toString();
            temperature = Integer.valueOf(temp.substring(temp.indexOf("inline;\"")+10, temp.indexOf("</div>")).trim());
            dataDate.getListOfTemperature().add(temperature);
        }
        }

    public static HashMap<String, DataDate> loadDayTempObj(){
        //String fileName = getPath();
        File file = new File("D:\\Java\\Weather calendar\\RP5");
        String[] listFiles = file.list();
        Pattern pattern = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}\\.dat");
        Matcher matcher = null;
        DataDate loadDay = null;
        HashMap<String, DataDate> mapDayTemperature = new HashMap<>();
        for (int i = 0; i < listFiles.length; i ++) {
            matcher = pattern.matcher(listFiles[i]);
            if (!matcher.matches()){
                System.out.println(listFiles[i] + " не верное имя файла");
                continue;}
            try ( ObjectInputStream objIPS = new ObjectInputStream(new FileInputStream(String.format("D:\\Java\\Weather calendar\\RP5\\%1$s", listFiles[i])))) {

                loadDay = (DataDate) objIPS.readObject();
                mapDayTemperature.put(listFiles[i].substring(0,listFiles[i].indexOf(".dat")),loadDay);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mapDayTemperature;
    }




//    @Override
//    public String toString() {
//        return "dayTemperature.DayTemperatureRP5{" +
//                "listTemperature=" + listTemperature +
//                ", date='" + date + '\'' +
//                '}';
//    }
}
