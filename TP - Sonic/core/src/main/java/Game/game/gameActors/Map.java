package Game.game.gameActors;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Map extends Actor {

	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer mapRenderer;
	private static final int ppm = 4;
	OrthographicCamera camera;
	//private float mapWidth, mapHeight;

	public Map(OrthographicCamera camera) {
		super();
		
		this.camera = camera;
		tiledMap = new TmxMapLoader().load("Mapa.tmx");

		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		mapRenderer.setView(camera);
		mapRenderer.render();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	public static int getPpm() {
		return ppm;
	}

}
