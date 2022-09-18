package com.dndcraft.quaint.savables;

import com.dndcraft.quaint.AnimationBuilder;
import de.leonhard.storage.sections.FlatFileSection;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

public class SavedLocation {
    @Getter @Setter
    private String world;
    @Getter @Setter
    private double x, y, z;
    @Getter @Setter
    private float yaw, pitch;

    public SavedLocation(Location from) {
        setWorld(from.getWorld().getName());
        setX(from.getX());
        setY(from.getY());
        setZ(from.getZ());
        setYaw(from.getYaw());
        setPitch(from.getPitch());
    }

    public SavedLocation(FlatFileSection section) {
        setWorld(section.getString("world"));
        setX(section.getDouble("x"));
        setY(section.getDouble("y"));
        setZ(section.getDouble("z"));
        setYaw(section.getFloat("yaw"));
        setPitch(section.getFloat("pitch"));
    }

    public void saveInto(FlatFileSection section) {
        section.set("world", getWorld());
        section.set("x", getX());
        section.set("y", getY());
        section.set("z", getZ());
        section.set("yaw", getYaw());
        section.set("pitch", getPitch());
    }

    public Location get() {
        return new Location(AnimationBuilder.getInstance().getServer().getWorld(getWorld()),
                getX(), getY(), getZ(),
                getYaw(), getPitch()
        );
    }
}
