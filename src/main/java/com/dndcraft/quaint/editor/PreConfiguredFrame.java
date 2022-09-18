package com.dndcraft.quaint.editor;

import com.dndcraft.quaint.savables.SavedEulerAngle;
import com.dndcraft.quaint.savables.SavedItemStack;
import com.dndcraft.quaint.savables.SavedLocation;
import de.leonhard.storage.sections.FlatFileSection;
import lombok.Getter;
import lombok.Setter;
import me.thundertnt33.animatronics.api.Animatronic;
import me.thundertnt33.animatronics.api.ApiPose;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class PreConfiguredFrame {
    @Getter @Setter
    private ItemStack handItem;
    @Getter @Setter
    private ItemStack offHandItem;
    @Getter @Setter
    private ItemStack headItem;
    @Getter @Setter
    private EulerAngle headAngle;
    @Getter @Setter
    private ItemStack bodyItem;
    @Getter @Setter
    private EulerAngle bodyAngle;
    @Getter @Setter
    private EulerAngle leftArmAngle;
    @Getter @Setter
    private EulerAngle rightArmAngle;
    @Getter @Setter
    private ItemStack legsItem;
    @Getter @Setter
    private EulerAngle leftLegAngle;
    @Getter @Setter
    private EulerAngle rightLegAngle;
    @Getter @Setter
    private ItemStack bootsItem;

    public PreConfiguredFrame(Location location, PreConfiguredItemsList itemsList) {
        setHandItem(new SavedItemStack(itemsList.);
        setOffHandItem(new SavedItemStack(pose.getOffHandItem()));

        setHeadItem(new SavedItemStack(pose.getHeadItem()));
        setHeadAngle(new SavedEulerAngle(pose.getHeadAngle()));

        setBodyItem(new SavedItemStack(pose.getBodyItem()));
        setBodyAngle(new SavedEulerAngle(pose.getBodyAngle()));
        setLeftArmAngle(new SavedEulerAngle(pose.getLeftArmAngle()));
        setRightArmAngle(new SavedEulerAngle(pose.getRightArmAngle()));

        setLegsItem(new SavedItemStack(pose.getLegsItem()));
        setLeftLegAngle(new SavedEulerAngle(pose.getLeftLegAngle()));
        setRightLegAngle(new SavedEulerAngle(pose.getRightLegAngle()));

        setBootsItem(new SavedItemStack(pose.getBootsItem()));
    }

    public ApiPose get(Animatronic parent) {
        parent.addPose(
                getLocation().get(),
                getHeadAngle().get(),
                getBodyAngle().get(),
                getLeftArmAngle().get(),
                getRightArmAngle().get(),
                getLeftLegAngle().get(),
                getRightLegAngle().get()
        );
        parent.setHelmet(getIndex(), getHeadItem().get());
        parent.setChestplate(getIndex(), getBodyItem().get());
        parent.setLeggings(getIndex(), getLegsItem().get());
        parent.setBoots(getIndex(), getBootsItem().get());

        return parent.getPose(getIndex());
    }
}
