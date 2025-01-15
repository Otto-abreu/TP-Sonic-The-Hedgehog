package Game.game.observer;

import Game.game.gameObjects.Sonic;

public interface Subscriber {
	
	public void receiveUpdate(Sonic sonic, boolean chase);
}
