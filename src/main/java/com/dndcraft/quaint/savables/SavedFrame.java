package com.dndcraft.quaint.savables;

import de.leonhard.storage.sections.FlatFileSection;
import lombok.Getter;
import lombok.Setter;
import me.thundertnt33.animatronics.api.Animatronic;
import me.thundertnt33.animatronics.api.ApiPose;
import org.bukkit.Location;

public class SavedFrame {
    @Getter @Setter
    private int index;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private SavedLocation location;

    @Getter @Setter
    private SavedItemStack handItem;
    @Getter @Setter
    private SavedItemStack offHandItem;
    @Getter @Setter
    private SavedItemStack headItem;
    @Getter @Setter
    private SavedEulerAngle headAngle;
    @Getter @Setter
    private SavedItemStack bodyItem;
    @Getter @Setter
    private SavedEulerAngle bodyAngle;
    @Getter @Setter
    private SavedEulerAngle leftArmAngle;
    @Getter @Setter
    private SavedEulerAngle rightArmAngle;
    @Getter @Setter
    private SavedItemStack legsItem;
    @Getter @Setter
    private SavedEulerAngle leftLegAngle;
    @Getter @Setter
    private SavedEulerAngle rightLegAngle;
    @Getter @Setter
    private SavedItemStack bootsItem;

    public SavedFrame(ApiPose pose, Location location) {
        setIndex(pose.getPoseNumber());
        setName(pose.getName());
        setLocation(new SavedLocation(location));

        setHandItem(new SavedItemStack(pose.getHandItem()));
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

    public SavedFrame(FlatFileSection section) {
        setIndex(section.getInt("index"));
        setName(section.getString("name"));
        setLocation(new SavedLocation(section.getSection("location")));

        setHandItem(new SavedItemStack(section.getSection("hand.main.item")));
        setOffHandItem(new SavedItemStack(section.getSection("hand.other.item")));

        setHeadItem(new SavedItemStack(section.getSection("head.item")));
        setHeadAngle(new SavedEulerAngle(section.getSection("head.angle")));

        setBodyItem(new SavedItemStack(section.getSection("body.item")));
        setBodyAngle(new SavedEulerAngle(section.getSection("body.angle")));
        setLeftArmAngle(new SavedEulerAngle(section.getSection("arm.left.angle")));
        setRightArmAngle(new SavedEulerAngle(section.getSection("arm.right.angle")));

        setLegsItem(new SavedItemStack(section.getSection("legs.item")));
        setLeftLegAngle(new SavedEulerAngle(section.getSection("leg.left.angle")));
        setRightLegAngle(new SavedEulerAngle(section.getSection("leg.right.angle")));

        setBootsItem(new SavedItemStack(section.getSection("boots.item")));
    }

    public void saveInto(FlatFileSection section) {
        section.set("index", getIndex());
        section.set("name", getName());
        getLocation().saveInto(section.getSection("location"));

        getHandItem().saveInto(section.getSection("hand.main.item"));
        getOffHandItem().saveInto(section.getSection("hand.other.item"));

        getHeadItem().saveInto(section.getSection("head.item"));
        getHeadAngle().saveInto(section.getSection("head.angle"));

        getBodyItem().saveInto(section.getSection("body.item"));
        getBodyAngle().saveInto(section.getSection("body.angle"));
        getLeftArmAngle().saveInto(section.getSection("arm.left.angle"));
        getRightArmAngle().saveInto(section.getSection("arm.right.angle"));

        getLegsItem().saveInto(section.getSection("legs.item"));
        getLeftLegAngle().saveInto(section.getSection("leg.left.angle"));
        getRightLegAngle().saveInto(section.getSection("leg.right.angle"));

        getBootsItem().saveInto(section.getSection("boots.item"));
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
