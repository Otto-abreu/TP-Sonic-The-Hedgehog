package Game.game.gameAssets;

import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;

public abstract class TextureManager {

	protected HashMap<String, Texture> textures;

	public TextureManager() {
		textures = new HashMap<>();
	}

	public abstract void loadTextures();

	public Texture getTexture(String name) {
		return textures.get(name);
	}

	public void dispose() {
		for (Texture texture : textures.values()) {
			texture.dispose();
		}
		textures.clear();
	}
}