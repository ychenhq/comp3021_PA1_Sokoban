package hk.ust.comp3021.tui;


//import hk.ust.comp3021.actions.Action;
import hk.ust.comp3021.actions.ActionResult;
//import hk.ust.comp3021.actions.InvalidInput;
import hk.ust.comp3021.game.AbstractSokobanGame;
import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.game.InputEngine;
import hk.ust.comp3021.game.RenderingEngine;
//import hk.ust.comp3021.utils.NotImplementedException;

import static hk.ust.comp3021.utils.StringResources.INVALID_INPUT_MESSAGE;

/**
 * A Sokoban game running in the terminal.
 */
public class TerminalSokobanGame extends AbstractSokobanGame {

    private final InputEngine inputEngine;

    private final RenderingEngine renderingEngine;

    /**
     * Create a new instance of TerminalSokobanGame.
     * Terminal-based game only support at most two players, although the hk.ust.comp3021.game package supports up to 26 players.
     * This is only because it is hard to control too many players in a terminal-based game.
     *
     * @param gameState       The game state.
     * @param inputEngine     the terminal input engin.
     * @param renderingEngine the terminal rendering engine.
     * @throws IllegalArgumentException when there are more than two players in the map.
     */
    public TerminalSokobanGame(GameState gameState, TerminalInputEngine inputEngine, TerminalRenderingEngine renderingEngine) {
        super(gameState);
        this.inputEngine = inputEngine;
        this.renderingEngine = renderingEngine;
        // TODO
        // Check the number of players
        if(gameState.getAllPlayerPositions().size()>2) throw new IllegalArgumentException();
//        throw new NotImplementedException();
    }

    @Override
    public void run() {
        // TODO
        while(!shouldStop()){
            var result = processAction(this.inputEngine.fetchAction());
            if(result.getClass() == ActionResult.Failed.class){
                renderingEngine.render(this.state);
                renderingEngine.message(INVALID_INPUT_MESSAGE);
            }else {
                renderingEngine.render(this.state);
            }
        }
//        throw new NotImplementedException();
    }
}
