package com.lee.msims.util;

import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component
public class CourseTimeConverter {

    public String[] convertTimeIntoArray(String time){
        return time.split(",");
    }

    public HashMap<String, String> convertTimeIntoMap(String time){
        String[] temp = time.split(",");
        HashMap<String, String> timeMap = new HashMap<>();
        for (String timeForEachDay :
                temp) {
            String[] eachDay = timeForEachDay.split("@");
            timeMap.put(eachDay[0], eachDay[1]);
        }
        return timeMap;
    }
}
