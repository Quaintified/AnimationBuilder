package com.dndcraft.quaint;

import com.dndcraft.quaint.commands.SimpleCommand;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class AnimationBuilder extends JavaPlugin {
    @Getter
    private static AnimationBuilder instance;

    @Getter
    private static final List<SimpleCommand> executors = List.of(
            new LoadCommand(),
            new SaveCommand()
    );

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        executors.forEach(SimpleCommand::register);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
