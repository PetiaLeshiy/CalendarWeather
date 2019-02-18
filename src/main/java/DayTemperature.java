import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayTemperature implements Serializable {
    private ArrayList<Integer> listTemperature = new ArrayList<Integer>();
    private String date;

    public DayTemperature(String date) {
        this.date = date;
    }

    public ArrayList<Integer> getListTemperature() {
        return listTemperature;
    }

    public void setListTemperature(ArrayList<Integer> listTemperature) {
        this.listTemperature = listTemperature;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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
         listTemperature.add(temperature);
        }
        System.out.println(listTemperature);

}

    public int maxDayTemperature () {
    return  Collections.max(listTemperature);
    }
    public int minDayTemperature () {
        return  Collections.min(listTemperature);
    }

    public double averageTemperature () {
    OptionalDouble average = listTemperature.stream().mapToInt(e -> e).average();
        if (average.isPresent())
        {
         return average.getAsDouble();
        }

        return 0;
    }

    public void saveDayTempObj() {
        try {
            ObjectOutputStream objOPS = new ObjectOutputStream(new FileOutputStream(String.format("D:\\Java\\Wather calendar\\%1$s.dat", this.date)));
        objOPS.writeObject(this);
       // objOPS.flush();
        objOPS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static HashMap<String,DayTemperature> loadDayTempObj(){
        File file = new File("D:\\Java\\Wather calendar");
        String[] listFiles = file.list();

        Pattern pattern = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}\\.dat");
        Matcher matcher = null;
        ObjectInputStream objIPS = null;
        DayTemperature loadDay = null;
        HashMap<String, DayTemperature> mapDayTemperature = new HashMap<>();
        for (int i = 0; i < listFiles.length; i ++) {
            matcher = pattern.matcher(listFiles[i]);
            if (!matcher.matches()){
                System.out.println(listFiles[i] + " не подходящий файл");
                continue;}
            try {
                objIPS = new ObjectInputStream(new FileInputStream(String.format("D:\\Java\\Wather calendar\\%1$s", listFiles[i])));
                loadDay = (DayTemperature) objIPS.readObject();
                mapDayTemperature.put(listFiles[i].substring(0,listFiles[i].indexOf(".dat")),loadDay);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return mapDayTemperature;
    }

    @Override
    public String toString() {
        return "DayTemperature{" +
                "listTemperature=" + listTemperature +
                ", date='" + date + '\'' +
                '}';
    }
}
