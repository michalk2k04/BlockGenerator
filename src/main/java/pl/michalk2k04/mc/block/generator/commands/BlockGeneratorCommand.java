package pl.michalk2k04.mc.block.generator.commands;

/*
 *
 *   Created by michalk2k04
 *
 *   01.11.2020 12:28
 *
 */

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.michalk2k04.mc.block.generator.BlockGenerator;
import pl.michalk2k04.mc.block.generator.generator.Generator;
import pl.michalk2k04.mc.block.generator.generator.GeneratorPattern;

import java.util.ArrayList;
import java.util.List;

public class BlockGeneratorCommand implements CommandExecutor
{
    private final BlockGenerator blockGenerator;

    public BlockGeneratorCommand(BlockGenerator blockGenerator)
    {
        this.blockGenerator = blockGenerator;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 0)
        {
            Material material;


            if (args.length == 1)
            {
                sender.sendMessage(blockGenerator.getBlockGeneratorConfig().getWrongUsage().replace("<MATERIAL>",ChatColor.RED+"<MATERIAL>"+ChatColor.GREEN));
                return true;
            }

            material = Material.matchMaterial(args[1]);

            if (material == null)
            {
                sender.sendMessage(blockGenerator.getBlockGeneratorConfig().getWrongUsage().replace("<MATERIAL>",ChatColor.RED+"<MATERIAL>"+ChatColor.GREEN));
                return true;
            }

            if (args[0].equalsIgnoreCase("add"))
            {
                if (args.length == 2)
                {
                    sender.sendMessage(blockGenerator.getBlockGeneratorConfig().getWrongUsage().replace("<RESPAWN TIME>",ChatColor.RED+"<RESPAWN TIME>"+ChatColor.GREEN));
                    return true;
                }

                long respawTime;

                try
                {
                    respawTime = Long.parseLong(args[2]);
                }
                catch (NumberFormatException e)
                {
                    sender.sendMessage(blockGenerator.getBlockGeneratorConfig().getWrongUsage().replace("<RESPAWN TIME>",ChatColor.RED+"<RESPAWN TIME>"+ChatColor.GREEN));
                    return true;
                }

                if (args.length == 3)
                {
                    sender.sendMessage(blockGenerator.getBlockGeneratorConfig().getWrongUsage().replace("<GENERATOR NAME>",ChatColor.RED+"<GENERATOR NAME>"+ChatColor.GREEN));
                    return true;
                }

                StringBuilder sb = new StringBuilder();

                for (int i = 3 ; i < args.length ; i ++)
                {
                    sb.append(args[i]).append(" ");
                }

                String string = sb.toString().replace('&','§').trim();

                if (!string.startsWith("§"))
                    string = "§1"+string;

                GeneratorPattern pattern = new GeneratorPattern(material,string,respawTime);

                blockGenerator.getDataController().addGeneratorPattern(pattern);

                sender.sendMessage(blockGenerator.getBlockGeneratorConfig().getGeneratorCreated());
                return true;
            }
            else if (args[0].equalsIgnoreCase("remove"))
            {
                if (blockGenerator.getDataController().getGeneratorPatterns().containsKey(material))
                {
                    List<Generator> toRemove = new ArrayList<>();

                    for (Generator generator : blockGenerator.getDataController().getGenerators())
                    {
                        if (generator.getMaterial() == material)
                        {
                            toRemove.add(generator);
                        }
                    }

                    blockGenerator.getDataController().getGenerators().removeAll(toRemove);

                    blockGenerator.getDataController().getGeneratorPatterns().remove(material);
                    sender.sendMessage(blockGenerator.getBlockGeneratorConfig().getGeneratorRemoved());
                }
                else
                {
                    sender.sendMessage(blockGenerator.getBlockGeneratorConfig().getGeneratorNotExist());
                }
            }
            else if (args[0].equalsIgnoreCase("get"))
            {
                if (blockGenerator.getDataController().getGeneratorPatterns().containsKey(material))
                {
                    if (sender instanceof Player)
                    {
                        Player player = (Player) sender;

                        if (player.getInventory().contains(Material.AIR))
                        {
                            player.getInventory().addItem(blockGenerator.getDataController().getGeneratorPatternItem(material));
                        }
                        else
                        {
                            player.getWorld().dropItemNaturally(player.getLocation(),blockGenerator.getDataController().getGeneratorPatternItem(material));
                        }
                        sender.sendMessage(blockGenerator.getBlockGeneratorConfig().getYouGotGenerator());

                    }
                    else
                    {
                        sender.sendMessage("Command only for player");
                    }
                }
                else
                {
                    sender.sendMessage(blockGenerator.getBlockGeneratorConfig().getGeneratorNotExist());
                }
            }
            else
            {
                sender.sendMessage(blockGenerator.getBlockGeneratorConfig().getWrongUsage().replace("<ADD/REMOVE/GET>",ChatColor.RED+"<ADD/REMOVE/GET>"+ChatColor.GREEN));
                return true;
            }

        }
        else
        {
            sender.sendMessage(blockGenerator.getBlockGeneratorConfig().getWrongUsage());
        }
        return true;
    }
}
