package ml.pkom.clearvehicle;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;

public class SpawnEvent implements Listener {
    public static Logger logger;

    @EventHandler
    public void onSpawn(VehicleCreateEvent e){
        if (Plugin.clearIsTrue == false){
            return;
        }
        Entity entity = e.getVehicle();
        ArrayList<Entity> loopVehicles = new ArrayList<>();
        if (Plugin.targetEntities.contains(entity.getType())){
            for (Entity loopEntity: entity.getWorld().getEntities()) {
                if (Plugin.targetEntities.contains(loopEntity.getType())) {
                    loopVehicles.add(loopEntity);
                }
            }
        }else{
            return;
        }
        
        if (loopVehicles.size() >= Plugin.entitiesMax){
            for (Entity loopEntity: loopVehicles) {
                if (Plugin.targetEntities.contains(loopEntity.getType())) {
                    loopEntity.remove();
                }
            }
            if (Plugin.clearMsg == true){
                Bukkit.broadcastMessage("§7[" + Plugin.MOD_NAME + "] トロッコ類のエンティティをすべて削除しました。");
            }
            return;
        }
        return;
    }
}
