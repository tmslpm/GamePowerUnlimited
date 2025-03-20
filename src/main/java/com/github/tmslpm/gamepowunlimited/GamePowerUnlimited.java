package com.github.tmslpm.gamepowunlimited;

import com.github.tmslpm.gamepowunlimited.enums.AnsiColor;
import com.github.tmslpm.gamepowunlimited.enums.Direction;
import com.github.tmslpm.gamepowunlimited.enums.GameState;
import com.github.tmslpm.gamepowunlimited.enums.PieceType;
import com.github.tmslpm.gamepowunlimited.players.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Game Pow Unlimited, feature:
 * <ul>
 *     <li>length of a valid combo can be configured</li>
 *     <li>grid length and height can be configured</li>
 *     <li>can accept an unlimited number of players</li>
 *     <li>-</li>
 * </ul>
 * <h4>example</h4>
 * <pre><code>
 *  GamePowerUnlimited game = new GamePowerUnlimited(2, 2, 2); // LITTLE
 *  GamePowerUnlimited game = new GamePowerUnlimited(4, 6, 7); // NORMAL
 *  GamePowerUnlimited game = new GamePowerUnlimited(8, 12, 14); // GRAND
 *  GamePowerUnlimited game = new GamePowerUnlimited(20, 120, 140); // XXL xD
 * </code></pre>
 *
 * @author tmslpm
 */
public class GamePowerUnlimited {
  /**
   * the length required for a valid combo
   */
  private transient int comboLength;
  /**
   * the round counter for the current party (is reset after each win/tie/lose)
   */
  private int counterRound;
  /**
   * the axe length and barrier width
   */
  private transient int xLength, yLength, xLengthWithBorder, yLengthWithBorder;
  /**
   * the total {@link PieceType} (player) currently added in grid
   */
  private int totalPiecesAdded;
  /**
   * the position X, Y of the last piece added in grid
   */
  private int lastPositionPieceAddedX, lastPositionPieceAddedY;
  /**
   * the grid 2d with all {@link  PieceType} data
   */
  private PieceType[][] grid;

  private Player[] players;
  /**
   * enable/disable register possible combo for IA
   */
  private transient final boolean registerInfoForIA;
  /**
   * ArrayList with all possible combo positions for the IA
   */
  private transient final List<Object[]> comboToBeCancelled;

  /**
   * Game Constructor
   *
   * @param comboLength       int - length required for a valid combo
   * @param xLength           int - grid axe X length (without the border)
   * @param yLength           int - grid axe Y length (without the border)
   * @param registerInfoForIA boolean - true for register information for the IA
   */
  public GamePowerUnlimited(final int comboLength, final int xLength, final int yLength, boolean registerInfoForIA) {
    this.registerInfoForIA = registerInfoForIA;
    this.comboToBeCancelled = new ArrayList<>();
    this.players = new Player[0];
    this.createGrid(comboLength, xLength, yLength);
  }

  /**
   * reset the game to restart a new game/party
   */
  public void resetGame() {
    this.createGrid(this.comboLength, this.xLength, this.yLength);
  }

  /**
   * generate the grid with default data (barrier and empty pieces).
   *
   * @param axeXLength the axe X length
   * @param axeYLength the axe Y length
   */
  private void createGrid(final int comboLength, final int axeXLength, final int axeYLength) {
    this.comboToBeCancelled.clear();
    this.comboLength = comboLength;
    this.totalPiecesAdded = 0;
    this.counterRound = 0;
    this.xLength = axeXLength;
    this.yLength = axeYLength;
    // set last position to left bottom
    this.lastPositionPieceAddedX = this.comboLength;
    this.lastPositionPieceAddedY = this.yLengthWithBorder;
    // calculate barrier width (*2 because top, bottom, left and right barrier)
    this.xLengthWithBorder = this.xLength + (this.comboLength * 2);
    this.yLengthWithBorder = this.yLength + (this.comboLength * 2);
    // create grid/array 2d
    this.grid = new PieceType[this.xLengthWithBorder][this.yLengthWithBorder];
    // store data in grid
    for (int x = 0; x < this.xLengthWithBorder; x++) {
      for (int y = 0; y < this.yLengthWithBorder; y++) {
        this.grid[x][y] = x < this.comboLength ||
                          y < this.comboLength ||
                          x >= this.xLengthWithBorder - this.comboLength ||
                          y >= this.yLengthWithBorder - this.comboLength
          ? PieceType.BARRIER
          : PieceType.EMPTY;
      }
    }
  }

  public void playRound() {
    // get the player for the current round
    Player currentPlayer = this.getCurrentPlayer();
    // insert a new piece (if possible or do nothing)
    this.onTryInsertPiece(currentPlayer);
    GameState gamestate = this.checkGameState();
    this.onRender(gamestate);
    this.onGameState(gamestate);
  }

  /**
   * Check game status, control game stages
   * <ul>
   * <li> win = has combo</li>
   * <li> tie = grid is full</li>
   * <li> play = other</li>
   * </ul>
   *
   * @return {@link  GameState} - WIN or TIE or PLAY
   */
  public GameState checkGameState() {
    if (this.totalPiecesAdded == 0) {
      return GameState.PLAY;
    }

    PieceType lastPiece = this.grid[this.lastPositionPieceAddedX][this.lastPositionPieceAddedY];
    Direction[] directions = Direction.values();
    if (this.totalPiecesAdded > (this.comboLength - 2)) {
      for (Direction v : directions) {
        if (checkComboLine(this.lastPositionPieceAddedX, this.lastPositionPieceAddedY, v, lastPiece, false)) {
          return GameState.WIN;
        }
      }
    }

    if (this.totalPiecesAdded >= (this.xLength * this.yLength)) {
      return GameState.TIE;
    }

    this.counterRound++;
    return GameState.PLAY;
  }

  /**
   * Start from a position and move in direction to check for a valid combo ({@link GamePowerUnlimited#comboLength}).
   * If the combo is invalid and greater than 1, we take the last known position
   * and start again in the opposite direction.
   *
   * @param iPosX      int position axe X
   * @param iPosY      int position axe Y
   * @param dir        Direction for movement
   * @param pieceType  PieceType the piece excepted, see {@link PieceType}
   * @param isOpposite boolean is the opposite recursion
   * @return boolean: true if combo is valid
   */
  private boolean checkComboLine(int iPosX, int iPosY, Direction dir, PieceType pieceType, boolean isOpposite) {
    int countCombo = 1; // 1 because we take in count the starting piece

    for (int j = 0; j < this.comboLength; j++) {
      iPosX += dir.getX();
      iPosY += dir.getY();

      // check if same piece and increment combo counter
      if (!pieceType.equals(PieceType.EMPTY) &&
          !pieceType.equals(PieceType.BARRIER) &&
          this.isPieceAt(iPosX, iPosY, pieceType)
      ) {
        if (++countCombo >= this.comboLength) {
          return true;
        }
      } else {
        // (?) register possible combo for the AI, to avoid a second call of this method, as it could potentially
        // be expensive as it has to iterate on all directions over a length of 2 or more.
        if (this.registerInfoForIA && countCombo == (this.comboLength - 1)) {
          this.comboToBeCancelled.add(new Object[]{dir, iPosX - dir.getX(), iPosY - dir.getY(),});
        }

        // If we've started a combo, take the last position and go in the opposite dir to check again.
        return !isOpposite && countCombo > 1 && this.checkComboLine(
          iPosX + dir.opposite().getX(),
          iPosY + dir.opposite().getY(),
          dir.opposite(),
          pieceType,
          true
        );
      }
    }

    return false;
  }

  /**
   * Insert a new piece. The method creates a recursion to make the piece fall.
   *
   * @param displayPosY int - the display position axe Y
   * @param pieceType   PieceType - the piece, see {@link PieceType}
   * @return boolean: true if is added
   */
  public boolean insertPieces(int displayPosY, PieceType pieceType) {
    return this.insertPieces(this.comboLength, displayPosY + (this.getComboLength() - 1), pieceType);
  }

  /**
   * Insert a new piece. The method creates a recursion to make the piece fall.
   *
   * @param posX      int position axe X
   * @param posY      int position axe Y
   * @param pieceType PieceType the piece, see {@link PieceType}
   * @return boolean: true if is added
   */
  public boolean insertPieces(int posX, int posY, PieceType pieceType) {
    if (this.isPieceAt(posX, posY, PieceType.EMPTY)) {
      if (this.isPieceAt(posX + 1, posY, PieceType.EMPTY)) {
        return this.insertPieces(posX + 1, posY, pieceType);
      }
      this.addPieces(posX, posY, pieceType);
      return true;
    }
    return false;
  }

  /**
   * Check if the pieceType at position[x][y] equal to the pieceType sent in argument
   *
   * @param posX   the position on the X (with a barrier)
   * @param posY   the position on the Y (with a barrier)
   * @param pieces all pieces for the equals
   * @return true if the pieces equal with cell data
   */
  public boolean isPieceAt(int posX, int posY, @NotNull PieceType pieces) {
    return pieces.equals(this.grid[posX][posY]);
  }

  public void onGameState(@NotNull GameState gamestate) {
    switch (gamestate) {
      case WIN:
        this.onWin();
        this.resetGame();
        break;
      case TIE:
        this.onTie();
        this.resetGame();
        break;
      case PLAY:
      default:
        this.onPlay();
        break;
    }
  }

  /**
   * Event executed on game rendered
   */
  public void onRender(GameState gamestate) {

  }

  /**
   * Event executed on game play
   */
  public void onPlay() {
  }

  /**
   * Event executed on game tie
   */
  public void onTie() {
  }

  /**
   * Event executed on game win
   */
  public void onWin() {
  }

  /**
   * Event executed on try insert piece with position received from the player
   *
   * @param currentPlayer The current player for this current game round
   */
  protected void onTryInsertPiece(@org.jetbrains.annotations.NotNull Player currentPlayer) {
    if (!this.insertPieces(currentPlayer.getPosition(this.getYLength()), currentPlayer.getType()))
      this.onTryInsertPiece(currentPlayer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder().append("GamePowerUnlimited { grid =");
    for (int x = 0; x < this.xLengthWithBorder; x++) {
      sb.append("\n   {");
      if (x == this.lastPositionPieceAddedX) {
        // with indicator last piece
        for (int y = 0; y < this.yLengthWithBorder; y++) {
          sb.append(y == this.lastPositionPieceAddedY
            ? AnsiColor.RED.apply(String.valueOf(this.grid[x][y].ordinal()))
            : this.grid[x][y].ordinal()
          ).append(",");
        }
      } else {
        // normal
        for (int y = 0; y < this.yLengthWithBorder; y++) {
          sb.append(this.grid[x][y].ordinal()).append(",");
        }
      }
      sb.replace(sb.length() - 1, sb.length() - 1, "}");
    }
    return sb.append("\n};").toString();
  }

  @SuppressWarnings("unused")
  public String objectToString() {
    return "GamePowerUnlimited{" +
           "\n  comboLength=" + comboLength +
           ",\n  counterRound=" + counterRound +
           ",\n  xLength=" + xLength +
           ",\n  yLength=" + yLength +
           ",\n  xLengthWithBorder=" + xLengthWithBorder +
           ",\n  yLengthWithBorder=" + yLengthWithBorder +
           ",\n  totalPiecesAdded=" + totalPiecesAdded +
           ",\n  lastPositionPieceAddedX=" + lastPositionPieceAddedX +
           ",\n  lastPositionPieceAddedY=" + lastPositionPieceAddedY +
           ",\n  players= [...] (len" + players.length +
           "),\n  grid= [...] (len" + grid.length +
           "),\n  registerInfoForIA=" + registerInfoForIA +
           ",\n  comboToBeCancelled=" + comboToBeCancelled +
           "\n }";

  }

  /**
   * Set the player list
   *
   * @param players Player[] all player in array
   */
  public void setPlayers(Player[] players) {
    this.players = players;
  }

  /**
   * Add a new piece and increment the total piece added
   *
   * @param posX      position axe X
   * @param posY      position axe Y
   * @param pieceType type of the pieces, see {@link PieceType}
   */
  public void addPieces(int posX, int posY, PieceType pieceType) {
    this.grid[posX][posY] = pieceType;
    this.setLastPosXY(posX, posY);
    this.totalPiecesAdded++;
  }

  /**
   * Set the grid content
   *
   * @param data int[][] all piece ordinal id
   */
  public void setGrid(int[][] data) {
    PieceType[] pieceTypes = PieceType.values();
    for (int x = 0; x < this.getAxeXLengthWithBorder(); x++) {
      for (int y = 0; y < this.getAxeYLengthWithBorder(); y++) {
        int pieceId = data[x][y];
        if (pieceId > 1) {
          this.addPieces(x, y, pieceTypes[pieceId]);
        }
      }
    }
  }

  /**
   * Set the last position X Y of the piece added
   *
   * @param posX int position axe X
   * @param posY int position axe Y
   */
  public void setLastPosXY(int posX, int posY) {
    this.lastPositionPieceAddedX = posX;
    this.lastPositionPieceAddedY = posY;
  }

  /**
   * @return int {@link GamePowerUnlimited#xLength}
   */
  public int getXLength() {
    return this.xLength;
  }

  /**
   * @return int {@link GamePowerUnlimited#yLength}
   */
  public int getYLength() {
    return this.yLength;
  }

  /**
   * @return int {@link GamePowerUnlimited#xLengthWithBorder}
   */
  public int getAxeXLengthWithBorder() {
    return this.xLengthWithBorder;
  }

  /**
   * @return int {@link GamePowerUnlimited#yLengthWithBorder}
   */
  public int getAxeYLengthWithBorder() {
    return this.yLengthWithBorder;
  }

  /**
   * @return int {@link GamePowerUnlimited#comboLength}
   */
  public int getComboLength() {
    return this.comboLength;
  }

  /**
   * @return int {@link GamePowerUnlimited#counterRound}
   */
  public int getCounterRound() {
    return this.counterRound;
  }

  /**
   * @return int {@link GamePowerUnlimited#lastPositionPieceAddedY}
   */
  public int getLastPositionPieceAddedY() {
    return this.lastPositionPieceAddedY;
  }

  /**
   * @return int {@link GamePowerUnlimited#lastPositionPieceAddedX}
   */
  public int getLastPositionPieceAddedX() {
    return this.lastPositionPieceAddedX;
  }

  /**
   * @return int {@link GamePowerUnlimited#totalPiecesAdded}
   */
  public int getTotalPiecesAdded() {
    return this.totalPiecesAdded;
  }

  /**
   * @return PieceType[][] {@link GamePowerUnlimited#grid}
   */
  public PieceType[][] getGrid() {
    return this.grid;
  }

  /**
   * @return Player {@link Player} return current player for the round
   */
  public Player getCurrentPlayer() {
    return this.players[this.counterRound % this.players.length];
  }

  /**
   * @return List<Object [ ]> {@link GamePowerUnlimited#comboToBeCancelled}
   */
  public List<Object[]> getComboToBeCancelled() {
    return this.comboToBeCancelled;
  }

}
