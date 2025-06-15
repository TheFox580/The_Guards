package fr.thefox580.theGuards.listensers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class netheriteBan implements Listener {

    Material[] materialList = {Material.ANCIENT_DEBRIS, Material.NETHERITE_AXE, Material.NETHERITE_BLOCK,
            Material.NETHERITE_BOOTS, Material.NETHERITE_HOE, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_HELMET,
            Material.NETHERITE_INGOT, Material.NETHERITE_LEGGINGS, Material.NETHERITE_PICKAXE, Material.NETHERITE_SCRAP,
            Material.NETHERITE_SHOVEL, Material.NETHERITE_SWORD, Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE};


    @EventHandler
    public void onMine(BlockBreakEvent event){
        if (event.getPlayer().getGameMode() == GameMode.SURVIVAL){
            if (Arrays.stream(materialList).anyMatch(material -> material == event.getBlock().getType())){
                event.setCancelled(true);
                Component displayName = new ItemStack(event.getBlock().getType()).displayName();
                event.getPlayer().sendMessage(Component.text("You are not allowed to mine ", NamedTextColor.RED)
                        .append(displayName.color(NamedTextColor.DARK_RED))
                        .append(Component.text(", as Netherite is banned.", NamedTextColor.RED)));
                event.getBlock().setType(Material.AIR);
            }
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event){
        if (event.getEntity() instanceof Player){
            Player player = ((Player) event.getEntity()).getPlayer();
            assert player != null;
            if (player.getGameMode() == GameMode.SURVIVAL){
                if (Arrays.stream(materialList).anyMatch(material -> material == event.getItem().getItemStack().getType())){
                    event.setCancelled(true);
                    player.sendMessage(Component.text("You are not allowed to mine ", NamedTextColor.RED)
                            .append(event.getItem().getItemStack().displayName().color(NamedTextColor.DARK_RED))
                            .append(Component.text(", as Netherite is banned.", NamedTextColor.RED)));
                    event.getItem().remove();
                }
            }
        }
    }

}
