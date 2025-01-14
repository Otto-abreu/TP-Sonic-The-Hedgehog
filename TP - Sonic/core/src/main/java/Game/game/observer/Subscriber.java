package Game.game.observer;

public interface Subscriber {
	
	public void receiveUpdate(float targetPos, boolean chase);
}
