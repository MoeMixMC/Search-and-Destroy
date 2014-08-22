package minefuse.events;

import minefuse.Arena;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WinEvent extends Event{
	public Arena arena;
	public String type;
	public WinEvent(Arena a, String winType){
		this.arena = a;
		this.type = winType;
	}
	
	public Arena getArena(){
		return arena;
	}
	
	public String getWinType(){
		return type;
	}
	
	
	
	private static final HandlerList handlers = new HandlerList();
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
