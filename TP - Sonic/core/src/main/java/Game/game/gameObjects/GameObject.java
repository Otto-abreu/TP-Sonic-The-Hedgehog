package Game.game.gameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class GameObject extends Actor{
	
	protected Sprite image;
	protected Rectangle bounds;
	protected float elapsedTime;
	
	public GameObject() {
		// TODO Auto-generated constructor stub
	}
	
	

}
