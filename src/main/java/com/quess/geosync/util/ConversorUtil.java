package com.quess.geosync.util;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ConversorUtil {
    public String getSqlTimestampToString(Timestamp timestamp) {
        String string = timestamp.toString();
        return string.substring(8, 10) + "/" + string.substring(5, 7) + "/" + string.substring(0, 4) + " " + string.substring(11, 16);
    }

    public String getFloatToString(Float num) {
        String string = String.valueOf(num);
        return string.replace(".", ",");
    }
}
