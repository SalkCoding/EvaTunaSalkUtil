package net.evatunasalkutil.salkcoding.main;

import net.evatunasalkutil.salkcoding.Constants;
import net.evatunasalkutil.salkcoding.event.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public Main() {
        if (instance != null)
            throw new IllegalStateException();
        instance = this;
    }

    @Override
    public void onEnable() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new BlockPlace(), this);
        manager.registerEvents(new EntitySpawn(), this);
        // manager.registerEvents(new PlayerTeleport(), this);
        manager.registerEvents(new PlayerInteract(), this);
        manager.registerEvents(new NetherChest(), this);
        manager.registerEvents(new EntityPotion(), this);
        System.out.println(Constants.Info_Format + "SalkUtil is working");
    }

    @Override
    public void onDisable() {
        System.out.println(Constants.Info_Format + "SalkUtil is disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()) {
            if (label.equalsIgnoreCase("sc")) {
                if (args.length <= 1) return false;
                String format;
                switch (args[0].toLowerCase()) {
                    case "info":
                        format = Constants.Info_Format;
                        break;
                    case "warn":
                        format = Constants.Warn_Format;
                        break;
                    case "error":
                        format = Constants.Error_Format;
                        break;
                    case "mob":
                        format = Constants.Mob_Format;
                        break;
                    default:
                        format = Constants.Info_Format;
                        break;
                }
                StringBuilder builder = new StringBuilder(format);
                for (int i = 1; i < args.length; i++) builder.append(args[i]).append(" ");
                for (Player player : Bukkit.getOnlinePlayers())
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', builder.toString()));
            }
        } else {
            sender.sendMessage(Constants.Error_Format + "You don't have a permission");
        }
        return true;
    }
}
