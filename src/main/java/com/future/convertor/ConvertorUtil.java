package com.future.convertor;

import com.sun.javafx.binding.StringFormatter;
import lombok.experimental.UtilityClass;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

@UtilityClass
public class ConvertorUtil {

    public static <T> void trimBeanStringField(T sourceBean, T targetBean)
            throws IllegalAccessException,InstantiationException,InvocationTargetException{
        ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
        convertUtilsBean.register( new Converter(){
            public Object convert(Class type, Object value) {
                return StringUtils.trimToNull((String)value);
            }
        }, String.class);

        BeanUtilsBean beanUtilsBean = new BeanUtilsBean(convertUtilsBean);

        beanUtilsBean.copyProperties(targetBean, sourceBean);
    }

    public static <T> T trimBeanStringField(T sourceBean)
            throws IllegalAccessException,InstantiationException,InvocationTargetException{
        ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
        convertUtilsBean.register( new Converter(){
            public Object convert(Class type, Object value) {
                return StringUtils.trimToNull((String)value);
            }
        }, String.class);

        BeanUtilsBean beanUtilsBean = new BeanUtilsBean(convertUtilsBean);

        T targetBean = (T)sourceBean.getClass().newInstance();

        beanUtilsBean.copyProperties(targetBean, sourceBean);

        return targetBean;
    }
}
