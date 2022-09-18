package com.dndcraft.quaint.savables;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

public class SavableManager {
    public static ConcurrentSkipListMap<String, File> getSavedAnimationFilesByName() {
        ConcurrentSkipListMap<String, File> r = new ConcurrentSkipListMap<>();

        File[] files = SavedAnimation.getAnimationFolder().listFiles();

        if (files == null) {
            return r;
        }

        for (File file : files) {
            if (! file.isFile()) continue;
            if (! file.exists()) continue;
            if (! file.getName().endsWith(".toml")) continue;

            String name = file.getName().substring(0, file.getName().lastIndexOf("."));
            r.put(name, file);
        }

        return r;
    }
}
