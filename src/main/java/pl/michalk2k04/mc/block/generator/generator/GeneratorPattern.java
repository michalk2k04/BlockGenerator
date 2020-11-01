package pl.michalk2k04.mc.block.generator.generator;

/*
 *
 *   Created by michalk2k04
 *
 *   01.11.2020 14:05
 *
 */

import org.bukkit.Material;

public class GeneratorPattern
{
    private final Material material;

    private final String name;

    private final long respawnTime;

    public GeneratorPattern(Material material, String name, long respawnTime)
    {
        this.material = material;
        this.name = name;
        this.respawnTime = respawnTime;
    }

    public Material getMaterial()
    {
        return material;
    }

    public String getName()
    {
        return name;
    }

    public long getRespawnTime()
    {
        return respawnTime;
    }
}
