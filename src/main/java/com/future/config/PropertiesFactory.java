package com.future.config;

import com.future.exception.FutureException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.security.AccessControlException;
import java.util.Locale;
import java.util.Properties;

@Slf4j
public class PropertiesFactory {

    public static final String PROPERTIES_FILE = "com.gznb.inFuture.properties";

    private FutureException initException = null;

    private String propSrc = null;

    private PropertiesParser cfg;

    private PropertiesFactory() {
        //
    }

    /**
     * 通过外部输入流初始化
     * @param propertiesStream  外部输入流
     * @throws FutureException
     */
    public void initialize(InputStream propertiesStream) throws FutureException {
        // short-circuit if already initialized
        if (cfg != null) {
            return;
        }

        if (initException != null) {
            throw initException;
        }

        Properties props = new Properties();

        if (propertiesStream != null) {
            try {
                props.load(propertiesStream);
                propSrc = "外部打开的输入流.";
            } catch (IOException e) {
                initException = new FutureException(
                        "从输入流加载属性失败", e);
                throw initException;
            }
        } else {
            initException = new FutureException(
                    "Error loading property data from InputStream - InputStream is null.");
            throw initException;
        }

        initialize(props);
    }

    /**
     * 通过外部属性初始化
     * @param props   外部属性
     * @throws RuntimeException
     */
    public void initialize(Properties props) throws FutureException {
        if (propSrc == null) {
            propSrc = "外部提供的属性实例.";
        }

        this.cfg = new PropertiesParser(props);
    }

    /**
     * 默认初始化：查找设置的配置文件，默认的配置文件完成初始化
     * @throws FutureException
     */
    public void initialize() throws FutureException {
        // short-circuit if already initialized
        if (cfg != null) {
            return;
        }

        if (initException != null) {
            throw initException;
        }

        String requestedFile = System.getProperty(PROPERTIES_FILE);
        String propFileName = requestedFile != null ? requestedFile
                : "inFuture.properties";
        File propFile = new File(propFileName);

        Properties props = new Properties();

        try {
            if (propFile.exists()) {
                props = readFromPropFile(propFileName, requestedFile);
            } else if (requestedFile != null) {

                props = readFromReqFile(requestedFile);
            } else {
                props = readFromClassPath();
            }
        } catch (Exception e) {
            log.error("Read properties failed",e);
        }

        //initialize(overrideWithSysProps(props));
        initialize(props);
    }

    /**
     * 从当前工作目录读取配置文件
     * @param propFileName    配置文件名
     * @param requestedFile   请求文件名
     * @return
     */
    private Properties readFromPropFile(String propFileName, String requestedFile) throws Exception{
        Properties props = new Properties();

        try(FileInputStream fis = new FileInputStream(propFileName);
            InputStream in = new BufferedInputStream(fis);){
            if (requestedFile != null) {
                propSrc = "指定配置文件: '" + requestedFile + "'";
            } else {
                propSrc = "默认配置文件在当前工作目录: 'inFuture.properties'";
            }

            props.load(in);

        } catch (IOException ioe) {
            initException = new FutureException("不能读取属性文件: '"
                    + propFileName + "'", ioe);
            throw initException;
        }

        return props;
    }

    /**
     * 从类路径读取资源文件
     * @param requestedFile  资源文件名
     * @return
     */
    private Properties readFromReqFile(String requestedFile) throws Exception {
        Properties props = new Properties();
        try (InputStream in =
                     Thread.currentThread().getContextClassLoader().getResourceAsStream(requestedFile)) {


            if (in == null) {
                initException = new FutureException("不能读取配置文件: '"
                        + requestedFile + "'");
                throw initException;
            }

            propSrc = "指定配置文件（类资源路径）: '" + requestedFile + "'.";

            InputStream is = new BufferedInputStream(in);

            props.load(is);
        } catch (IOException ioe) {
            initException = new FutureException("不能读取配置文件: '"
                    + requestedFile + "'.", ioe);
            throw initException;
        }

        return props;
    }

    /**
     * 读取包内默认配置文件
     * @return
     */
    private Properties readFromClassPath() throws Exception {
        Properties props = new Properties();

        propSrc = "默认配置文件位于包内: 'inFuture.properties'";

        ClassLoader cl = getClass().getClassLoader();
        if(cl == null)
            cl = findClassloader();

        if(cl == null)
            throw new FutureException("未能在当前线程/类内找到类加载器.");

        InputStream in = cl.getResourceAsStream(
                "inFuture.properties");

        if (in == null) {
            in = cl.getResourceAsStream(
                    "/inFuture.properties");
        }

        if (in == null) {
            in = cl.getResourceAsStream(
                    "com/future/inFuture.properties");
        }

        if (in == null) {
            initException = new FutureException(
                    "未能在类路径找到默认配置文件：inFuture.properties");
            throw initException;
        }

        try {
            props.load(in);
        } catch (IOException ioe) {
            initException = new FutureException(
                    "未能在类路径读取到配置文件: 'com/future/inFuture.properties'.", ioe);
            throw initException;
        } finally {
            if(in != null) {
                try {
                    in.close();
                }catch (Exception e) {
                    log.error("关闭输入流失败",e);
                }
            }
        }

        return props;
    }

    /**
     * 用系统属性覆盖私有属性
     * @param props
     * @return
     */
    private Properties overrideWithSysProps(Properties props) {
        Properties sysProps = null;
        try {
            sysProps = System.getProperties();
        } catch (AccessControlException e) {
            log.warn("没有获取系统属性的权限.",e);
        }

        if (sysProps != null) {
            props.putAll(sysProps);
        }

        return props;
    }

    /**
     * 属性实例化
     * @throws FutureException
     */
    private void instantiate() throws FutureException {
        if (cfg == null) {
            initialize();
        }

        if (initException != null) {
            throw initException;
        }

/*        Properties groupProp = cfg.getPropertyGroup(MessagePropertiesConstants.EMAIL_EXCHANGE_PROPERTIES_PREFIX, true);
        try {
            setBeanProps(emailConfigProperties, groupProp);
        } catch (Exception e) {
            initException = new FutureException("EmailConfigProperties类属性配置失败.", e);
            throw initException;
        }

        groupProp = cfg.getPropertyGroup(MessagePropertiesConstants.SMS_PROPERTIES_PREFIX, true);
        try {
            setBeanProps(smsConfigProperties, groupProp);
        } catch (Exception e) {
            initException = new FutureException("SmsConfigProperties类属性配置失败.", e);
            throw initException;
        }*/

    }


    /**
     * 设置将属性值设置到对象同名属性
     * @param obj     目标对象
     * @param props   属性
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws IntrospectionException
     * @throws FutureException
     */
    private void setBeanProps(Object obj, Properties props)
            throws NoSuchMethodException, IllegalAccessException,
            java.lang.reflect.InvocationTargetException,
            IntrospectionException, FutureException {
        props.remove("class");

        BeanInfo bi = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propDescs = bi.getPropertyDescriptors();
        PropertiesParser pp = new PropertiesParser(props);

        java.util.Enumeration<Object> keys = props.keys();
        while (keys.hasMoreElements()) {
            String name = (String) keys.nextElement();
            String c = name.substring(0, 1).toUpperCase(Locale.US);
            String methName = "set" + c + name.substring(1);

            java.lang.reflect.Method setMeth = getSetMethod(methName, propDescs);

            try {
                if (setMeth == null) {
                    throw new NoSuchMethodException(
                            "属性：'" + name + "'没有setter方法");
                }

                Class<?>[] params = setMeth.getParameterTypes();
                if (params.length != 1) {
                    throw new NoSuchMethodException(
                            "属性： '" + name + "' 参数数量不是一");
                }

                // does the property value reference another property's value? If so, swap to look at its value
                PropertiesParser refProps = pp;
                String refName = pp.getStringProperty(name);
                if(refName != null && refName.startsWith("$@")) {
                    refName =  refName.substring(2);
                    refProps = cfg;
                }
                else
                    refName = name;

                if (params[0].equals(int.class) || params[0].equals(Integer.class)) {
                    setMeth.invoke(obj, new Object[]{Integer.valueOf(refProps.getIntProperty(refName))});
                } else if (params[0].equals(long.class) || params[0].equals(Long.class)) {
                    setMeth.invoke(obj, new Object[]{Long.valueOf(refProps.getLongProperty(refName))});
                } else if (params[0].equals(float.class) || params[0].equals(Float.class)) {
                    setMeth.invoke(obj, new Object[]{Float.valueOf(refProps.getFloatProperty(refName))});
                } else if (params[0].equals(double.class) || params[0].equals(Double.class)) {
                    setMeth.invoke(obj, new Object[]{Double.valueOf(refProps.getDoubleProperty(refName))});
                } else if (params[0].equals(boolean.class) || params[0].equals(Boolean.class)) {
                    setMeth.invoke(obj, new Object[]{Boolean.valueOf(refProps.getBooleanProperty(refName))});
                } else if (params[0].equals(String.class)) {
                    setMeth.invoke(obj, new Object[]{refProps.getStringProperty(refName)});
                }else {
                    throw new NoSuchMethodException(
                            "属性：'" + name + "' 没有对应参数类型的配置方法");
                }
            } catch (NumberFormatException nfe) {
                throw new FutureException("不能解析属性： '"
                        + name + "' 未正确的类型: " + nfe.toString());
            }
        }
    }

    /**
     * 获取属性设置方法
     * @param name      属性名
     * @param props     属性描述列表
     * @return
     */
    private java.lang.reflect.Method getSetMethod(String name,
                                                  PropertyDescriptor[] props) {
        for (int i = 0; i < props.length; i++) {
            java.lang.reflect.Method wMeth = props[i].getWriteMethod();

            if (wMeth != null && wMeth.getName().equals(name)) {
                return wMeth;
            }
        }

        return null;
    }

    /**
     * 加载类
     * @param className    类名
     * @return
     * @throws ClassNotFoundException
     * @throws RuntimeException
     */
    private Class<?> loadClass(String className) throws ClassNotFoundException, RuntimeException {

        try {
            ClassLoader cl = findClassloader();
            if(cl != null)
                return cl.loadClass(className);
            throw new RuntimeException("未能在当前线程/类内找到类加载器.");
        } catch (ClassNotFoundException e) {
            if(getClass().getClassLoader() != null)
                return getClass().getClassLoader().loadClass(className);
            throw e;
        }
    }

    /**
     * 查找当前类加载器
     * @return
     */
    private ClassLoader findClassloader() {
        if(Thread.currentThread().getContextClassLoader() == null && getClass().getClassLoader() != null) {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        }
        return Thread.currentThread().getContextClassLoader();
    }
}
