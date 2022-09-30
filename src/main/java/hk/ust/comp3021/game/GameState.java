package hk.ust.comp3021.game;

import hk.ust.comp3021.entities.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The state of the Sokoban Game.
 * Each game state represents an ongoing game.
 * As the game goes, the game state changes while players are moving while the original game map stays the unmodified.
 * <b>The game state should not modify the original game map.</b>
 * <p>
 * GameState consists of things changing as the game goes, such as:
 * <li>Current locations of all crates.</li>
 * <li>A move history.</li>
 * <li>Current location of player.</li>
 * <li>Undo quota left.</li>
 */
public class GameState {

    public int Width;
    public int Height;
    public String[] LastStepMap;
    public String[] CheckpointMap;
    public String[] InitialMap;
    public String[] MapString;
    public Set<Position> Dest;
    public Set<Position> BoxDest;
    public ArrayList<Integer> Players;
    public Set<Position> PlayerPosition;
    public ArrayList<Position> Wall;
    public int UndoLimit;
    public int Undo;
    public ArrayList<Position> HistoryMovements;
    public boolean HasCheckPoint;
    /**
     * Create a running game state from a game map.
     *
     * @param map the game map from which to create this game state.
     */
    public GameState(@NotNull GameMap map) {
        // TODO
//        curPosition = map._initialBoxDest.get(map._initialBoxDest.size()-1);
        Height = map._height;
        Width = map._width;
        HistoryMovements = new ArrayList<>();
        Players = new ArrayList<>();
        BoxDest = new HashSet<>();
        InitialMap = map._mapStringNonStatic.clone();
        UndoLimit = map._undoLimit;
        MapString = map._mapStringNonStatic.clone();
        Players = map._playersNonStatic;
        Wall = map._wallNonStatic;
        Undo =0;
        BoxDest = map._initialBoxDest;
//        throw new NotImplementedException();
    }

    /**
     * Get the current position of the player with the given id.
     *
     * @param id player id.
     * @return the current position of the player.
     */
    public @Nullable Position getPlayerPositionById(int id) {
        // TODO
        for(int i = 1; i< MapString.length; i++){
            for(int j = 0; j< MapString[i].length(); j++){
                if(MapString[i].charAt(j)-'A' == id){
                    return new Position(j,i-1);
                }
            }
        }

        throw new IllegalArgumentException("There is no player with the given Id");
    }

    /**
     * Get current positions of all players in the game map.
     *
     * @return a set of positions of all players.
     */
    public @NotNull Set<Position> getAllPlayerPositions() {
        // TODO
        PlayerPosition = new HashSet<>();
        for(int i = 1; i< MapString.length; i++){
            for(int j = 0; j< MapString[i].length(); j++){
                if(MapString[i].charAt(j) >='A'&& MapString[i].charAt(j) <='Z'){
                    PlayerPosition.add(new Position(j,i-1));
                }
            }
        }
        return PlayerPosition;
//        throw new NotImplementedException();
    }

    /**
     * Get the entity that is currently at the given position.
     *
     * @param position the position of the entity.
     * @return the entity object.
     */
    public @Nullable Entity getEntity(@NotNull Position position) {
        // TODO
        int x = position.x();
        int y = position.y()+1;
        if(x>= Width || y>= Height)return null;
//        System.out.print(_mapString[y].length()+'\n');
        char _char = MapString[y].charAt(x);
//        System.out.print(_char+"\n");
        if (_char>= 'A' && _char<='Z' ){
            return new Player(_char -'A');
        }else if(_char>='a' && _char <='z'){
            return new Box(Character.toLowerCase(_char)-'A');
        } else if(_char =='.' || _char =='@'){
            return new Empty();
        }else if(_char == '#'){
            return new Wall();
        }
        else return null;
//        throw new NotImplementedException();
    }

    /**
     * Get all box destination positions as a set in the game map.
     * This should be the same as that in {@link GameMap} class.
     *
     * @return a set of positions.
     */
    public @NotNull @Unmodifiable Set<Position> getDestinations() {
        // TODO
        return Dest;
//        throw new NotImplementedException();
    }

    public Set<Position> getBoxPlaces(){
        BoxDest = new HashSet<>();
        for(int i = 1; i< MapString.length; i++){
            for(int j = 0; j< MapString[i].length(); j++){
                if(MapString[i].charAt(j) >='a' && MapString[i].charAt(j) <='z'){
                    BoxDest.add(new Position(j,i-1));
                }
            }
        }
        return BoxDest;
    }

    /**
     * Get the undo quota currently left, i.e., the maximum number of undo actions that can be performed from now on.
     * If undo is unlimited,
     *
     * @return the undo quota left (using {@link Optional#of(Object)}) if the game has an undo limit;
     * {@link Optional#empty()} if the game has unlimited undo.
     */
    public Optional<Integer> getUndoQuota() {
        // TODO
        int remain = UndoLimit - Undo;
        return Optional.of(remain);
//        throw new NotImplementedException();
    }

    /**
     * Check whether the game wins or not.
     * The game wins only when all box destinations have been occupied by boxes.
     *
     * @return true is the game wins.
     */
    public boolean isWin() {
// TODO
        for(Position pos : getBoxPlaces()){
            int x = pos.x();
            int y = pos.y()+1;
            if(!(InitialMap[y].charAt(x)=='@')){
                return false;
            }
        }

        return true;
//        throw new NotImplementedException();
    }

    /**
     * Move the entity from one position to another.
     * This method assumes the validity of this move is ensured.
     * <b>The validity of the move of the entity in one position to another need not to check.</b>
     *
     * @param from The current position of the entity to move.
     * @param to   The position to move the entity to.
     */
    public void move(Position from, Position to) {
        // TODO
        int x_from = from.x();
        int y_from = from.y()+1;
        int x_to = to.x();
        int y_to = to.y()+1;
        char charFrom = MapString[y_from].charAt(x_from);
        char charTo = MapString[y_to].charAt(x_to);
//        _historyMovements.add(from);
//        _historyMovements.add(to);
        if(charFrom>='a' && charFrom<='z'){
            LastStepMap = MapString.clone();
        }
        if(charTo =='@') charTo = '.';
        MapString[y_from] = MapString[y_from].substring(0,x_from)+charTo+ MapString[y_from].substring(x_from+1);
        MapString[y_to] = MapString[y_to].substring(0,x_to)+charFrom+ MapString[y_to].substring(x_to+1);
//        throw new NotImplementedException();
    }

    /**
     * Record a checkpoint of the game state, including:
     * <li>All current positions of entities in the game map.</li>
     * <li>Current undo quota</li>
     * <p>
     * Checkpoint is used in {@link GameState#undo()}.
     * Every undo actions reverts the game state to the last checkpoint.
     */
    public void checkpoint() {
        // TODO
        HasCheckPoint = true;
        CheckpointMap = LastStepMap;
//        System.out.print( "_lastStepMap2" + _lastStepMap[2].toString()+'\n');
//        Position from = _historyMovements.get(_historyMovements.size()-2);
//        Position to = _historyMovements.get(_historyMovements.size()-1);
//        int x_from = from.x();
//        int y_from = from.y()+1;
//        int x_to = to.x();
//        int y_to = to.y()+1;
//        char charFrom = _checkpointMap[y_from].charAt(x_from);
//        char charTo = _checkpointMap[y_to].charAt(x_to);
//        _checkpointMap[y_from] = _checkpointMap[y_from].substring(0,x_from)+charTo+ _checkpointMap[y_from].substring(x_from+1);
//        _checkpointMap[y_to] = _checkpointMap[y_to].substring(0,x_to)+charFrom+_checkpointMap[y_to].substring(x_to+1);
//        _undo++;
//        _historyMovements.remove(_historyMovements.size()-2);
//        _historyMovements.remove(_historyMovements.size()-1);

//        throw new NotImplementedException();
    }

    /**
     * Revert the game state to the last checkpoint in history.
     * This method assumes there is still undo quota left, and decreases the undo quota by one.
     * <p>
     * If there is no checkpoint recorded, i.e., before moving any box when the game starts,
     * revert to the initial game state.
     */
    public void undo() {
        // TODO
        if(HasCheckPoint){
//            System.out.print("has check point");
//            System.out.print("_mapString"+ _mapString[2].toString());
//            System.out.print("_checkpointMap"+ _checkpointMap[2].toString());
            MapString = CheckpointMap.clone();
            Undo++;
            HasCheckPoint = false;
        }else{
            MapString = InitialMap;
            Undo =0;
        }

//        throw new NotImplementedException();
    }

    /**
     * Get the maximum width of the game map.
     * This should be the same as that in {@link GameMap} class.
     *
     * @return maximum width.
     */
    public int getMapMaxWidth() {
        // TODO
        return Width;
//        throw new NotImplementedException();
    }

    /**
     * Get the maximum height of the game map.
     * This should be the same as that in {@link GameMap} class.
     *
     * @return maximum height.
     */
    public int getMapMaxHeight() {
        // TODO
        return Height;
//        throw new NotImplementedException();
    }
}
