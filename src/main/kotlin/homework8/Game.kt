package homework8

import homework8.playerTypes.Player
import javafx.beans.property.SimpleObjectProperty

class Game(val fieldSize: Int = 3, val gameMode: GameMode) {
    var currentPlayer = getStartPlayer()

    data class Coordinates(val x: Int, val y: Int)

    var gameCount = 0

    val field = List(fieldSize) { List(fieldSize) { SimpleObjectProperty<GameMark>(null) } }
    var fieldFilled = 0

    private fun getStartPlayer(): Player {
        return when (gameMode.userPlayer.playerMark.name) {
            "cross" -> gameMode.userPlayer
            else -> gameMode.opponentPlayer
        }
    }

    fun changePlayer() {
        currentPlayer = when(currentPlayer.playerMark) {
            gameMode.userPlayer.playerMark -> gameMode.opponentPlayer
            else -> gameMode.userPlayer
        }
    }

    fun getEmptyCells(): List<Coordinates> {
        val emptyCells = mutableListOf<Coordinates>()
        for (x in 0 until fieldSize) {
            for (y in 0 until fieldSize) {
                if (field[x][y].value == null) {
                    emptyCells.add(Coordinates(x, y))
                }
            }
        }
        return emptyCells
    }

    fun setCellValue(x: Int, y: Int) {
        field[x][y].set(currentPlayer.playerMark)
        fieldFilled += 1
    }

    fun clearLocalField() {
        for (x in 0 until fieldSize) {
            for (y in 0 until fieldSize) {
                field[x][y].set(null)
            }
        }
        fieldFilled = 0
        currentPlayer = getStartPlayer()
    }

    fun getCell(x: Int, y: Int): SimpleObjectProperty<GameMark> = field[x][y]

    fun getStatistics(): String = "Game number: $gameCount"

    fun getGameMatrix() {
        for (x in 0..2) {
            for (y in 0..2) {
                if (this.field[x][y].value == null) {
                    print("null ")
                } else {
                    print("${this.field[x][y].value.markValue} ")
                }
            }
            print('\n')
        }
    }
}
