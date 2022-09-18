package com.dndcraft.quaint.editor;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ConcurrentSkipListMap;

public class PreConfiguredItemsList {
    public enum ItemType {
        HANDS(1),
        ARMOR(2, 5),
        ;

        @Getter
        private final int minimum;
        @Getter
        private final int maximum;

        ItemType(int minimum, int maximum) {
            this.minimum = minimum;
            this.maximum = maximum;
        }

        ItemType(int maximum) {
            this(0, maximum);
        }
    }

    @Getter @Setter
    private ConcurrentSkipListMap<Integer, ItemStack> items = new ConcurrentSkipListMap<>();

    public PreConfiguredItemsList(ItemStack... stacks) {
        for (ItemStack stack : stacks) {
            addStack(stack);
        }
    }

    public void addStack(int index, ItemStack stack) {
        if (! canAppendMore()) return;
        getItems().put(index, stack);
    }

    public void addStack(ItemStack stack) {
        addStack(size(), stack);
    }

    public int size() {
        return getItems().size();
    }

    public boolean canAppendMore() {
        return size() < ItemType.ARMOR.getMaximum() + 1;
    }

    public ItemStack[] asArray() {
        return getItems().values().toArray(new ItemStack[0]);
    }

    public ItemStack[] getHandsAsArray() {
        ItemStack[] r = new ItemStack[ItemType.HANDS.getMaximum() - ItemType.HANDS.getMinimum() + 1];

    }
}
