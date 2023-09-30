package com.github.tmslpm.gamepowunlimited;

import com.github.tmslpm.gamepowunlimited.enums.GameState;
import com.github.tmslpm.gamepowunlimited.enums.PieceType;
import com.github.tmslpm.gamepowunlimited.players.Player;
import com.github.tmslpm.gamepowunlimited.players.PlayerIA;
import com.github.tmslpm.gamepowunlimited.utils.GameToImage;
import com.github.tmslpm.gamepowunlimited.utils.HelperFile;
import com.github.tmslpm.gamepowunlimited.utils.HelperJSON;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * - issue event
 *    |- start action
 *    |- get data in issue
 *    |- get and cast column choice
 *    |- deserialize old game
 *    |- insert piece in column selected
 *    |- update the game
 *    |- generate image with game output
 *    |- serialize the game
 *   ---
 */

public class GameProcessAction extends GamePowerUnlimited {
    private static final String PATH_OUT_JSON = "game_process.save.json";
    private static final int CONFIG_POWER = 4;
    private static final int CONFIG_X_LENGTH = 6;
    private static final int CONFIG_Y_LENGTH = 7;

    public GameProcessAction() {
        super(CONFIG_POWER, CONFIG_X_LENGTH, CONFIG_Y_LENGTH, true);
    }

    public static void start(int y) {
        final GameProcessAction game;

        // Create Game instance from Json or with default config
        if (!Files.exists(Path.of(PATH_OUT_JSON))) {
            // init game if not exist in directory
            game = new GameProcessAction();
            // add 2 players
            game.setPlayers(new Player[] {
                    new Player(PieceType.YELLOW),
                    new PlayerIA(game, PieceType.RED)
            });
        } else {
            game = HelperJSON.fromFile(PATH_OUT_JSON, GameProcessAction.class);
        }

        // insert piece for the visitor
        boolean visitorInsertIsSuccess = game.insertPieces(y, PieceType.YELLOW);
        if (visitorInsertIsSuccess) {
            // check if player win
            final GameState stateAfterVisitorPlay = game.checkGameState();
            // dispatch event
            game.onGameState(stateAfterVisitorPlay);
            // IA play only if is GameState.PLAY, not after TIE,WIN...
            if (GameState.PLAY.equals(stateAfterVisitorPlay)) {
                // insert piece for the IA
                game.insertPieces(PlayerIA.getRandomPosition(game), PieceType.RED);
                // check if player win
                game.checkGameState();
            }
        }

        // Serialize data
        HelperJSON.toFile(PATH_OUT_JSON, game);
        // Generate image
        String pathDirOutput = "gen";
        HelperFile.deleteFiles(Path.of(pathDirOutput), "game_to_image*{.png}");
        GameToImage.generate(game, 64, GameProcessAction.createOutPathImage(pathDirOutput));
    }

    public static Path createOutPathImage(String pathDir) {
        return Path.of(pathDir, HelperFile.createUniqueName("game_to_image", 1)[0] + ".png");
    }
}