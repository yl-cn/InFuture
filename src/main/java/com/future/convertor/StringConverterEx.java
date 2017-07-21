package com.future.convertor;

import org.apache.commons.beanutils.converters.AbstractConverter;

public final class StringConverterEx extends AbstractConverter {
    public StringConverterEx() {
    }

    public StringConverterEx(Object defaultValue) {
        super(defaultValue);
    }

    protected Class<?> getDefaultType() {
        return String.class;
    }

    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        if(!String.class.equals(type) && !Object.class.equals(type)) {
            throw this.conversionException(type, value);
        } else {
            return type.cast(value.toString());
        }
    }
}
