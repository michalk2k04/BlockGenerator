package pl.michalk2k04.mc.block.generator;

/*
 *
 *   Created by michalk2k04
 *
 *   01.11.2020 10:55
 *
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.michalk2k04.mc.block.generator.commands.BlockGeneratorCommand;
import pl.michalk2k04.mc.block.generator.commands.BlockGeneratorTabComplete;
import pl.michalk2k04.mc.block.generator.data.BlockGeneratorConfig;
import pl.michalk2k04.mc.block.generator.data.DataController;
import pl.michalk2k04.mc.block.generator.listeners.OnBlockBreak;
import pl.michalk2k04.mc.block.generator.listeners.OnBlockPlace;
import pl.michalk2k04.mc.block.generator.listeners.OnGeneratorBreak;
import pl.michalk2k04.mc.block.generator.listeners.OnGeneratorPlace;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class BlockGenerator extends JavaPlugin
{
    private DataController dataController;

    private BlockGeneratorConfig blockGeneratorConfig;

    private final File configFile = new File(getDataFolder().getAbsolutePath()+File.separator+BlockGeneratorConfig.getConfigFileName());

    @Override
    public void onEnable()
    {
        this.dataController = new DataController(this);
        Objects.requireNonNull(Bukkit.getServer().getPluginCommand("blockgenerator")).setExecutor(new BlockGeneratorCommand(this));
        Objects.requireNonNull(Bukkit.getServer().getPluginCommand("blockgenerator")).setTabCompleter(new BlockGeneratorTabComplete(this));

        Bukkit.getServer().getPluginManager().registerEvents(new OnBlockBreak(this),this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnBlockPlace(this),this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnGeneratorBreak(this),this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnGeneratorPlace(this),this);

    }

    @SuppressWarnings({"MismatchedQueryAndUpdateOfStringBuilder", "ResultOfMethodCallIgnored"})
    @Override
    public void onLoad()
    {
        try
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            if (!configFile.exists())
            {
                configFile.createNewFile();
                FileWriter writer = new FileWriter(configFile);
                writer.write(gson.toJson(new BlockGeneratorConfig()));
                blockGeneratorConfig = new BlockGeneratorConfig();
            }
            else
            {
                try (Stream<String> stream = Files.lines(Paths.get(configFile.getAbsolutePath()), StandardCharsets.UTF_8))
                {
                    StringBuilder contentBuilder = new StringBuilder();
                    stream.forEach(s -> contentBuilder.append(s).append("\n"));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable()
    {
        dataController.getRespawner().cancel();
        dataController.getRepairer().cancel();
        try
        {
            dataController.save();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public DataController getDataController()
    {
        return this.dataController;
    }

    public BlockGeneratorConfig getBlockGeneratorConfig()
    {
        return blockGeneratorConfig;
    }
}
