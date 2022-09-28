package hk.ust.comp3021.game;

import hk.ust.comp3021.entities.*;
import hk.ust.comp3021.utils.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import javax.print.attribute.standard.Destination;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A Sokoban game board.
 * GameBoard consists of information loaded from map data, such as
 * <li>Width and height of the game map</li>
 * <li>Walls in the map</li>
 * <li>Box destinations</li>
 * <li>Initial locations of boxes and player</li>
 * <p/>
 * GameBoard is capable to create many GameState instances, each representing an ongoing game.
 */
public class GameMap {
    public int _width;
    public int _height;
    public String[] _mapStringNonStatic;
    public static String[] _mapString;
    public int _undoLimit;
    public Set<Position> _initialBoxDest;
    public static ArrayList<Position> _movableBox;
    public ArrayList<Integer> _playersNonStatic;
    public static ArrayList<Integer> _players;
    public static ArrayList<Position> _playerDest;
    public ArrayList<Position> _wallNonStatic;

    public static ArrayList<Position> _wall;

    /**
     * Create a new GameMap with width, height, set of box destinations and undo limit.
     *
     * @param maxWidth     Width of the game map.
     * @param maxHeight    Height of the game map.
     * @param destinations Set of box destination positions.
     * @param undoLimit    Undo limit.
     *                     Positive numbers specify the maximum number of undo actions.
     *                     0 means undo is not allowed.
     *                     -1 means unlimited. Other negative numbers are not allowed.
     */
    public GameMap(int maxWidth, int maxHeight, Set<Position> destinations, int undoLimit) {
        // TODO
        _width = maxWidth;
        _height = maxHeight;
        _initialBoxDest = destinations;
        _undoLimit = undoLimit;
        _mapStringNonStatic = _mapString;
        _playersNonStatic = _players;
        _wallNonStatic = _wall;
//        throw new NotImplementedException();
    }

    /**
     * Parses the map from a string representation.
     * The first line is undo limit.
     * Starting from the second line, the game map is represented as follows,
     * <li># represents a {@link Wall}</li>
     * <li>@ represents a box destination.</li>
     * <li>Any upper-case letter represents a {@link Player}.</li>
     * <li>
     * Any lower-case letter represents a {@link Box} that is only movable by the player with the corresponding upper-case letter.
     * For instance, box "a" can only be moved by player "A" and not movable by player "B".
     * </li>
     * <li>. represents an {@link Empty} position in the map, meaning there is no player or box currently at this position.</li>
     * <p>
     * Notes:
     * <li>
     * There can be at most 26 players.
     * All implementations of classes in the hk.ust.comp3021.game package should support up to 26 players.
     * </li>
     * <li>
     * For simplicity, we assume the given map is bounded with a closed boundary.
     * There is no need to check this point.
     * </li>
     * <li>
     * Example maps can be found in "src/main/resources".
     * </li>
     *
     * @param mapText The string representation.
     * @return The parsed GameMap object.
     * @throws IllegalArgumentException if undo limit is negative but not -1.
     * @throws IllegalArgumentException if there are multiple same upper-case letters, i.e., one player can only exist at one position.
     * @throws IllegalArgumentException if there are no players in the map.
     * @throws IllegalArgumentException if the number of boxes is not equal to the number of box destinations.
     * @throws IllegalArgumentException if there are boxes whose {@link Box#getPlayerId()} do not match any player on the game board,
     *                                  or if there are players that have no corresponding boxes.
     */
    public static GameMap parse(String mapText) {
        // TODO
        _mapString = mapText.split("\n");
        if(Integer.parseInt(_mapString[0]) <-1) throw new IllegalArgumentException("It should not be a negative value");
        Set<Position> boxDest = new HashSet<>();
        _players = new ArrayList<>();
        _playerDest = new ArrayList<>();
        _movableBox = new ArrayList<>();
        _wall = new ArrayList<>();
        for(int i =1; i<_mapString.length; i++){
            _wall.add(new Position(i,0));
        }
        for(int i =1;i<_mapString.length;i++){
            for(int j =0; j<_mapString[i].length();j++){
                if(_mapString[i].charAt(j) == '@'){
//                    System.out.print("Added a destination");
                    boxDest.add(new Position(j,i-1));
                }
                else if(_mapString[i].charAt(j)>= 'A' && _mapString[i].charAt(j) <= 'Z'){
                    if(_players.contains(_mapString[i].charAt(j)-'A')){
                        throw new IllegalArgumentException("There should not be duplicate players");
                    }
                    else{
                        _players.add(_mapString[i].charAt(j)-'A');
                        _playerDest.add(new Position(j,i-1));
                    }
                }
                else if(_mapString[i].charAt(j)>= 'a' && _mapString[i].charAt(j) <= 'z'){
                    _movableBox.add(new Position(j,i-1));
                }
                else if(_mapString[i].charAt(j) == '#'){
                    _wall.add(new Position(j,i-1));
                }
            }
        }
        if(_players.size() ==0) throw new IllegalArgumentException("There should be at least 1 player");
        return new GameMap(_mapString[1].length(),_mapString.length-1, boxDest,Integer.parseInt(_mapString[0]));
//        throw new NotImplementedException();
    }

    /**
     * Get the entity object at the given position.
     *
     * @param position the position of the entity in the game map.
     * @return Entity object.
     */
    @Nullable
    public Entity getEntity(Position position) {
        // TODO
        int x = position.x();
        int y = position.y()+1;
        char _char = _mapString[y].charAt(x);
        System.out.print(_char+"\n");
        if (_char>= 'A' && _char<='Z' ){
            return new Player(_char -'A');
        }else if(_char>='a' && _char <='z'){
            return new Box(Character.toUpperCase(_char)-'A');
        } else if (_char =='#') {
            return new Wall();
        }
        else{
            return new Empty();
        }
//        throw new NotImplementedException();
    }

    /**
     * Put one entity at the given position in the game map.
     *
     * @param position the position in the game map to put the entity.
     * @param entity   the entity to put into game map.
     */
    public void putEntity(Position position, Entity entity) {
        // TODO

        throw new NotImplementedException();
    }

    /**
     * Get all box destination positions as a set in the game map.
     *
     * @return a set of positions.
     */
    public @NotNull @Unmodifiable Set<Position> getDestinations() {
        // TODO
        System.out.print(_initialBoxDest.toString());
        return _initialBoxDest;
//        throw new NotImplementedException();
    }

    /**
     * Get the undo limit of the game map.
     *
     * @return undo limit.
     */
    public Optional<Integer> getUndoLimit() {
        // TODO
        return Optional.of(_undoLimit);
//        throw new NotImplementedException();
    }

    /**
     * Get all players' id as a set.
     *
     * @return a set of player id.
     */
    public Set<Integer> getPlayerIds() {
        // TODO
        return new HashSet<>(_players);
//        throw new NotImplementedException();
    }

    /**
     * Get the maximum width of the game map.
     *
     * @return maximum width.
     */
    public int getMaxWidth() {
        // TODO
        return _width;
//        throw new NotImplementedException();
    }

    /**
     * Get the maximum height of the game map.
     *
     * @return maximum height.
     */
    public int getMaxHeight() {
        // TODO
        return _height;
//        throw new NotImplementedException();
    }
}
