package Game.game.commander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import Game.game.gameObjects.Sonic;

public class InputHandler {

	private JumpCommander jumpCommander;
	private WalkLeftCommander walkLeftCommander;
	private WalkRightCommander walkRightCommander;

	public InputHandler() {
		this.jumpCommander = new JumpCommander();
		this.walkLeftCommander = new WalkLeftCommander();
		this.walkRightCommander = new WalkRightCommander();
	}
	
	public void handleYAxisInput(Sonic sonic) {
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			jumpCommander.executar(sonic);
		}
	}
	
	public boolean handleXAxisInput(Sonic sonic) {
		boolean sonicIsMovingInX = false;
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			walkLeftCommander.executar(sonic);
			sonicIsMovingInX = true;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			walkRightCommander.executar(sonic);
			sonicIsMovingInX = true;
		}
		return sonicIsMovingInX;
	}

}
