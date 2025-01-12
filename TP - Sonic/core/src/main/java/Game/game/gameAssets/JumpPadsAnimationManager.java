package Game.game.gameAssets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;

public class JumpPadsAnimationManager extends TextureManager {
	private Animation<TextureRegion> jumpPadsAnimation; 
	private float stateTime; 
	
	public JumpPadsAnimationManager() {
		loadTextures();
		stateTime = 0f; 
	}

	@Override
	public void loadTextures() {
		Array<TextureRegion> frames = new Array<>();

		for (int i = 1; i <= 2; i++) {
			String filePath = "springs/springRed" + i + ".png"; 
			Texture texture = new Texture(Gdx.files.internal(filePath));
			textures.put(filePath, texture); 
			frames.add(new TextureRegion(texture)); 
		}

		jumpPadsAnimation = new Animation<>(0.4f, frames, Animation.PlayMode.LOOP);
	}

	public TextureRegion getCurrentFrame(float deltaTime) {
		stateTime += deltaTime; 
		return jumpPadsAnimation.getKeyFrame(stateTime, true); 
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
