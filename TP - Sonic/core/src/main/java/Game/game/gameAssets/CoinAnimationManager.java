package Game.game.gameAssets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;

public class CoinAnimationManager extends TextureManager {
	private Animation<TextureRegion> coinAnimation; 
	private float stateTime; 

	public CoinAnimationManager() {
		loadTextures(); 
		stateTime = 0f; 
	}

	@Override
	public void loadTextures() {
		Array<TextureRegion> frames = new Array<>();

		for (int i = 1; i <= 8; i++) {
			String filePath = "bigRing/bigRing" + i + ".png"; 
			Texture texture = new Texture(Gdx.files.internal(filePath));
			textures.put(filePath, texture); 
			frames.add(new TextureRegion(texture)); 
		}

		coinAnimation = new Animation<>(0.3f, frames, Animation.PlayMode.LOOP);
	}

	public TextureRegion getCurrentFrame(float deltaTime) {
		stateTime += deltaTime; 
		return coinAnimation.getKeyFrame(stateTime, true); 
	}

	@Override
	public void dispose() {
		super.dispose(); 
	}
}