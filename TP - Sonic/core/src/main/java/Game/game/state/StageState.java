package Game.game.state;

import Game.game.gameStage.GameStage;

public interface StageState {


	public void update(GameStage stage);
	public void handleInput(GameStage stage);

}
