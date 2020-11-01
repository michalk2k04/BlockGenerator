package pl.michalk2k04.mc.block.generator.listeners;

/*
 *
 *   Created by michalk2k04
 *
 *   01.11.2020 15:08
 *
 */

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.michalk2k04.mc.block.generator.BlockGenerator;
import pl.michalk2k04.mc.block.generator.generator.Generator;

public class OnGeneratorBreak implements Listener
{
    private final BlockGenerator blockGenerator;

    public OnGeneratorBreak(BlockGenerator blockGenerator)
    {
        this.blockGenerator = blockGenerator;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event)
    {
        if (event.getPlayer().getItemInHand().getType() == Material.GOLDEN_PICKAXE)
        {
            for (int i = 0 ; i < blockGenerator.getDataController().getGenerators().size() ; i++)
            {
                Generator generator = blockGenerator.getDataController().getGenerators().get(i);

                if (generator.isThisGenerator(event.getBlock().getLocation()))
                {
                    generator.onDestroyGenerator(blockGenerator,event);
                    return;
                }
            }
        }
    }
}
