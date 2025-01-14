package Game.game.gameAssets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;

public class BuzzBomberAnimationManager extends TextureManager {
	private Animation<TextureRegion> buzzAnimation;
	private Animation<TextureRegion> buzzLeftAnimation;
	private float stateTime;
	private String currentAction = "movementRight";

	public BuzzBomberAnimationManager() {
		loadTextures();
		stateTime = 0f;
	}

	@Override
	public void loadTextures() {
		buzzAnimation = loadAnimation("enemy2/buzzBomber", 4, 0.15f);
		buzzLeftAnimation = loadAnimation("enemy2/buzzBomberLeft", 4, 0.15f);
	}

	private Animation<TextureRegion> loadAnimation(String basePath, int frameCount, float frameDuration) {
		Array<TextureRegion> frames = new Array<>();
		for (int i = 1; i <= frameCount; i++) {
			String filePath = basePath + i + ".png";
			Texture texture = new Texture(Gdx.files.internal(filePath));
			textures.put(filePath, texture);
			frames.add(new TextureRegion(texture));
		}
		return new Animation<>(frameDuration, frames, Animation.PlayMode.LOOP);
	}

	public void setAction(String action) {
		if (!this.currentAction.equals(action)) {
			this.currentAction = action;
			stateTime = 0f;
		}
	}

	public TextureRegion getCurrentFrame(float deltaTime) {
		stateTime += deltaTime; 

	    switch (currentAction) {
	        case "movementRight":
	            return buzzAnimation.getKeyFrame(stateTime, true);
	        case "movementLeft":
	            return buzzLeftAnimation.getKeyFrame(stateTime, true);
	        default:
	            return buzzAnimation.getKeyFrame(stateTime, true);
	    }
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}