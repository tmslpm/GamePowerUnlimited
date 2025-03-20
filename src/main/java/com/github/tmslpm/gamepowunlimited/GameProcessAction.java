package com.github.tmslpm.gamepowunlimited;

import com.github.tmslpm.gamepowunlimited.enums.GameState;
import com.github.tmslpm.gamepowunlimited.enums.PieceType;
import com.github.tmslpm.gamepowunlimited.players.Player;
import com.github.tmslpm.gamepowunlimited.players.PlayerIA;
import com.github.tmslpm.gamepowunlimited.utils.GameToImage;
import com.github.tmslpm.gamepowunlimited.utils.HelperFile;
import com.github.tmslpm.gamepowunlimited.utils.HelperJSON;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

        // insert a piece for the visitor
        boolean visitorInsertIsSuccess = game.insertPieces(y, PieceType.YELLOW);
        if (visitorInsertIsSuccess) {
            // check if player win
            final GameState stateAfterVisitorPlay = game.checkGameState();
            // dispatch event
            game.onGameState(stateAfterVisitorPlay);
            // IA play only if is GameState.PLAY, not after TIE, WIN...
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
        Path path = GameProcessAction.createOutPathImage(pathDirOutput);
        GameToImage.generate(game, 64, path);

        // Get readme template
        StringBuilder readmeText = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("gen/template/readme.md", StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                readmeText.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        if (readmeText.length() <= 10)
            throw new RuntimeException("template readme empty/low data");

        // update readme
        writeFile("README.md", readmeText.toString().replaceAll("\\{\\{IMG_PATH}}", path.getFileName().toString()));
        // store image name
        writeFile("gen/game_image_name.txt", path.getFileName().toString());
    }

    public static Path createOutPathImage(String pathDir) {
        return Path.of(pathDir, HelperFile.createUniqueName("game_to_image", 1)[0] + ".png");
    }

    private static void writeFile(String fileName, String data) {
        try (FileWriter writer = new FileWriter(fileName);) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}