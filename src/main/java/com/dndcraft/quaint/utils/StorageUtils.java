package com.dndcraft.quaint.utils;

import com.dndcraft.quaint.AnimationBuilder;
import de.leonhard.storage.Config;
import de.leonhard.storage.SimplixBuilder;
import de.leonhard.storage.Toml;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

public class StorageUtils {
    public static Toml loadConfigFromSelf(File file, String fileString) {
        if (! file.exists()) {
            file.getParentFile().mkdirs();
            try {
                AnimationBuilder.getInstance().getDataFolder().mkdirs();
                try (InputStream in = AnimationBuilder.getInstance().getResource(fileString)) {
                    assert in != null;
                    Files.copy(in, file.toPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return SimplixBuilder.fromFile(file).createToml();
    }

    public static Toml loadConfigNoDefault(File file) {
        if (! file.exists()) {
            file.getParentFile().mkdirs();
            try {
                AnimationBuilder.getInstance().getDataFolder().mkdirs();
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return SimplixBuilder.fromFile(file).createToml();
    }
}
