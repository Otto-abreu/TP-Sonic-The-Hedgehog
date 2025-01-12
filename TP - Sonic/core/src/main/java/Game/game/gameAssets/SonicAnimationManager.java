package Game.game.gameAssets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;

public class SonicAnimationManager extends TextureManager {
	private Animation<TextureRegion> basicMotionRight;
	private Animation<TextureRegion> basicMotionLeft;
	private Animation<TextureRegion> jumpingAnimation;
	private float stateTime;
	private String currentAction;

	public SonicAnimationManager() {
		loadTextures();
		stateTime = 0f;
		currentAction = "idle"; 
	}

	@Override
	public void loadTextures() {
		basicMotionRight = loadAnimation("sonicBasicMotion/sonicBasicMotion", 6, 0.2f);
		basicMotionLeft = loadAnimation("sonicBasicMotion/sonicBasicMotionLeft", 6, 0.2f);
		jumpingAnimation = loadAnimation("sonicJumping/sonicJumping", 7, 0.02f);

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
	        case "idle":
	            return basicMotionRight.getKeyFrame(0, false);
	        case "walkRight":
	            return basicMotionRight.getKeyFrame(stateTime, true);
	        case "walkLeft":
	            return basicMotionLeft.getKeyFrame(stateTime, false);
	        case "jump":
	            return jumpingAnimation.getKeyFrame(stateTime, false);
	        default:
	            return basicMotionRight.getKeyFrame(0, false);
	    }
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}