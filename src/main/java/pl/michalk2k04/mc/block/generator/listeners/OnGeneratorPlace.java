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
import pl.michalk2k04.mc.block.generator.generator.GeneratorImpl;
import pl.michalk2k04.mc.block.generator.generator.GeneratorPattern;

import java.util.Objects;

public class OnGeneratorPlace implements Listener
{
    private final BlockGenerator blockGenerator;

    public OnGeneratorPlace(BlockGenerator blockGenerator)
    {
        this.blockGenerator = blockGenerator;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event)
    {
        if (blockGenerator.getDataController().getGeneratorPatterns().containsKey(event.getItemInHand().getType()))
        {
            GeneratorPattern pattern = blockGenerator.getDataController().getGeneratorPatterns().get(event.getItemInHand());

            if (Objects.requireNonNull(event.getItemInHand().getItemMeta()).getDisplayName().equals(pattern.getName()))
            {
                Generator generator = new GeneratorImpl(event.getBlockPlaced().getLocation(),pattern);

                blockGenerator.getDataController().getGenerators().add(generator);

                generator.onPlaceGenerator(blockGenerator,event);
            }
        }
    }

}
