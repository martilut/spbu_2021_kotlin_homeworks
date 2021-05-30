package homework8.gameModel

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller
import homework8.playerTypes.Player

class GameController : Controller() {
    val resultProperty = SimpleStringProperty(null)

    fun makeTurn(x: Int, y: Int, game: Game) {
        if (game.getCell(x, y).value == null && resultProperty.value == null) {
            if (!makeTurnUser(x, y, game)) {
                if (game.currentPlayer.playerType != Player.Type.USER) {
                    makeTurnBot(game)
                }
            }
        }
    }

    private fun makeTurnUser(x: Int, y: Int, game: Game): Boolean {
        game.setCellValue(x, y)
        return when(game.isOver(x, y)) {
            true -> true
            else -> {
                game.changePlayer()
                false
            }
        }
    }

    fun makeTurnBot(game: Game) {
        return when(val coordinates = game.currentPlayer.getCoordinates(game)) {
            null -> {}
            else -> {
                game.setCellValue(coordinates.x, coordinates.y)
                when (game.isOver(coordinates.x, coordinates.y)) {
                    true -> {}
                    else -> {
                        game.changePlayer()
                    }
                }
            }
        }
    }

    fun isBotFirst(game: Game): Boolean = game.gameMode.opponentType == Player.Type.BOT

    fun clearField(game: Game) {
        game.clearLocalField()
        resultProperty.set(null)
    }

    private fun Game.isOver(x: Int, y: Int): Boolean {
        return when {
            this.isWin(x, y) && this.fieldFilled != 0 -> {
                resultProperty.set("${this.currentPlayer.name} won!\n" + this.getStatistics())
                this.gameCount += 1
                true
            }
            this.fieldFilled == this.fieldSize * this.fieldSize -> {
                resultProperty.set("Draw!\n" + this.getStatistics())
                this.gameCount += 1
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

    private fun Game.checkInRow(rowIndex: Int): Boolean {
        return this.field[rowIndex].all {
            it.value == this.field[rowIndex][0].value
        }
    }

   private fun Game.checkInColumn(columnIndex: Int): Boolean {
        val columnElements = mutableListOf<SimpleObjectProperty<GameMark>>()
        for (element in this.field) {
            columnElements.add(element[columnIndex])
        }
        return columnElements.all {
            it.value == columnElements[0].value
        }
    }

    private fun Game.checkInDiagonalDown(): Boolean {
        val diagonalElements = mutableListOf<SimpleObjectProperty<GameMark>>()
        for (i in 0 until this.fieldSize) {
            diagonalElements.add(this.field[i][i])
        }
        return diagonalElements.all {
            it.value == diagonalElements[0].value
        }
    }

    private fun Game.checkInDiagonalUp(): Boolean {
        val diagonalElements = mutableListOf<SimpleObjectProperty<GameMark>>()
        for (i in 0 until this.fieldSize) {
            diagonalElements.add(this.field[this.fieldSize - 1 - i][i])
        }
        return diagonalElements.all {
            it.value == diagonalElements[0].value
        }
    }
}
