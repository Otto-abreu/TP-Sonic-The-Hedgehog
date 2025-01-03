package Game.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import Game.game.gameActors.Sonic;

public class WalkRightCommander extends Commander {

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
