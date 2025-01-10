package Game.game.gameAssets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;

public class EggManAnimationManager extends TextureManager {
	private Animation<TextureRegion> eggManAnimation; 
	private float stateTime; 

	public EggManAnimationManager() {
		loadTextures(); 
		stateTime = 0f; 
	}

	@Override
	public void loadTextures() {
		eggManAnimation = loadAnimation("enemy3/eggMan", 5, 0.2f);
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
		return eggManAnimation.getKeyFrame(stateTime, true); 
	}

	@Override
	public void dispose() {
		super.dispose(); 
	}
}