package fr.thefox580.theGuards.listensers;

import fr.thefox580.theGuards.TheGuards;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class death implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event){

        FileConfiguration config = TheGuards.getInstance().getConfig();

        Component deathMessage = Component.text("");

        if (event.deathMessage() != null){
            deathMessage = event.deathMessage();
        }

        assert deathMessage != null;
        deathMessage = deathMessage.color(NamedTextColor.GRAY);

        if (event.getPlayer().getUniqueId() == config.get("player_uuid")){
            event.deathMessage(Component.text("[", NamedTextColor.DARK_GRAY)
                    .append(Component.text("The Guards", NamedTextColor.RED))
                    .append(Component.text("] ", NamedTextColor.DARK_GRAY))
                    .append(Component.text(event.getPlayer().getName(), NamedTextColor.GOLD))
                    .append(Component.text(", the hardcore player, has died! ", NamedTextColor.GRAY))
                    .append(deathMessage));
            deathMessage = deathMessage.replaceText(builder -> {
                builder.match(event.getPlayer().getName()).replacement("You");
            });
            event.deathScreenMessageOverride(Component.text("You", NamedTextColor.GOLD)
                    .append(Component.text(", the hardcore player, has died! ", NamedTextColor.GRAY))
                    .append(deathMessage));
            event.getPlayer().setRespawnLocation(event.getPlayer().getLocation(), true);
            for (Player player : Bukkit.getOnlinePlayers()){
                player.setGameMode(GameMode.SPECTATOR);
            }
            Bukkit.getScheduler().runTaskLater(TheGuards.getInstance(), () -> {
                Bukkit.getLogger().severe("Hardcore player died OMEGALUL");
                event.getPlayer().spigot().respawn();
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
            }, 5*20L);
        } else {
            event.deathMessage(Component.text("[", NamedTextColor.DARK_GRAY)
                    .append(Component.text("The Guards", NamedTextColor.RED))
                    .append(Component.text("] ", NamedTextColor.DARK_GRAY))
                    .append(deathMessage)
                    .append(Component.text("! They were not the hardcore player.", NamedTextColor.GRAY)));
            deathMessage = deathMessage.replaceText(builder -> {
                builder.match(event.getPlayer().getName()).replacement("You");
            });
            event.deathScreenMessageOverride(deathMessage
                    .append(Component.text("! You were not the hardcore player.", NamedTextColor.GRAY)));
        }
    }

}
