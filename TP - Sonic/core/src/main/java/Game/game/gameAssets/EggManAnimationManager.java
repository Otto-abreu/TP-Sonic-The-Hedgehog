package Game.game.gameAssets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;

public class EggManAnimationManager extends TextureManager {
	private Animation<TextureRegion> eggManLeftAnimation;
	private Animation<TextureRegion> eggManRightAnimation;
	private float stateTime; 
	private String currentAction = "movementRight";

	public EggManAnimationManager() {
		loadTextures(); 
		stateTime = 0f; 
	}

	@Override
	public void loadTextures() {
		eggManLeftAnimation = loadAnimation("enemy3/eggMan", 5, 0.2f);
		eggManRightAnimation = loadAnimation("enemy3/eggManLeft", 5, 0.2f);
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
	            return eggManLeftAnimation.getKeyFrame(stateTime, true);
	        case "movementRight":
	            return eggManRightAnimation.getKeyFrame(stateTime, true);
	        default:
	            return eggManLeftAnimation.getKeyFrame(stateTime, true);
	    }
	}

	@Override
	public void dispose() {
		super.dispose(); 
	}
}