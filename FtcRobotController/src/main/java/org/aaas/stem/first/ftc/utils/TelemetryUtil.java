package org.aaas.stem.first.ftc.utils;


import com.qualcomm.robotcore.robocol.Telemetry;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;


/**
 *
 */
public class TelemetryUtil  {

    private long startTime;
    private Telemetry telemetry;
    private Map<String, TelemetryModel> teleMap ;

    public TelemetryUtil(Telemetry telemetry) {
        this.telemetry = telemetry;
        reset();

    }

    public void reset() {
        Date d = new Date();
        startTime = d.getTime();
        teleMap = new HashMap<String, TelemetryModel>();

       // models = new ArrayList<TelemetryModel>();
    }

    public void addData(String key , double message) {
        addData(key ,  Double.toString(message));
    }
    public void addData(String key , float message) {
        addData(key ,  Float.toString(message));
    }
    public void addData(String key, boolean message) {
        addData(key ,  Boolean.toString(message));
    }


    public void addData(String key , String message) {

        TelemetryModel model = teleMap.get(key);
        if ( model == null ) {
            model = new TelemetryModel();
            model.setKey(key);
            teleMap.put(key,model);
        }
        model.setMessage(message);
        model.setMillsecondsElapsed(calcElapsedTime());
    }

    public void sendTelemetry() {

        List<TelemetryModel> models = new ArrayList<TelemetryModel>();

        Iterator entries = teleMap.entrySet().iterator();
        while (entries.hasNext()) {
            Entry thisEntry = (Entry) entries.next();
            models.add((TelemetryModel) thisEntry.getValue());
        }

        Collections.sort(models);
        for (TelemetryModel model : models) {
            telemetry.addData(model.getKey(),
                    model.getMessage() + " (t="+model.getMillsecondsElapsed()+")");
        }

    }


    private long calcElapsedTime() {
        Date d = new Date();
        return d.getTime() - startTime;
    }



    private class TelemetryModel  implements Comparable  {
        private long millsecondsElapsed;
        private String key;
        private String message;

        public long getMillsecondsElapsed() {
            return millsecondsElapsed;
        }

        public void setMillsecondsElapsed(long millsecondsElapsed) {
            this.millsecondsElapsed = millsecondsElapsed;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public int compareTo(Object another) {
            TelemetryModel anotherModel = (TelemetryModel)another;
            //sort descending
            long val = anotherModel.getMillsecondsElapsed()- getMillsecondsElapsed();
            if (val != 0 ){
                return val < 1 ? -1 : 1;
            }
            return getKey().compareTo(anotherModel.getKey());
        }
    }
}
