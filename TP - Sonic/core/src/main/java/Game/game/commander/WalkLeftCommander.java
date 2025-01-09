package Game.game.commander;
import Game.game.gameObjects.Sonic;

public class WalkLeftCommander extends Commander {

	public WalkLeftCommander() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executar(Sonic sonic) {
		moveLeft(sonic);
	}

	public void moveLeft(Sonic sonic) {

		if (sonic.getSpeedX() > -sonic.getFinalSpeedX()) {
			sonic.setSpeedX((float) (sonic.getSpeedX() - sonic.getAcelerationX()));

		}
	}
}
