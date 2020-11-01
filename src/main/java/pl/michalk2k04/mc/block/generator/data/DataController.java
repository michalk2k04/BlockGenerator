package pl.michalk2k04.mc.block.generator.data;

/*
 *
 *   Created by michalk2k04
 *
 *   01.11.2020 11:01
 *
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.michalk2k04.mc.block.generator.BlockGenerator;
import pl.michalk2k04.mc.block.generator.generator.Generator;
import pl.michalk2k04.mc.block.generator.generator.GeneratorImpl;
import pl.michalk2k04.mc.block.generator.generator.GeneratorPattern;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class DataController
{
    private final List<Generator> generators;
    
    private final Map<Material, GeneratorPattern> generatorPatterns;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    private final BlockGenerator blockGenerator;
    
    private final File generatorsFolder;

    private final File paternsFolder;

    private final Timer repairer;

    private final Timer respawner;

    public DataController(BlockGenerator blockGenerator)
    {
        this.blockGenerator = blockGenerator;
        this.generatorsFolder = new File(blockGenerator.getDataFolder() + File.separator + "generators");
        this.paternsFolder = new File(blockGenerator.getDataFolder()+File.separator+"patterns");
        this.generators = new ArrayList<>();
        this.generatorPatterns = new HashMap<>();
        load();

        repairer = new Timer();
        repairer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                for (Generator generator : generators)
                {
                    if (!generator.isBlockDestroyed())
                    {
                        if ((System.currentTimeMillis() - generator.getLastDestroyed()) > generator.getRespawnTime())
                        {
                            generator.regenerate(blockGenerator);
                        }
                    }
                }
            }
        }, 0, 20000);

        respawner = new Timer();
        respawner.schedule(new TimerTask() {
            @Override
            public void run()
            {
                for (Generator generator : generators)
                {
                    if (generator.isBlockDestroyed())
                    {
                        if ((System.currentTimeMillis() - generator.getLastDestroyed()) > generator.getRespawnTime())
                        {
                            generator.regenerate(blockGenerator);
                        }
                    }
                }
            }
        }, 0, blockGenerator.getBlockGeneratorConfig().getRefreshTime());
    }

    private void load()
    {
        checkFiles();

        for (File file : Objects.requireNonNull(paternsFolder.listFiles()))
        {
            if (file.getName().endsWith(".json"))
            {
                try (Stream<String> stream = Files.lines( Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8))
                {
                    StringBuilder sb = new StringBuilder();
                    stream.forEach(s -> sb.append(s).append("\n"));
                    GeneratorPattern generator = gson.fromJson(sb.toString(),GeneratorPattern.class);
                    generatorPatterns.put(generator.getMaterial(),generator);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        for (File file : Objects.requireNonNull(generatorsFolder.listFiles()))
        {
            if (file.getName().endsWith(".json"))
            {
                try (Stream<String> stream = Files.lines( Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8))
                {
                    StringBuilder sb = new StringBuilder();
                    stream.forEach(s -> sb.append(s).append("\n"));
                    Generator generator = gson.fromJson(sb.toString(), GeneratorImpl.class);
                    generators.add(generator);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void save() throws IOException
    {
        paternsFolder.deleteOnExit();
        generatorsFolder.deleteOnExit();

        checkFiles();

        for (GeneratorPattern generator : generatorPatterns.values())
        {
            File generatorFile = new File(paternsFolder.getAbsolutePath()+File.separator+generator.getMaterial().toString()+".json");

            if (!generatorFile.exists())
                generatorFile.createNewFile();

            FileWriter fileWriter = new FileWriter(generatorFile);

            fileWriter.write(gson.toJson(generator));
            fileWriter.flush();
            fileWriter.close();

        }

        for (Generator generator : generators)
        {
            File generatorFile = new File(generatorsFolder.getAbsolutePath()+File.separator+"x="+generator.getX()+"y="+generator.getY()+"z="+generator.getZ()+".json");

            if (!generatorFile.exists())
                generatorFile.createNewFile();

            FileWriter fileWriter = new FileWriter(generatorFile);

            fileWriter.write(gson.toJson(generator));
            fileWriter.flush();
            fileWriter.close();

        }
    }
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void checkFiles()
    {
        if (!blockGenerator.getDataFolder().exists())
            blockGenerator.getDataFolder().mkdir();
        if (!generatorsFolder.exists())
            generatorsFolder.mkdir();
        if (!paternsFolder.exists())
            paternsFolder.mkdir();

    }

    public BlockGenerator getBlockGenerator()
    {
        return blockGenerator;
    }

    public List<Generator> getGenerators()
    {
        return generators;
    }

    public Map<Material, GeneratorPattern> getGeneratorPatterns()
    {
        return generatorPatterns;
    }

    public void addGeneratorPattern(GeneratorPattern pattern)
    {
        if (generatorPatterns.containsKey(pattern.getMaterial()))
        {
            generatorPatterns.remove(pattern.getMaterial());
        }
        generatorPatterns.put(pattern.getMaterial(), pattern);

    }

    public ItemStack getGeneratorPatternItem(Material material)
    {
        GeneratorPattern pattern = generatorPatterns.get(material);

        ItemStack itemStack = new ItemStack(pattern.getMaterial());
        itemStack.setAmount(1);

        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(pattern.getName());

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public Timer getRepairer()
    {
        return repairer;
    }

    public Timer getRespawner()
    {
        return respawner;
    }
}
