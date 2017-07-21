package com.future.convertor;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class DateConvert implements Converter {

    private String dateTimePattern = "yyyy-MM-dd HH:mm:ss";

    private boolean nullToEmpty = true;

    @Override
    public Object convert(Class targetClass, Object sourceValue) {
        if(null == sourceValue && targetClass.equals(String.class) && nullToEmpty){
            return "";
        }

        if(sourceValue instanceof Date && targetClass.equals(String.class) ){
            return DateFormatUtils.format((Date)sourceValue, this.dateTimePattern);
        }

        if (sourceValue instanceof Long) {
            Long longValue = (Long) sourceValue;
            return new Date(longValue.longValue());
        }
        if (sourceValue instanceof String) {
            String dateStr = (String)sourceValue;
            Date endTime = null;
            try {
                endTime = DateUtils.parseDate(dateStr, this.dateTimePattern);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return endTime;
        }
        return sourceValue;
    }
}
