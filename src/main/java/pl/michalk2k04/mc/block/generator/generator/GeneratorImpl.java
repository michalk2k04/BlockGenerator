package pl.michalk2k04.mc.block.generator.generator;

/*
 *
 *   Created by michalk2k04
 *
 *   01.11.2020 14:17
 *
 */

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.michalk2k04.mc.block.generator.BlockGenerator;

import java.util.Objects;

public class GeneratorImpl implements Generator
{
    private final Material material;

    private final String worldName;

    private final int x;
    private final int y;
    private final int z;

    private boolean destroyed;

    private long lastDestroyed;

    private final long respawnTime;

    @SuppressWarnings("unused")
    public GeneratorImpl(Material material, String worldName, int x, int y, int z, boolean destroyed, long lastDestroyed, long respawnTime)
    {
        this.material = material;
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.destroyed = destroyed;
        this.lastDestroyed = lastDestroyed;
        this.respawnTime = respawnTime;
    }

    public GeneratorImpl(Location location , GeneratorPattern pattern)
    {
        this.material = pattern.getMaterial();
        this.worldName = Objects.requireNonNull(location.getWorld()).getName();
        this.x = location.getBlockX();
        this.y = location.getBlockX();
        this.z = location.getBlockX();
        this.destroyed = false;
        this.lastDestroyed = System.currentTimeMillis();
        this.respawnTime = pattern.getRespawnTime();

    }

    @Override
    public Material getMaterial()
    {
        return material;
    }


    @Override
    public int getX()
    {
        return x;
    }

    @Override
    public int getY()
    {
        return y;
    }

    @Override
    public int getZ()
    {
        return z;
    }

    @Override
    public boolean isThisGenerator(Location location)
    {
        return false;
    }

    @Override
    public boolean isBlockDestroyed()
    {
        return destroyed;
    }

    @Override
    public long getLastDestroyed()
    {
        return lastDestroyed;
    }

    @Override
    public long getRespawnTime()
    {
        return respawnTime;
    }

    @Override
    public void onPlaceGenerator(BlockGenerator blockGenerator,BlockPlaceEvent event)
    {
        placeBlock(blockGenerator);
        lastDestroyed = System.currentTimeMillis();
        event.getPlayer().sendMessage(blockGenerator.getBlockGeneratorConfig().getGeneratorPlaced());
    }

    @Override
    public void onDestroyGenerator(BlockGenerator blockGenerator,BlockBreakEvent event)
    {
        event.setDropItems(false);
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), blockGenerator.getDataController().getGeneratorPatternItem(material));
        blockGenerator.getDataController().getGenerators().remove(this);
        event.getPlayer().sendMessage(blockGenerator.getBlockGeneratorConfig().getGeneratorRemoved());
    }

    @Override
    public void onDestroyBlock(BlockGenerator blockGenerator,BlockBreakEvent event)
    {
        destroyed = true;
        lastDestroyed = System.currentTimeMillis();
    }

    @Override
    public void placeBlock(BlockGenerator blockGenerator)
    {
        new BukkitRunnable() { public void run() { Objects.requireNonNull(Bukkit.getWorld(worldName)).getBlockAt(x, y, z).setType(material); }}.runTask(blockGenerator);
        destroyed = false;
    }

    @Override
    public void regenerate(BlockGenerator blockGenerator)
    {
        placeBlock(blockGenerator);
    }


}
