package com.lee.msims.util;

import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateFormatter {

    public String formatDateToString(Date now){
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(now);
    }
}
