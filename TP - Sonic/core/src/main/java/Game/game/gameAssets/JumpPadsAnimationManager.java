package Game.game.gameAssets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;

public class JumpPadsAnimationManager extends TextureManager {
	private TextureRegion idleFrame; // Frame parado 
	private TextureRegion activatedFrame; // Frame ativado (interação com o personagem)
	private boolean isActivated; // Estado da mola
	private float activationDuration; 
	private float activationTimer; 

	public JumpPadsAnimationManager() {
		loadTextures();
		isActivated = false; 
		activationDuration = 0.2f; 
		activationTimer = 0f;
	}

	@Override
	public void loadTextures() {
		
		String idleFilePath = "springs/springRed1.png";
		String activatedFilePath = "springs/springRed2.png";

		Texture idleTexture = new Texture(Gdx.files.internal(idleFilePath));
		Texture activatedTexture = new Texture(Gdx.files.internal(activatedFilePath));

		textures.put(idleFilePath, idleTexture);
		textures.put(activatedFilePath, activatedTexture);

		idleFrame = new TextureRegion(idleTexture);
		activatedFrame = new TextureRegion(activatedTexture);
	}

	public void activate() {
		isActivated = true;
		activationTimer = 0f; 
	}

	public TextureRegion getCurrentFrame(float deltaTime) {
		/*if (isActivated) {
			activationTimer += deltaTime;
			if (activationTimer > activationDuration) {
				isActivated = false; 
			}
			return activatedFrame; 
		}*/
		return idleFrame; 
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
