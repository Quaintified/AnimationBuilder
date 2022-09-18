package com.dndcraft.quaint.savables;

import com.dndcraft.quaint.utils.Messenger;
import de.leonhard.storage.sections.FlatFileSection;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SavedItemStack {
    @Getter @Setter
    private String material;
    @Getter @Setter
    private int count;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private List<String> lore = new ArrayList<>();
    @Getter @Setter
    private ConcurrentHashMap<String, Integer> enchants = new ConcurrentHashMap<>();

    public void loreFrom(String[] lore) {
        this.lore = new ArrayList<>();
        this.lore.addAll(Arrays.stream(lore).toList());
    }

    public void addLore(String line) {
        this.lore.add(line);
    }

    public void addEnchant(Enchantment enchantment, int level) {
        this.enchants.put(enchantment.getKey().getKey(), level);
    }

    public SavedItemStack(ItemStack from) {
        if (from == null) from = new ItemStack(Material.AIR);
        this.material = from.getType().toString();
        this.count = from.getAmount();
        this.name = Messenger.legacyColors(from.displayName());
        List<Component> components = from.lore();
        if (components != null) {
            for (Component component : components) {
                this.addLore(Messenger.legacyColors(component));
            }
        }
        for (Enchantment enchantment : from.getEnchantments().keySet()) {
            this.addEnchant(enchantment, from.getEnchantmentLevel(enchantment));
        }
    }

    public SavedItemStack(FlatFileSection section) {
        setMaterial(section.getString("material"));
        setCount(section.getInt("count"));
        setName(section.getString("name"));
        setLore(section.getStringList("lore"));

        for (String key : section.singleLayerKeySet("enchantments")) {
            Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString(key));
            if (enchantment == null) continue;
            addEnchant(enchantment, section.getInt("enchantments." + key));
        }
    }

    public void saveInto(FlatFileSection section) {
        section.set("material", getMaterial());
        section.set("count", getCount());
        section.set("name", getName());
        if (getLore() == null) {
            setLore(new ArrayList<>());
        }
        section.set("lore", getLore());

        if (getEnchants() == null) {
            setEnchants(new ConcurrentHashMap<>());
        }
        for (String key : getEnchants().keySet()) {
            section.set("enchantments." + key, getEnchants().get(key));
        }
    }

    public ItemStack get() {
        ItemStack stack = new ItemStack(
                Material.valueOf(this.material),
                this.count
        );
        if (stack.getType().equals(Material.AIR)) {
            return stack;
        }

        ItemMeta meta = stack.getItemMeta();
        if (meta == null) meta = Bukkit.getItemFactory().getItemMeta(stack.getType());
        if (meta == null) {
            Messenger.logWarning("Could not get meta for Material '" + this.getMaterial() + "'...");
            return stack;
        }
        meta.displayName(Messenger.codedComponent(this.name));
        List<Component> components = new ArrayList<>();
        for (String line : this.lore) {
            components.add(Messenger.codedComponent(line));
        }
        meta.lore(components);
        for (String enchant : enchants.keySet()) {
            Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString(enchant));
            if (enchantment == null) continue;
            meta.addEnchant(enchantment, enchants.get(enchant), false);
        }
        stack.setItemMeta(meta);
        return stack;
    }
}
