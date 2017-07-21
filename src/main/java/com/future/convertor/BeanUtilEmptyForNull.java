package com.future.convertor;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class BeanUtilEmptyForNull extends BeanUtilsBean {

    @Override
    public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
        if (value == null) {
            super.copyProperty(bean, name, "");
        }
        else if(value instanceof Date) {
            String strDate = DateFormatUtils.format((Date)value, "yyyyMMddHHmmss");
            super.copyProperty(bean, name, strDate);
        }
        else {
            super.copyProperty(bean, name, value);
        }

    }
}


