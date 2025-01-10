package Game.game.gameAssets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;

public class BuzzBomberAnimationManager extends TextureManager {
	private Animation<TextureRegion> buzzAnimation; 
	private float stateTime;

	public BuzzBomberAnimationManager() {
		loadTextures(); 
		stateTime = 0f; 
	}

	@Override
	public void loadTextures() {
		
		buzzAnimation = loadAnimation("enemy2/buzzBomber", 4, 0.15f);
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

	public TextureRegion getCurrentFrame(float deltaTime) {
		stateTime += deltaTime; 
		return buzzAnimation.getKeyFrame(stateTime, true); 
	}

	@Override
	public void dispose() {
		super.dispose(); 
	}
}