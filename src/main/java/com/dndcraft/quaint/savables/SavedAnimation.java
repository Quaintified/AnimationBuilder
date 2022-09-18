package com.dndcraft.quaint.savables;

import com.dndcraft.quaint.AnimationBuilder;
import com.dndcraft.quaint.savables.SavedFrame;
import com.dndcraft.quaint.savables.SavedLocation;
import com.dndcraft.quaint.utils.StorageUtils;
import de.leonhard.storage.Toml;
import lombok.Getter;
import lombok.Setter;
import me.thundertnt33.animatronics.api.Animatronic;
import me.thundertnt33.animatronics.api.ApiPose;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

public class SavedAnimation {
    @Getter
    private static File animationFolder = new File(AnimationBuilder.getInstance().getDataFolder(), "saved-animations" + File.separator);

    @Getter
    private Toml toml;
    @Getter
    private final String name;
    @Getter
    private final File file;

    @Getter @Setter
    private ConcurrentSkipListMap<Integer, SavedFrame> frames;

    public SavedAnimation(String name, boolean load) {
        getAnimationFolder().mkdirs();

        this.name = name;
        this.file = new File(getAnimationFolder(), name + ".toml");
        this.frames = new ConcurrentSkipListMap<>();

        reload();

        if (load) {
            for (String key : getToml().singleLayerKeySet("frames")) {
                try {
                    int i = Integer.parseInt(key);

                    getFrames().put(i, new SavedFrame(getToml().getSection("frames." + i)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public SavedAnimation(String name) {
        this(name, true);
    }

    public SavedAnimation(String name, Animatronic animatronic) {
        this(name, false);

        ApiPose[] poses = animatronic.getPositions();

        if (poses != null) {
            for (int i = 0; i < poses.length; i++) {
                ApiPose pose = poses[i];
                getFrames().put(pose.getPoseNumber(), new SavedFrame(pose, animatronic.getArmorstand().getLocation()));
            }
        }
    }

    public Toml load() {
        return StorageUtils.loadConfigNoDefault(this.file);
    }

    public void reload() {
        this.toml = load();
    }

    public Animatronic get() {
        Animatronic animatronic = new Animatronic(getName());

        for (int i : getFrames().keySet()) {
            SavedFrame frame = getFrames().get(i);

            SavedLocation location = frame.getLocation();

            World world = AnimationBuilder.getInstance().getServer().getWorld(location.getWorld());
            if (world == null) continue;

            ArmorStand stand = world.spawn(location.get(), ArmorStand.class);
            stand.getEquipment().setHelmet(frame.getHeadItem().get(), true);
            stand.getEquipment().setChestplate(frame.getBodyItem().get(), true);
            stand.getEquipment().setLeggings(frame.getLegsItem().get(), true);
            stand.getEquipment().setBoots(frame.getBootsItem().get(), true);

            animatronic.addPose(stand);
        }

        return animatronic;
    }

    public void save() {
        toml.set("name", getName());

        for (int i : frames.keySet()) {
            SavedFrame frame = frames.get(i);

            frame.saveInto(toml.getSection("frames." + i));
        }
    }
}
