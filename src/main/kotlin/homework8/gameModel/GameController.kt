package homework8.gameModel

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller
import homework8.playerTypes.Player
import homework8.playerTypes.UserPlayer

class GameController : Controller() {
    val gameModeProperty = SimpleObjectProperty<GameMode>()
    private var currentPlayer: Player = UserPlayer("Player 1", Cross())

    val resultProperty = SimpleStringProperty()

    fun changeGameMode(opponentPlayerType: Player.Type, isUserFirst: Boolean) {
        when (opponentPlayerType) {
            Player.Type.USER -> {
                val value = UserGameMode(
                    UserPlayer("Player 1", Cross()),
                    UserPlayer("Player 2", Nought())
                )
                gameModeProperty.set(value)
            }
            Player.Type.RANDBOT -> {
                val value = when (isUserFirst) {
                    true -> RandBotGameMode(UserPlayer("Player", Cross()))
                    else -> RandBotGameMode(UserPlayer("Player", Nought()))
                }
                gameModeProperty.set(value)
            }
            Player.Type.SMARTBOT -> {
                val value = when (isUserFirst) {
                    true -> SmartBotGameMode(UserPlayer("Player", Cross()))
                    else -> SmartBotGameMode(UserPlayer("Player", Nought()))
                }
                gameModeProperty.set(value)
            }
        }
        currentPlayer = getStartPlayer()
    }

    fun makeTurn(x: Int, y: Int, game: Game) {
        if (game.getCell(x, y).value == null && resultProperty.value == null) {
            if (!makeTurnUser(x, y, game)) {
                if (currentPlayer.playerType != Player.Type.USER) {
                    makeTurnBot(game)
                }
            }
        }
    }

    private fun makeTurnUser(x: Int, y: Int, game: Game): Boolean {
        game.setCellValue(x, y, currentPlayer.playerMark)
        return when (game.isOver(x, y)) {
            true -> true
            else -> {
                changePlayer()
                false
            }
        }
    }

    fun makeTurnBot(game: Game) {
        when (val coordinates = currentPlayer.getCoordinates(game)) {
            null -> {}
            else -> {
                game.setCellValue(coordinates.x, coordinates.y, currentPlayer.playerMark)
                when (game.isOver(coordinates.x, coordinates.y)) {
                    true -> {}
                    else -> {
                        changePlayer()
                    }
                }
            }
        }
    }

    private fun getStartPlayer(): Player {
        return when (gameModeProperty.value.userPlayer.playerMark.name) {
            "cross" -> gameModeProperty.value.userPlayer
            else -> gameModeProperty.value.opponentPlayer
        }
    }

    private fun changePlayer() {
        val gameMode = gameModeProperty.value
        currentPlayer = when (currentPlayer.playerMark) {
            gameMode.userPlayer.playerMark -> gameMode.opponentPlayer
            else -> gameMode.userPlayer
        }
    }

    fun isBotFirst(): Boolean {
        val gameMode = gameModeProperty.value
        return gameMode.opponentPlayer.playerType != Player.Type.USER && gameMode.userPlayer.playerMark.name != "cross"
    }

    fun clearField(game: Game) {
        currentPlayer = getStartPlayer()
        game.clearLocalField()
        resultProperty.set(null)
    }

    private fun Game.isOver(x: Int, y: Int): Boolean {
        return when {
            this.isWin(x, y) && this.fieldFilled != 0 -> {
                ++currentPlayer.score
                resultProperty.set(
                    "${currentPlayer.name} won!\n" + this.getStatistics(gameModeProperty.value.userPlayer, gameModeProperty.value.opponentPlayer)
                )
                true
            }
            this.fieldFilled == this.fieldSize * this.fieldSize -> {
                resultProperty.set(
                    "Draw!\n" + this.getStatistics(gameModeProperty.value.userPlayer, gameModeProperty.value.opponentPlayer)
                )
                true
            }
            else -> false
        }
    }

    private fun Game.isWin(x: Int, y: Int): Boolean {
        val rowCheck = this.checkInRow(x)
        val columnCheck = this.checkInColumn(y)
        val diagonalUpCheck = this.checkInDiagonalUp()
        val diagonalDownCheck = this.checkInDiagonalDown()
        return when {
            x == y -> rowCheck || columnCheck || diagonalDownCheck
            x + y == this.fieldSize - 1 -> rowCheck || columnCheck || diagonalUpCheck
            else -> rowCheck || columnCheck
        }
    }
}
