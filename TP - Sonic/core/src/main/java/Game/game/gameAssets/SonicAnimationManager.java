package Game.game.gameAssets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;

public class SonicAnimationManager extends TextureManager {
	private Animation<TextureRegion> basicMotionAnimation;
	private Animation<TextureRegion> jumpingAnimation;
	private float stateTime;
	private String currentAction;

	public SonicAnimationManager() {
		loadTextures();
		stateTime = 0f;
		currentAction = "walk"; 
	}

	@Override
	public void loadTextures() {
		
		basicMotionAnimation = loadAnimation("sonicBasicMotion/sonicBasicMotion", 6, 0.2f);
		jumpingAnimation = loadAnimation("sonicJumping/sonicJumping", 5, 0.15f);

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
		this.currentAction = action; 
		stateTime = 0f; 
	}

	public TextureRegion getCurrentFrame(float deltaTime) {
		stateTime += deltaTime;

		switch (currentAction) {
		case "idle":
			return basicMotionAnimation.getKeyFrame(0); //o primeiro asset, sonic parado
		case "walk":
			return basicMotionAnimation.getKeyFrame(stateTime, true); 
		case "jump":
			return jumpingAnimation.getKeyFrame(stateTime, false);
		default:
			return basicMotionAnimation.getKeyFrame(0); //o primeiro asset, sonic parado
		}
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}