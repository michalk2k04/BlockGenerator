package pl.michalk2k04.mc.block.generator.data;

/*
 *
 *   Created by michalk2k04
 *
 *   01.11.2020 12:29
 *
 */

import org.bukkit.ChatColor;

public class BlockGeneratorConfig
{

    private static final String configFileName = "config.json";

    private final String wrongUsage;

    private final String inThisPlaceGeneratorAlredyIs;

    private final String generatorPlaced;

    private final String generatorCreated;

    private final String generatorRemoved;

    private final String youGotGenerator;

    private final String generatorNotExist;

    @SuppressWarnings("unused")
    public BlockGeneratorConfig(String wrongUsage, String inThisPlaceGeneratorAlredyIs, String generatorPlaced, String generatorCreated, String generatorRemoved, String youGotGenerator, String generatorNotExist)
    {
        this.wrongUsage = ChatColor.translateAlternateColorCodes('&' , wrongUsage);
        this.inThisPlaceGeneratorAlredyIs = ChatColor.translateAlternateColorCodes('&' , inThisPlaceGeneratorAlredyIs);
        this.generatorPlaced = ChatColor.translateAlternateColorCodes('&' , generatorPlaced);
        this.generatorCreated = ChatColor.translateAlternateColorCodes('&' , generatorCreated);
        this.generatorRemoved = ChatColor.translateAlternateColorCodes('&' , generatorRemoved);
        this.youGotGenerator = ChatColor.translateAlternateColorCodes('&' , youGotGenerator);
        this.generatorNotExist = ChatColor.translateAlternateColorCodes('&' , generatorNotExist);
    }

    public BlockGeneratorConfig()
    {
        this.wrongUsage = ChatColor.RED+"Wrong usage ! \n "+ChatColor.GREEN+" /bg <ADD/REMOVE/GET> <MATERIAL> <RESPAWN TIME> <GENERATOR NAME>";
        this.inThisPlaceGeneratorAlredyIs = ChatColor.RED+"The generator is already here !";
        this.generatorPlaced = ChatColor.GREEN + "The generator has been placed";
        this.generatorCreated = ChatColor.GREEN + "Generator created !";
        this.generatorRemoved = ChatColor.RED+"The generator has been removed!";
        this.youGotGenerator =  ChatColor.GREEN+"You got a generator!";
        generatorNotExist = ChatColor.RED+"The generator not exist!";
    }

    public static String getConfigFileName()
    {
        return configFileName;
    }

    public String getWrongUsage()
    {
        return wrongUsage;
    }

    public String getGeneratorCreated()
    {
        return generatorCreated;
    }

    public String getGeneratorPlaced()
    {
        return generatorPlaced;
    }

    public String getInThisPlaceGeneratorAlredyIs()
    {
        return inThisPlaceGeneratorAlredyIs;
    }

    public String getGeneratorRemoved()
    {
        return generatorRemoved;
    }

    public String getYouGotGenerator()
    {
        return youGotGenerator;
    }

    public String getGeneratorNotExist()
    {
        return generatorNotExist;
    }
}
