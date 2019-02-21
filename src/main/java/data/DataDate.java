package data;

import dayTemperature.SourceInformation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class DataDate  implements Serializable {
    private String date;
    private ArrayList<Integer> listOfTemperature = new ArrayList<>();
    private SourceInformation source;

    public DataDate() {
    }

    public DataDate(String date, ArrayList<Integer> listOfTemperature, SourceInformation source) {
        this.date = date;
        this.listOfTemperature = listOfTemperature;
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Integer> getListOfTemperature() {
        return listOfTemperature;
    }

    public void setListOfTemperature(ArrayList<Integer> listOfTemperature) {
        this.listOfTemperature = listOfTemperature;
    }

    public SourceInformation getSource() {
        return source;
    }

    public void setSource(SourceInformation source) {
        this.source = source;
    }
}
