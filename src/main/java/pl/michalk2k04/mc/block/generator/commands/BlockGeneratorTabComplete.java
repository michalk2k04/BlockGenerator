package pl.michalk2k04.mc.block.generator.commands;

/*
 *
 *   Created by michalk2k04
 *
 *   01.11.2020 13:40
 *
 */

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import pl.michalk2k04.mc.block.generator.BlockGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockGeneratorTabComplete implements TabCompleter
{
    private final BlockGenerator blockGenerator;

    public BlockGeneratorTabComplete(BlockGenerator blockGenerator)
    {
        this.blockGenerator = blockGenerator;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {

        List<String> list = new ArrayList<>();

        if (args.length == 1)
        {
            list.add("get");
            list.add("add");
            list.add("remove");
        }
        else if (args.length == 2)
        {
            if (args[0].equalsIgnoreCase("get"))
            {
                blockGenerator.getDataController().getGeneratorPatterns().keySet().forEach(x ->
                        list.add(x.toString()));
            }
            else
            {
                Arrays.stream(Material.values()).forEach(x ->
                {
                    if (x.isSolid() && x.isBlock())
                        list.add(x.toString());
                });
            }
        }
        return list;
    }
}
