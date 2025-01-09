package Game.game.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Eggman extends Enemy{

	public Eggman() {

		image = new Sprite(new Texture(Gdx.files.internal("ovo.jpg")));
		image.setSize(128, 128);
		setPosition(00, 1000);
		setWidth(64);
		setHeight(64);
	}
	

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(image, this.getX(), this.getY(), image.getWidth(), image.getHeight());
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}

}
