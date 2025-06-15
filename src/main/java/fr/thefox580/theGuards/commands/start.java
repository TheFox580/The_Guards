package fr.thefox580.theGuards.commands;

import fr.thefox580.theGuards.TheGuards;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class start implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        FileConfiguration config = TheGuards.getInstance().getConfig();

        boolean isReset = false;

        if (config.get("player_uuid") != null){
            isReset = true;
        }

        int randomNumber = new Random().nextInt(Bukkit.getOnlinePlayers().size());

        for (Player player : Bukkit.getOnlinePlayers()){
            if (randomNumber == 0){
                config.set("player_uuid", player.getUniqueId());
                TheGuards.getInstance().saveConfig();
                if (isReset){
                    Bukkit.broadcast(Component.text("[", NamedTextColor.DARK_GRAY)
                            .append(Component.text("The Guards", NamedTextColor.RED))
                            .append(Component.text("] ", NamedTextColor.DARK_GRAY))
                            .append(Component.text("The hardcore player has been chosen, again. Whoever may it be?", NamedTextColor.GRAY)));
                } else {
                    Bukkit.broadcast(Component.text("[", NamedTextColor.DARK_GRAY)
                            .append(Component.text("The Guards", NamedTextColor.RED))
                            .append(Component.text("] ", NamedTextColor.DARK_GRAY))
                            .append(Component.text("The hardcore player has been chosen. Whoever may it be?", NamedTextColor.GRAY)));
                    Objects.requireNonNull(Bukkit.getWorld("world")).getWorldBorder().setSize(2000);
                }
                return true;
            } else{
                randomNumber -= 1;
            }
        }

        return false;
    }
}
