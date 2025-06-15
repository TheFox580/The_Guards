package fr.thefox580.theGuards;

import fr.thefox580.theGuards.commands.start;
import fr.thefox580.theGuards.listensers.death;
import fr.thefox580.theGuards.listensers.netheriteBan;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TheGuards extends JavaPlugin {

    public static TheGuards getInstance() {
        return TheGuards.getPlugin(TheGuards.class);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("The Guards Season 2 plugin has been started");
        Objects.requireNonNull(getCommand("start")).setExecutor(new start()); //Add the /start command

        getServer().getPluginManager().registerEvents(new death(), this); //Registers the death message on player death event
        getServer().getPluginManager().registerEvents(new netheriteBan(), this); //Registers the netherite ban events
        Bukkit.getLogger().info("The Guards Season 2 plugin has sucessfully been loaded");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("The Guards Season 2 plugin has been stopped");
    }
}
