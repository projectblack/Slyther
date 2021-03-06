package net.gegy1000.slyther.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.gegy1000.slyther.util.SystemUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public enum ConfigHandler {
    INSTANCE;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public <T> void saveConfig(File file, T configuration) throws Exception {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        PrintWriter out = new PrintWriter(new FileWriter(file));
        out.print(GSON.toJson(configuration));
        out.close();
    }

    public <T> T readConfig(File file, Class<T> configClass) throws Exception {
        if (file.exists()) {
            return GSON.fromJson(new FileReader(file), configClass);
        }
        return configClass.getDeclaredConstructor().newInstance();
    }
}
