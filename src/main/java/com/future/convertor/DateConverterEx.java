package com.future.convertor;

import org.apache.commons.beanutils.converters.DateTimeConverter;

import java.util.Date;

public final class DateConverterEx extends DateTimeConverter {
    private boolean returnEmptyForNull = false;

    public DateConverterEx() {
    }

    public DateConverterEx(Object defaultValue) {
        super(defaultValue);
    }

/*    @Override
    protected <T> T handleMissing(Class<T> type) {
        T obj = super.handleMissing(type);
        if(!type.equals(String.class)) {
            Object value = this.getDefault(type);
            if( value != null && !type.equals(value.getClass())) {
                try {
                    value = this.convertToType(type, "");
                } catch (Throwable var4) {
                    throw new ConversionException("Default conversion to  failed.", var4);
                }
            }

            return type.cast(value);
        }
        return obj;
    }*/

   @Override
    public <T> T convert(Class<T> type, Object value) {
        T targetObj = super.convert(type, value);
        return targetObj;
    }

    @Override
    protected String convertToString(Object value) throws Throwable {
        String result = super.convertToString(value);
        if(null == result) {
            result = "";
        }
        return result;
    }

    @Override
    protected <T> T convertToType(Class<T> targetType, Object value) throws Exception {
        T targetObj = super.convertToType(targetType, value);
        return targetObj;
    }

    protected Class<?> getDefaultType() {
        return Date.class;
    }
}
