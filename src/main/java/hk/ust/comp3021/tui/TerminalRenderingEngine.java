package hk.ust.comp3021.tui;

import hk.ust.comp3021.entities.Box;
import hk.ust.comp3021.entities.Empty;
import hk.ust.comp3021.entities.Player;
import hk.ust.comp3021.entities.Wall;
import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.game.Position;
import hk.ust.comp3021.game.RenderingEngine;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;

/**
 * A rendering engine that prints to the terminal.
 */
public class TerminalRenderingEngine implements RenderingEngine {

    private final PrintStream outputSteam;

    /**
     * @param outputSteam The {@link PrintStream} to write the output to.
     */
    public TerminalRenderingEngine(PrintStream outputSteam) {
        this.outputSteam = outputSteam;
    }

    @Override
    public void render(@NotNull GameState state) {
        final var builder = new StringBuilder();
//        builder.append(state._undoLimit);
//        builder.append('\n');
        for (int y = 0; y <= state.getMapMaxHeight(); y++) {
            for (int x = 0; x <= state.getMapMaxWidth(); x++) {
                final var entity = state.getEntity(Position.of(x, y));
                System.out.print(entity);
                final var charToPrint = switch (entity) {
                    // TODO
                    case Wall ignored -> '#';
                    case Box b ->Character.toLowerCase((char)(b.getPlayerId()+65));
                    case Player p -> (char)(p.getId()+65);
                    case Empty ignored -> ' ';
                    case null -> ' ';
                };
                builder.append(charToPrint);
//                System.out.print("builder = " + builder+"\n");

            }
            builder.append('\n');
//            System.out.print("line=" +builder);
        }
        outputSteam.print(builder);
    }

    @Override
    public void message(@NotNull String content) {
        // TODO
        // Hint: System.out is also a PrintStream.
        outputSteam.print(content+System.lineSeparator());
//        throw new NotImplementedException();
    }
}
