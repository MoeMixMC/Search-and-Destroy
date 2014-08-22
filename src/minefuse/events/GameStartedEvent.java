package minefuse.events;

import minefuse.Arena;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStartedEvent extends Event{

	public Arena arena;
	public GameStartedEvent(Arena a){
		arena = a;
	}
	
	public Arena getArena(){
		return arena;
	}
	
	
	private static final HandlerList handlers = new HandlerList();
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
