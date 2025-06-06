package com.example;

import net.Mirik9724.api.Config;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        String resourcePath = "config/default-config.yml";
        File targetDir = new File("plugins/myplugin");

        // Copy the config file from the JAR resources to the target directory
        File configFile = Config.cloneConfigFromJar(resourcePath, targetDir);

        System.out.println("Config file copied to: " + configFile.getAbsolutePath());

        // Load YAML configuration from the copied file
        Object configData = Config.loadYamlConfig(configFile);

        // Print loaded config data (usually a Map or List)
        System.out.println("Loaded config data: " + configData);
    }
}
