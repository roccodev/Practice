package pw.roccodev.bukkit.practice.utils;

import pw.roccodev.bukkit.practice.PracticePlugin;

import java.io.File;
import java.io.IOException;

public class FileCheck {

    private static void checkNotExist(String name, boolean dir) {
        File res = new File(PracticePlugin.PLUGIN_DIR + name);
        if(!res.exists()) {
            if(dir) res.mkdirs();
            else {
                try {
                    res.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void initDirs() {
        checkNotExist("maps", true);
        checkNotExist("maps/schematics", true);
    }

}
