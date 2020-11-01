package pl.michalk2k04.mc.block.generator.listeners;

/*
 *
 *   Created by michalk2k04
 *
 *   01.11.2020 15:08
 *
 */

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.michalk2k04.mc.block.generator.BlockGenerator;
import pl.michalk2k04.mc.block.generator.generator.Generator;

public class OnBlockPlace implements Listener
{
    private final BlockGenerator blockGenerator;

    public OnBlockPlace(BlockGenerator blockGenerator)
    {
        this.blockGenerator = blockGenerator;
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event)
    {

        for(Generator generator : blockGenerator.getDataController().getGenerators())
        {
            if (generator.isThisGenerator(event.getBlock().getLocation()))
            {
                event.setCancelled(true);
                event.getPlayer().sendMessage(blockGenerator.getBlockGeneratorConfig().getInThisPlaceGeneratorAlredyIs());
            }
        }
    }
}
