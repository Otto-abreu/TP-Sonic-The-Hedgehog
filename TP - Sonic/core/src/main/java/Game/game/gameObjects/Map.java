package Game.game.gameObjects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map extends GameObject {

	private TiledMap map1;
	private TiledMap map2;
	private TiledMap map3;
	private int[] mapSizes = {21697, 36930, 39050};
	private OrthogonalTiledMapRenderer mapRenderer;
	private static final float ppm = 2;
	private OrthographicCamera camera;
	private static Map instance;
	private static final float gravity = (float) 0.1;
	private int mapSelected = 1;

	public static Map getInstance(OrthographicCamera camera) {
		if (instance == null) {
			instance = new Map(camera);
		}
		return instance;
	}

	private Map(OrthographicCamera camera) {
		super();

		this.camera = camera;
		map1 = new TmxMapLoader().load("Mapa.tmx");
		map2 = new TmxMapLoader().load("Mapa2.tmx");
		map3 = new TmxMapLoader().load("Mapa3.tmx");

		mapRenderer = new OrthogonalTiledMapRenderer(map1, 1 / ppm);
		
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

	public void chengeMap(int mapId) {
		TiledMap aux = new TiledMap();
		
		if(mapId == 1) {
			aux = map1;
			mapSelected = 1;
		}else if(mapId == 2) {
			aux = map2;
			mapSelected = 2;
		}else if(mapId == 3) {
			aux = map3;
			mapSelected = 3;
		}
		
		mapRenderer = new OrthogonalTiledMapRenderer(aux, 1/ppm);
	}

	public static float getPpm() {
		return ppm;
	}

	public static float getGravity() {
		return gravity;
	}

	public TiledMap getMap() {
		TiledMap returnValue = null;

		switch (mapSelected) {
		case 1:
			returnValue = map1;
			break;

		case 2:
			returnValue = map2;
			break;

		case 3:
			returnValue = map3;
			break;

		default:
			break;
		}
		return returnValue;
	}

	public int getMapSelected() {
		return mapSelected;
	}
	public int getMapSize(int mapId) {
		return mapSizes[mapId-1];
	}

}
