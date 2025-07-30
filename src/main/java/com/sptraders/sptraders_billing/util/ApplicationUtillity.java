package com.sptraders.sptraders_billing.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationUtillity {

    public static String dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
