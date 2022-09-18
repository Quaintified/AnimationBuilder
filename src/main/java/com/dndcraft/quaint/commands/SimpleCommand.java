package com.dndcraft.quaint.commands;

import com.dndcraft.quaint.AnimationBuilder;
import com.dndcraft.quaint.utils.Messenger;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;

public abstract class SimpleCommand implements CommandExecutor, TabExecutor {
    @Getter @Setter
    private String cmdName;

    public SimpleCommand(String cmdName) {
        setCmdName(cmdName);
    }

    public void register() {
        PluginCommand command = AnimationBuilder.getInstance().getServer().getPluginCommand(getCmdName());
        if (command == null) {
            Messenger.logWarning("Could not load command '" + getCmdName() + "' because we could not find it in the plugin.yml!");
            return;
        }
        command.setExecutor(this);
    }
}
