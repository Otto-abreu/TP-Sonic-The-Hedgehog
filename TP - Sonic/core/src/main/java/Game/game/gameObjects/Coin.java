package Game.game.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Coin extends GameObject{
	
	private static int quantity = 0;
	private int id;
	
	private int belongingMap;
	
	public Coin(float posX, float posY, int map) {
	
		image = new Sprite(new Texture(Gdx.files.internal("coin.png")));
		image.setSize(64, 64);
		setPosition(posX, posY);
		setWidth(64); setHeight(64);
		belongingMap = map;
		
		this.bounds = image.getBoundingRectangle();
		quantity++;
		id = quantity;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(image, this.getX(), this.getY(), image.getWidth(), image.getHeight());
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public int getBelongingMap() {
		return belongingMap;
	}
	
	public int getId() {
		return id;
	}

}
