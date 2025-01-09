package Game.game.commander;

import Game.game.gameObjects.Sonic;

public class JumpCommander extends Commander {

	public JumpCommander() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executar(Sonic sonic) {
		jump(sonic);
	}

	public void jump(Sonic sonic) {
		if (sonic.isJumpEnabled()) {

			sonic.setSpeedY(sonic.getSpeedY() + sonic.getFinalSpeedY());
		}
	}

}
