package ru.appline.ozoneFramework.managers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropMngr {
    private final Properties props =new Properties();
    private static PropMngr instance = null;

    private PropMngr(){
        loadProps();
        loadCustomProperties();
    }

    public static PropMngr GetPropMngr(){
        if(instance == null){
            instance = new PropMngr();
        }
        return instance;
    }
    public String getProperty(String key) {
        return props.getProperty(key);
    }

    private void loadProps(){
        try {
            props.load(new FileInputStream("src/main/resources/sets.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadCustomProperties() {
        props.forEach((key, value) -> System.getProperties()
                .forEach((customUserKey, customUserValue) -> {
                    if (key.toString().equals(customUserKey.toString()) &&
                            !value.toString().equals(customUserValue.toString())) {
                        props.setProperty(key.toString(), customUserValue.toString());
                    }
                }));
    }
}
