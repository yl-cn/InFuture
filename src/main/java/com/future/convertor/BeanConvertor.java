package com.future.convertor;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.InvocationTargetException;

public class BeanConvertor extends BeanUtils {
    static {
        //注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空

        ConvertUtils.register(new DateConverterEx(), java.util.Date.class);
        ConvertUtils.register(new DateConverterEx(), String.class);
//          BeanUtilsBean beanUtils = new BeanUtilsBean(ConvertUtils.class,new PropertyUtilsBean());

    }

    public static void copyProperties(Object target, Object source) throws
            InvocationTargetException, IllegalAccessException {
        //支持对日期copy


        org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);


    }
}
