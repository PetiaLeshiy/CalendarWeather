package dayTemperature;

import data.DataDate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.OptionalDouble;

public abstract class DayTemperatureABS {
    public DataDate getDataDate() {
        return dataDate;
    }

    public void setDataDate(DataDate dataDate) {
        this.dataDate = dataDate;
    }

    public DataDate dataDate= new DataDate();

    public DayTemperatureABS(String date, SourceInformation source) {
        this.dataDate.setDate(date);
        dataDate.setSource(source);
        init();
    }

    private void init() {
        String pathStr = getPath();
        Path path = Paths.get(pathStr);
        if(Files.notExists(path)){
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public abstract void doListTemperature(String str);
    public int maxDayTemperature () {
        return  Collections.max(dataDate.getListOfTemperature());
    }
    public int minDayTemperature () {
        return  Collections.min(dataDate.getListOfTemperature());
    }
    public double averageTemperature () {
        OptionalDouble average = dataDate.getListOfTemperature().stream().mapToInt(e -> e).average();
        if (average.isPresent())
        {
            return average.getAsDouble();
        }

        return 0;
    }

    public String getPath() {
        if (dataDate.getSource() == SourceInformation.RP5) {return "D:\\Java\\Weather calendar\\RP5";}
        if (dataDate.getSource()== SourceInformation.GISMETEO){return "D:\\Java\\Weather calendar\\GISMETEO";}
        else return null;
    }
    public void saveDayTempObj() {
        String fileName = getPath();
        try (ObjectOutputStream objOPS = new ObjectOutputStream(new FileOutputStream(String.format("%1$s\\%2$s.dat", fileName, this.dataDate.getDate())))) {
            objOPS.writeObject(dataDate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
