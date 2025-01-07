package Game.game.gameActors;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Map extends GameObject {

	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer mapRenderer;
	private static final float ppm = 2;
	private OrthographicCamera camera;
	private static Map instance;
	private static final float gravity = (float) 0.1;

	//private float mapWidth, mapHeight;
	
	public static Map getInstance(OrthographicCamera camera) {
		if(instance == null) {
			instance = new Map(camera);
		}
		return instance;
	}
	private Map(OrthographicCamera camera) {
		super();
		
		this.camera = camera;
		tiledMap = new TmxMapLoader().load("Mapa.tmx");

		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/ppm);
		
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
	
	public static float getPpm() {
		return ppm;
	}
	
	public static float getGravity() {
		return gravity;
	}
	public TiledMap getMap() {
		return tiledMap;
	}

}
