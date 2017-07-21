package com.future.config;

import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@AllArgsConstructor
@Slf4j
public class PropertiesParser {

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 成员/属性.
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    Properties props = null;


    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 接口.
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    public Properties getUnderlyingProperties() {
        return props;
    }

    /**
     * 获取字符串属性值
     * @param name
     * @return
     */
    public String getStringProperty(String name) {
        return getStringProperty(name, null);
    }

    /**
     * 获取字符串属性，如果为空返回定义值
     * @param name  属性名
     * @param def   属性为空时的返回值
     * @return
     */
    public String getStringProperty(String name, String def) {
        String val = props.getProperty(name, def);
        if (val == null) {
            return def;
        }

        val = val.trim();

        return (val.length() == 0) ? def : val;
    }

    public String[] getStringArrayProperty(String name) {
        return getStringArrayProperty(name, null);
    }

    public String[] getStringArrayProperty(String name, String[] def) {
        String vals = getStringProperty(name);
        if (vals == null) {
            return def;
        }

        StringTokenizer stok = new StringTokenizer(vals, ",");
        ArrayList<String> strs = new ArrayList<>();
        try {
            while (stok.hasMoreTokens()) {
                strs.add(stok.nextToken().trim());
            }

            return strs.toArray(new String[strs.size()]);
        } catch (Exception e) {
            return def;
        }
    }

    public boolean getBooleanProperty(String name) {
        return getBooleanProperty(name, false);
    }

    public boolean getBooleanProperty(String name, boolean def) {
        String val = getStringProperty(name);

        return (val == null) ? def : Boolean.valueOf(val).booleanValue();
    }

    public byte getByteProperty(String name) throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            throw new NumberFormatException(" null string");
        }

        try {
            return Byte.parseByte(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public byte getByteProperty(String name, byte def)
            throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            return def;
        }

        try {
            return Byte.parseByte(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public char getCharProperty(String name) {
        return getCharProperty(name, '\0');
    }

    public char getCharProperty(String name, char def) {
        String param = getStringProperty(name);
        return  (param == null) ? def : param.charAt(0);
    }

    public double getDoubleProperty(String name) throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            throw new NumberFormatException(" null string");
        }

        try {
            return Double.parseDouble(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public double getDoubleProperty(String name, double def)
            throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            return def;
        }

        try {
            return Double.parseDouble(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public float getFloatProperty(String name) throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            throw new NumberFormatException(" null string");
        }

        try {
            return Float.parseFloat(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public float getFloatProperty(String name, float def)
            throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            return def;
        }

        try {
            return Float.parseFloat(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public int getIntProperty(String name) throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            throw new NumberFormatException(" NULL字符串");
        }

        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public int getIntProperty(String name, int def)
            throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            return def;
        }

        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public int[] getIntArrayProperty(String name) throws NumberFormatException {
        return getIntArrayProperty(name, null);
    }

    public int[] getIntArrayProperty(String name, int[] def)
            throws NumberFormatException {
        String vals = getStringProperty(name);
        if (vals == null) {
            return def;
        }

        StringTokenizer stok = new StringTokenizer(vals, ",");
        ArrayList<Integer> ints = new ArrayList<>();
        try {
            while (stok.hasMoreTokens()) {
                try {
                    ints.add(new Integer(stok.nextToken().trim()));
                } catch (NumberFormatException nfe) {
                    throw new NumberFormatException(" '" + vals + "'");
                }
            }

            int[] outInts = new int[ints.size()];
            for (int i = 0; i < ints.size(); i++) {
                outInts[i] = ints.get(i).intValue();
            }
            return outInts;
        } catch (Exception e) {
            return def;
        }
    }

    public long getLongProperty(String name) throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            throw new NumberFormatException("NULL字符串");
        }

        try {
            return Long.parseLong(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public long getLongProperty(String name, long def)
            throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            return def;
        }

        try {
            return Long.parseLong(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public short getShortProperty(String name) throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            throw new NumberFormatException("NULL字符串");
        }

        try {
            return Short.parseShort(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public short getShortProperty(String name, short def)
            throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            return def;
        }

        try {
            return Short.parseShort(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public String[] getPropertyGroups(String prefix) {
        Enumeration<?> keys = props.propertyNames();
        HashSet<String> groups = new HashSet<>(10);

        if (!prefix.endsWith(".")) {
            prefix += ".";
        }

        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            if (key.startsWith(prefix)) {
                String groupName = key.substring(prefix.length(), key.indexOf(
                        '.', prefix.length()));
                groups.add(groupName);
            }
        }

        return groups.toArray(new String[groups.size()]);
    }

    public Properties getPropertyGroup(String prefix) {
        return getPropertyGroup(prefix, false, null);
    }

    public Properties getPropertyGroup(String prefix, boolean stripPrefix) {
        return getPropertyGroup(prefix, stripPrefix, null);
    }

    /**
     * 获取所有匹配前缀的属性.
     *
     * @param prefix 属性前缀 不能以'.'结尾.
     * @param stripPrefix 是否将前缀从结果关键字中剥离.
     * @param excludedPrefixes 过滤属性前缀，可选参数.
     *
     * @return
     */
    public Properties getPropertyGroup(String prefix, boolean stripPrefix, String[] excludedPrefixes) {
        Enumeration<?> keys = props.propertyNames();
        Properties group = new Properties();

        if (!prefix.endsWith(".")) {
            prefix += ".";
        }

        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            if (key.startsWith(prefix)) {

                boolean exclude = false;
                if (excludedPrefixes != null) {
                    for (int i = 0; (i < excludedPrefixes.length) && (exclude == false); i++) {
                        exclude = key.startsWith(excludedPrefixes[i]);
                    }
                }

                if (exclude == false) {
                    String value = getStringProperty(key, "");

                    if (stripPrefix) {
                        group.put(key.substring(prefix.length()), value);
                    } else {
                        group.put(key, value);
                    }
                }
            }
        }

        return group;
    }
}
