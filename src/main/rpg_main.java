package main;

import main.Char.Klassen.Archer.Archerevents;
import main.Char.charcommands.*;
import main.Dungeon.Dungeon;
import main.Dungeon.DungeonArena;
import main.Minigame.Minigame;
import main.Money.Moneyview;
import main.MySQL.FileManager;
import main.MySQL.MySQL;
import main.Quest.QuestSystem;
import main.text.info.Commands;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Fabian on 19.07.2017.
 */
public class rpg_main extends JavaPlugin {

    public void onEnable() {
        //Commands
        /*this.getCommand("Createnewchar").setExecutor(new Createnewchar());
        this.getCommand("Loadcharacter").setExecutor(new Loadcharacter());
        this.getCommand("Logoutcharacter").setExecutor(new Logoutcharacter());
        this.getCommand("deletecharacter").setExecutor(new Deletechar());
        this.getCommand("listcharacter").setExecutor(new Listchars());
        this.getCommand("Moneyview").setExecutor(new Moneyview());
        this.getCommand("saveinv").setExecutor(new invtest());
        this.getCommand("loadinv").setExecutor(new invtest());

        this.getCommand("commands").setExecutor(new Commands());*/

        //MySQL
        FileManager.setStandardMySQL();
        FileManager.readMySQL();
        MySQL.connect();
        MySQL.createTable();

        //MinigameSystem
        this.getCommand("minigame").setExecutor(new Minigame(this));

        //----------DungeonSystem
        this.getCommand("dungeon").setExecutor(new Dungeon(this));

        ResultSet rs = MySQL.getResultSet("SELECT * FROM Dungeons");
        try {
            while (rs.next()) {
                String[] loc = rs.getString(5).split(",");
                Dungeon.dungeonArenas.add(new DungeonArena(rs.getString(1), new Location(Bukkit.getServer().getWorld("world"), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2])), this, Integer.parseInt(rs.getString(2))));
            }
            int currenDungeonId=0;
            for(DungeonArena a: Dungeon.dungeonArenas) {
                Bukkit.broadcastMessage(a.name);
                if(a.dungeonid>currenDungeonId) {
                    currenDungeonId=a.dungeonid;
                }
            }
            currenDungeonId++;
            Dungeon.dungeonid=currenDungeonId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //----------

        //QuestSystem
        QuestSystem questSystem = new QuestSystem(this);
        this.getCommand("createquest").setExecutor(questSystem);
        this.getCommand("adddescription").setExecutor(questSystem);
        this.getCommand("printquest").setExecutor(questSystem);
        this.getCommand("addmissiontarget").setExecutor(questSystem);
        this.getCommand("editmissiontarget").setExecutor(questSystem);
        this.getCommand("deletemissiontarget").setExecutor(questSystem);
        this.getCommand("editreward").setExecutor(questSystem);
        this.getCommand("spawnquestnpc").setExecutor(questSystem);
        this.getCommand("bindquest").setExecutor(questSystem);


        Bukkit.getPluginManager().registerEvents(new PlayerEvents(), this);
        Bukkit.getPluginManager().registerEvents(new Archerevents(), this);

        Bukkit.getWorld("world").setGameRuleValue("keepInventory", "true");

        System.out.println("Rpg enabled");
    }

    public void onDisable() {
        System.out.println("Rpg disabled");
        MySQL.close();
    }

}
