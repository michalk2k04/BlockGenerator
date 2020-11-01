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
import org.bukkit.event.block.BlockBreakEvent;
import pl.michalk2k04.mc.block.generator.BlockGenerator;
import pl.michalk2k04.mc.block.generator.generator.Generator;

public class OnBlockBreak implements Listener
{
    private final BlockGenerator blockGenerator;

    public OnBlockBreak(BlockGenerator blockGenerator)
    {
        this.blockGenerator = blockGenerator;
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event)
    {
        for(Generator generator : blockGenerator.getDataController().getGenerators())
        {
            if (generator.isThisGenerator(event.getBlock().getLocation()))
            {
                generator.onDestroyBlock(blockGenerator,event);
            }
        }
    }
}
