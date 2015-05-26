package me.collidedmoon.InvSwap;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SwapUtils {
	
	public static void Swap(){
		int playersToSwap = (Bukkit.getServer().getOnlinePlayers().size())/(10/100);
		broadcast("Swap !");
		for(int i = 1; i<= playersToSwap; i++){
			Player pl = getRandomPlayer();
			InvSwapMain.isPlayerSelected.put(pl, true);
			Player pl2 = nearestPlayer(pl);
			InvSwapMain.isPlayerSelected.put(pl2, true);
		
			swapInvs(pl, pl2);
		
		
			pl.playSound(pl.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 5);
			pl2.playSound(pl.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 5);
		
			sendMessage(pl, "Your inventory got swapped with the nearest player !");
			sendMessage(pl2, "Your inventory got swapped with the nearest player !");
			pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5, 5), true);
			pl2.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5, 5), true);
		}
		broadcast("Next swap in 5 to 10 minutes !");
		
		resetVariables();

	}
	
	public static void resetVariables(){
		InvSwapMain.timeUntilSwap = randomizeTime();
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			InvSwapMain.isPlayerSelected.put(p, false);
		}
	}
	
	private static Player getRandomPlayer(){
		ArrayList<Player> players = new ArrayList<Player>();
		for(Player pl : Bukkit.getServer().getOnlinePlayers()){
			if(!InvSwapMain.isPlayerSelected.get(pl))
				players.add(pl);
		}
		Random r = new Random();
		int index = r.nextInt(players.size());
		Player player = players.get(index);
		return player;
	}
	
	public static String prefix(){
		return ChatColor.BLACK +  "[" + ChatColor.DARK_GREEN + "InvSwap" +  ChatColor.BLACK +"]" + ChatColor.GREEN + " ";
	}
	
	public static boolean hasPerm(Player pl,String perm){
		if(pl.hasPermission(perm)){
			return true;
		}else{
			pl.sendMessage(ChatColor.RED + "You don't have permission to use that command.");
			return false;
		}
	}
	
	public static void broadcast(String msg){
		for(Player pl : Bukkit.getServer().getOnlinePlayers()){
			pl.sendMessage(prefix() + msg);
		}
	}
	
	public static void sendMessage(Player pl, String msg){
		pl.sendMessage(prefix() + msg);
	}
	public static void swapInvs(Player pl1, Player pl2){
		
		Inventory pl1Inv = pl1.getInventory();
		Inventory pl2Inv = pl2.getInventory();
		
		ArrayList<ItemStack> inv1 = new ArrayList<ItemStack>();
		ArrayList<ItemStack> inv2 = new ArrayList<ItemStack>();
		
		// INVENTORY ITEMS
		for(int i = 0; i<=35 ; i++){
			ItemStack item = pl1Inv.getItem(i);
			inv1.add(i,item);
			
			ItemStack item2 = pl2Inv.getItem(i);
			inv2.add(i,item2);

		}
		
		for(int i = 0; i<=35; i++){
			pl1Inv.setItem(i, inv2.get(i));
			pl2Inv.setItem(i, inv1.get(i));

		}
		//
		
		//ARMOR
		for(int i = 100; i<=103 ; i++){
			ItemStack item = pl1Inv.getItem(i);
			inv1.add(i,item);
			
			ItemStack item2 = pl2Inv.getItem(i);
			inv2.add(i,item2);

		}
		for(int i = 100; i<=103 ; i++){
			pl1Inv.setItem(i, inv2.get(i));
			pl2Inv.setItem(i, inv1.get(i));

		}
		//
	}
	
	public static int randomizeTime(){
		Random r = new Random();
		int time = r.nextInt(6000) + 6000;
		return time;
	}
	
	public static Player nearestPlayer(Player pl){

		return getRandomPlayer();
	}
	
	
}
