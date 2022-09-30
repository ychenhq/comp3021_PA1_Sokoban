package hk.ust.comp3021.game;

import hk.ust.comp3021.actions.Action;
import hk.ust.comp3021.actions.ActionResult;
import hk.ust.comp3021.actions.Exit;
import hk.ust.comp3021.actions.Move;
import hk.ust.comp3021.entities.Box;
import hk.ust.comp3021.entities.Empty;
import hk.ust.comp3021.entities.Wall;
import hk.ust.comp3021.tui.TerminalInputEngine;
import hk.ust.comp3021.utils.NotImplementedException;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

import static hk.ust.comp3021.utils.StringResources.INVALID_INPUT_MESSAGE;
import static hk.ust.comp3021.utils.StringResources.PLAYER_NOT_FOUND;

/**
 * A base implementation of Sokoban Game.
 */
public abstract class AbstractSokobanGame implements SokobanGame {
    @NotNull
    protected final GameState state;

    protected AbstractSokobanGame(@NotNull GameState gameState) {
        this.state = gameState;
    }

    /**
     * @return True is the game should stop running.
     * For example when the user specified to exit the game or the user won the game.
     */
    protected boolean shouldStop() {
        // TODO
        if (state.isWin())return true;
        return false;
//        throw new NotImplementedException();
    }

    /**
     * @param action The action received from the user.
     * @return The result of the action.
     */
    protected ActionResult processAction(@NotNull Action action) {
        // TODO
        Position oldPosition = state.getPlayerPositionById(action.getInitiator());
        if(oldPosition==null)  return new ActionResult.Failed(action, INVALID_INPUT_MESSAGE);
        Position downPosition = new Position(oldPosition.x(), oldPosition.y()+1);
        Position rightPosition = new Position(oldPosition.x()+1, oldPosition.y());
        Position leftPosition = new Position(oldPosition.x()-1, oldPosition.y());
        Position upPosition = new Position(oldPosition.x(), oldPosition.y()-1);

//        System.out.print(action.getClass().getName());
        switch (action.getClass().getName()){
            case "hk.ust.comp3021.actions.Move$Down":
                if (state.getEntity(downPosition).getClass() == Empty.class){
                    state.move(oldPosition,downPosition );
                    return new ActionResult.Success(action);
                } else if (state.getEntity(downPosition).getClass() == Box.class) {
                    Position another = new Position(downPosition.x(), downPosition.y()+1);
                    if(state.getEntity(another).getClass() ==Empty.class){
                        state.move(downPosition,another);
                        state.move(oldPosition,downPosition);
                        state.checkpoint();
                        return new ActionResult.Success(action);
                    }else new ActionResult.Failed(action, "You have met a wall");
                }
                return new ActionResult.Failed(action, "You have met a wall");
            case "hk.ust.comp3021.actions.Move$Right":
                if (state.getEntity(rightPosition).getClass() == Empty.class) {
                    state.move(oldPosition,rightPosition );
                    return new ActionResult.Success(action);
                }else if (state.getEntity(rightPosition).getClass() == Box.class) {
                    Position another = new Position(rightPosition.x()+1, rightPosition.y());
                    if(state.getEntity(another).getClass() ==Empty.class){
                        state.move(rightPosition,another);
                        state.move(oldPosition,rightPosition);
                        state.checkpoint();
                        return new ActionResult.Success(action);
                    }else new ActionResult.Failed(action, "You have met a wall");
                }
                return new ActionResult.Failed(action, "You have met a wall");
            case "hk.ust.comp3021.actions.Move$Left":
                if (state.getEntity(leftPosition).getClass() == Empty.class) {
                    state.move(oldPosition,leftPosition );
                    return new ActionResult.Success(action);
                }else if (state.getEntity(leftPosition).getClass() == Box.class) {
                    Position another = new Position(leftPosition.x()-1, rightPosition.y());
                    if(state.getEntity(another).getClass() ==Empty.class){
                        state.move(leftPosition,another);
                        state.move(oldPosition,leftPosition);
                        state.checkpoint();
                        return new ActionResult.Success(action);
                    }else new ActionResult.Failed(action, "You have met a wall");
                }
                return new ActionResult.Failed(action, "You have met a wall");
            case "hk.ust.comp3021.actions.Move$Up":
                if (state.getEntity(upPosition).getClass() == Empty.class) {
                    state.move(oldPosition,upPosition );
                    return new ActionResult.Success(action);
                }else if (state.getEntity(upPosition).getClass() == Box.class) {
                    Position another = new Position(upPosition.x(), rightPosition.y()-1);
                    if(state.getEntity(another).getClass() ==Empty.class){
                        state.move(upPosition,another);
                        state.move(oldPosition,upPosition);
                        state.checkpoint();
                        return new ActionResult.Success(action);
                    }else new ActionResult.Failed(action, "You have met a wall");
                }
                return new ActionResult.Failed(action, "You have met a wall");
            default:
                return new ActionResult.Failed(action, "You have met a wall");

        }
//        throw new NotImplementedException();
    }
}
