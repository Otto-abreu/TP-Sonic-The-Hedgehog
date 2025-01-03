package Game.game.commander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import Game.game.gameActors.Sonic;

public class JumpCommander extends Commander {

	public JumpCommander() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executar(Sonic sonic) {
		moveY(sonic);
	}

	public void moveY(Sonic sonic) {
		if (sonic.getElapsedTime() - sonic.getLastJumpTime() >= 5 || sonic.isFirstJump() == false) {
			sonic.setFirstJump(true);

			jump(sonic);
		}
		//sonic.setY(sonic.getY() + sonic.getSpeedY());
	}

	public void jump(Sonic sonic) {
		sonic.setLastJumpTime(sonic.getElapsedTime());
		sonic.setSpeedY(sonic.getSpeedY() + sonic.getFinalSpeedY());
		System.out.println("speed:" + sonic.getSpeedY());
	}

}
