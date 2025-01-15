package Game.game.gameAssets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;

public class BuzzBomberAnimationManager extends TextureManager {
	private Animation<TextureRegion> buzzLeftAnimation;
	private Animation<TextureRegion> buzzRightAnimation;
	private float stateTime;
	private String currentAction = "movementRight";

	public BuzzBomberAnimationManager() {
		loadTextures();
		stateTime = 0f;
	}

	@Override
	public void loadTextures() {
		buzzLeftAnimation = loadAnimation("enemy2/buzzBomber", 4, 0.15f);
		buzzRightAnimation = loadAnimation("enemy2/buzzBomberLeft", 4, 0.15f);
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
	        case "movementLeft":
	            return buzzLeftAnimation.getKeyFrame(stateTime, true);
	        case "movementRight":
	            return buzzRightAnimation.getKeyFrame(stateTime, true);
	        default:
	            return buzzLeftAnimation.getKeyFrame(stateTime, true);
	    }
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}