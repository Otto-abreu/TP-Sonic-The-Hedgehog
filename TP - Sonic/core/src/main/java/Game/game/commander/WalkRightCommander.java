package Game.game.commander;

import Game.game.gameObjects.Sonic;

public class WalkRightCommander implements Commander {

	public WalkRightCommander() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executar(Sonic sonic) {
		moveRight(sonic);
	}

	public void moveRight(Sonic sonic) {
		if (sonic.getSpeedX() < sonic.getFinalSpeedX()) {
			sonic.setSpeedX((float) (sonic.getSpeedX() + sonic.getAcelerationX()));
		}

	}

}