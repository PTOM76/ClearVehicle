package ml.pkom.clearvehicle;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    public static final String MOD_ID = "clearvehicle"; // MODID
    public static String MOD_NAME = "ClearVehicle"; // MOD名
    public static String MOD_VER = "1.0.2"; // MODバージョン
    public static final String MOD_AUT = "Pitan"; // MOD開発者

    public static boolean clearIsTrue = true;
    public static boolean clearMsg = true;
    public static int entitiesMax = 15;
    public static String targetEntitiesString = "MINECART,MINECART_CHEST,MINECART_COMMAND,MINECART_FURNACE,MINECART_HOPPER,MINECART_MOB_SPAWNER,MINECART_TNT";
    public static ArrayList<EntityType> targetEntities = new ArrayList<>();
    public static String language = "ja_jp";

    public static FileConfiguration config;
    public static File pluginFolder;
    public static Messages messages = new Messages();

    @Override
    public void onEnable()
    {
        getLogger().info(MOD_NAME + " is enabled!");
        pluginFolder = getDataFolder();
        if (!pluginFolder.exists()) pluginFolder.mkdir();
        config = getConfig();
        checkAndNewConfig();
        saveConfig();
        messages.main();
        //Messages.langConfig = new File(getDataFolder() + "/lang", "ja_jp.json");
        //if (!Messages.langConfig.exists())saveResource(Messages.langConfig.getName(), false);

        getServer().getPluginManager().registerEvents(new SpawnEvent(), this);
    }

    @Override
    public void onDisable()
    {
        getLogger().info("Saving configs...");
        checkAndNewConfig();
        saveConfig();
        getLogger().info("Config is saved!");
        getLogger().info(MOD_NAME + " is disabled!");
    }

    public void checkAndNewConfig(){
        if (config.contains("plugin.MOD_NAME")){MOD_NAME = config.getString("plugin.MOD_NAME");}else{config.set("plugin.MOD_NAME", MOD_NAME);}
        if (config.contains("plugin.MOD_VER")){MOD_VER = config.getString("plugin.MOD_VER");}else{config.set("plugin.MOD_VER", MOD_VER);}
        if (config.contains("language")){language = config.getString("language");}else{config.set("language", language);}
        if (config.contains("clearIsTrue")){clearIsTrue = config.getBoolean("clearIsTrue");}else{config.set("clearIsTrue", clearIsTrue);}
        if (config.contains("clearMsg")){clearMsg = config.getBoolean("clearMsg");}else{config.set("clearMsg", clearMsg);}
        if (config.contains("entitiesMax")){entitiesMax = config.getInt("entitiesMax");}else{config.set("entitiesMax", entitiesMax);}
        if (config.contains("targetEntities")){targetEntitiesString = config.getString("targetEntities");}else{config.set("targetEntities", targetEntitiesString);}
        targetEntities = new ArrayList<>();
        for (String string : targetEntitiesString.split(",")) {
            string = string.replaceAll(" ", "");
            targetEntities.add(EntityType.valueOf(string));
        }
    }

    public void reloadConfigs(){
        reloadConfig();
        config = getConfig();
        checkAndNewConfig();
        messages.main();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args)
    {
        if(cmd.getName().equalsIgnoreCase("clearvehicle")){
            Player player = (Player) sender;
            if (!player.hasPermission("clearvehicle.command")) {
                player.sendMessage(Messages.lang.logo + " " + Messages.lang.error_msg1);
                return true;
            }
            if (args.length == 0){
                player.sendMessage("§rInformation§7: §b" + MOD_NAME + " " + MOD_VER + " §8Made by " + MOD_AUT + "\n§bHelp§7: §d/cv help");
                return true;
            }
            if (args[0].equalsIgnoreCase("help")){
                if (!player.hasPermission("clearvehicle.command.help")) {
                    player.sendMessage(Messages.lang.logo + " " + Messages.lang.error_msg1);
                    return true;
                }
                player.sendMessage(Messages.lang.cmdlist1 + "\n" +
                Messages.lang.cmdlist2 + "\n" +
                Messages.lang.cmdlist3 + "\n" +
                Messages.lang.cmdlist4 + "\n" +
                Messages.lang.cmdlist5 + "\n" +
                Messages.lang.cmdlist6 + "\n" +
                Messages.lang.cmdlist7);
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (!player.hasPermission("clearvehicle.command.reload")) {
                    player.sendMessage(Messages.lang.logo + " " + Messages.lang.error_msg1);
                    return true;
                }
                reloadConfigs();
                player.sendMessage(Messages.lang.logo + " " + Messages.lang.msg1);
                return true;
            }
            if (args[0].equalsIgnoreCase("viewconf")) {
                if (!player.hasPermission("clearvehicle.command.viewconf")) {
                    player.sendMessage(Messages.lang.logo + " " + Messages.lang.error_msg1);
                    return true;
                }
                player.sendMessage(Messages.lang.logo + " " + "§aconfig.yml\n§rplugin:\n  MOD_NAME: " + MOD_NAME + "\n  MOD_VER: " + MOD_VER + "\nclearIsTrue: " + clearIsTrue + "\nclearMsg: "
                 + clearMsg + "\nentitiesMax: " + entitiesMax + "\ntargetEntities: " + targetEntities + "\nlanguage: " + language);
                return true;
            }
            if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("on")) {
                if (!player.hasPermission("clearvehicle.command.switch")) {
                    player.sendMessage(Messages.lang.logo + " " + Messages.lang.error_msg1);
                    return true;
                }
                clearIsTrue = true;
                player.sendMessage(Messages.lang.logo + " " + Messages.lang.msg2);
                config = getConfig();
                config.set("clearIsTrue", clearIsTrue);
                saveConfig();
                return true;
            }
            if (args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("off")) {
                if (!player.hasPermission("clearvehicle.command.switch")) {
                    player.sendMessage(Messages.lang.logo + " " + Messages.lang.error_msg1);
                    return true;
                }
                clearIsTrue = false;
                player.sendMessage(Messages.lang.logo + " " + Messages.lang.msg3);
                config = getConfig();
                config.set("clearIsTrue", clearIsTrue);
                saveConfig();
                return true;
            }
            if (args[0].equalsIgnoreCase("msg")) {
                if (!player.hasPermission("clearvehicle.command.msg")) {
                    player.sendMessage(Messages.lang.logo + " " + Messages.lang.error_msg1);
                    return true;
                }
                if (args.length < 2) {
                    player.sendMessage(Messages.lang.logo + " " + Messages.lang.error_msg5);
                    return true;
                }
                if (args[1].equalsIgnoreCase("true")){
                    clearMsg = true;
                    player.sendMessage(Messages.lang.logo + " " + Messages.lang.msg4);
                    config = getConfig();
                    config.set("clearMsg", clearMsg);
                    saveConfig();
                    return true;
                }else if(args[1].equalsIgnoreCase("false")){
                    clearMsg = false;
                    player.sendMessage(Messages.lang.logo + " " + Messages.lang.msg5);
                    config = getConfig();
                    config.set("clearMsg", clearMsg);
                    saveConfig();
                    return true;
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("amount")){
                if (!player.hasPermission("clearvehicle.command.amount")) {
                    player.sendMessage(Messages.lang.logo + " " + Messages.lang.error_msg1);
                    return true;
                }
                if (args.length < 2){
                    player.sendMessage(Messages.lang.logo + " " + Messages.lang.error_msg2);
                    return true;
                }else{
                    try{
                        entitiesMax = Integer.parseInt(args[1]);
                        player.sendMessage(Messages.lang.logo + " " + Messages.lang.msg6.replaceAll("%entities_max%", "entitiesMax"));
                        config = getConfig();
                        config.set("entitiesMax", entitiesMax);
                        saveConfig();
                    } catch (NumberFormatException e) {
                        player.sendMessage(Messages.lang.logo + " " + Messages.lang.error_msg3);
                    }
                    return true;
                }
            }
            player.sendMessage(Messages.lang.logo + " " + Messages.lang.error_msg4);
            return true;
        }
        return false;
    }

}