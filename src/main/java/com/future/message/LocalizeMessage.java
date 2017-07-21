package com.future.message;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocalizeMessage {
    private static final String BUNDLE_NAME = "com.future.message.LocalizedErrorMessages";
    private static final ResourceBundle RESOURCE_BUNDLE;

    private LocalizeMessage() {
    }

    static {
        ResourceBundle temp = null;

        try {
            temp = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault(), LocalizeMessage.class.getClassLoader());
        } catch (Throwable var10) {
            try {
                temp = ResourceBundle.getBundle(BUNDLE_NAME);
            } catch (Throwable var9) {
                RuntimeException rt = new RuntimeException("Can't load resource bundle due to underlying exception " + var10.toString());
                rt.initCause(var9);
                throw rt;
            }
        } finally {
            RESOURCE_BUNDLE = temp;
        }

    }

    public static String getString(String key) {
        if(RESOURCE_BUNDLE == null) {
            throw new RuntimeException("Localized messages from resource bundle '"+ BUNDLE_NAME + "' not loaded during initialization.");
        } else {
            try {
                if(key == null) {
                    throw new IllegalArgumentException("Message key can not be null");
                } else {
                    String message = RESOURCE_BUNDLE.getString(key);
                    if(message == null) {
                        message = "Missing error message for key '" + key + "'";
                    }

                    return message;
                }
            } catch (MissingResourceException var2) {
                return '!' + key + '!';
            }
        }
    }

    public static String getString(String key, Object[] args) {
        return MessageFormat.format(getString(key), args);
    }

}

