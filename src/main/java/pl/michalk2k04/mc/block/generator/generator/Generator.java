package pl.michalk2k04.mc.block.generator.generator;

/*
 *
 *   Created by michalk2k04
 *
 *   01.11.2020 11:02
 *
 */

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.michalk2k04.mc.block.generator.BlockGenerator;

public interface Generator
{
    Material getMaterial();

    int getX();

    int getY();

    int getZ();

    boolean isThisGenerator(Location location);

    boolean isBlockDestroyed();

    long getLastDestroyed();

    long getRespawnTime();

    void onPlaceGenerator(BlockGenerator blockGenerator,BlockPlaceEvent event);

    void onDestroyGenerator(BlockGenerator blockGenerator,BlockBreakEvent event);

    void onDestroyBlock(BlockGenerator blockGenerator,BlockBreakEvent event);

    void placeBlock(BlockGenerator blockGenerator);

    void regenerate(BlockGenerator blockGenerator);
}
