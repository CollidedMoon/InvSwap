package me.collidedmoon.InvSwap;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class InvSwapMain extends JavaPlugin {

	public static int timeUntilSwap = 999999;
	public static boolean  isSwapOn = false;
	public static HashMap<Player, Boolean> isPlayerSelected = new HashMap<Player, Boolean>();
	
	@Override
	public void onEnable(){
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	if(isSwapOn){
            		if(timeUntilSwap == 999999){
            			timeUntilSwap = SwapUtils.randomizeTime();
            		}else{
            			timeUntilSwap--;
            		}
            		if(timeUntilSwap <=0){
            			SwapUtils.Swap();
            		}
            	}
            }
        }, 0L, 1L);		
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player player = (Player) sender;
		if (command.getName().equalsIgnoreCase("invswap") && sender instanceof Player) {
			int length = args.length;
			if(length == 1){
				if(args[0].equals("on") && SwapUtils.hasPerm((Player) sender, "invswap.on")){
					SwapUtils.broadcast("InvSwap is now on!");
					isSwapOn = true;
					SwapUtils.resetVariables();
					
				} else if(args[0].equals("off") && SwapUtils.hasPerm((Player) sender, "invswap.off")){
					SwapUtils.broadcast("InvSwap is now off!");
					isSwapOn = false;
					SwapUtils.resetVariables();
					
				}else if(args[0].equals("swap") && SwapUtils.hasPerm((Player) sender, "invswap.swap")){
					SwapUtils.resetVariables();
					SwapUtils.Swap();
				}
			}else {
				SwapUtils.sendMessage(player, "InvSwap commands:");
				SwapUtils.sendMessage(player, "/invswap on" + ChatColor.GRAY +" enables InvSwap");
				SwapUtils.sendMessage(player, "/invswap off"+ ChatColor.GRAY +" disables InvSwap");
				SwapUtils.sendMessage(player, "/invswap swap"+ ChatColor.GRAY +" swaps inventories");
			}
			return true;
		}		
		return false;

	}
}
