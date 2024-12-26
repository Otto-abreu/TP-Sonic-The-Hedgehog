package Game.game.gameActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Sonic extends GameObject {

	private float speed = 0;
	private final float aceleration = (float) 0.2;
	private final float deceleration = (float) 0.1;
	private float elapsedTime;

	public Sonic() {
		imagem = new Texture(Gdx.files.internal("sonic.png"));
		setPosition(320, 240);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(imagem, this.getX(), this.getY(), imagem.getWidth(), imagem.getHeight());
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		move();
		elapsedTime += Gdx.graphics.getDeltaTime();
	}

	public void dispose() {
		imagem.dispose();
	}

	public void move() {
		
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (speed < 10) {
				speed = (float) (speed + aceleration);
			}
		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (speed > -10) {
				speed = (float) (speed - aceleration);
			}
		} else {
			if (speed < 0) {
				speed = (float) (speed + deceleration);
				
				if (speed > 0) {
					speed = 0;
				}
			}
			if (speed > 0) {
				speed = (float) (speed - deceleration);;
				
				if (speed < 0) {
					speed = 0;
				}
			}
		}
		setX(getX() + speed);
		System.out.println(getX());
	}
}
