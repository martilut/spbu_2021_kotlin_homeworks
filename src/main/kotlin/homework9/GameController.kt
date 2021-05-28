package homework9

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller

class GameController : Controller() {
    val resultProperty = SimpleStringProperty(null)

    fun makeTurn(x: Int, y: Int, game: Game) {
        if (game.currentPlayer.playerType == PlayerType.USER) {
            game.setCellValue(x, y)
            if (game.isEnd(x, y)) {
                return
            }
        }
        game.changePlayer()
        if (game.currentPlayer.playerType != PlayerType.USER) {
            val coordinates = game.currentPlayer.getCoordinates(game.getEmptyCells())
            if (coordinates != null) {
                game.setCellValue(coordinates.x, coordinates.y)
                if (game.isEnd(coordinates.x, coordinates.y)) {
                    return
                }
            }
            game.changePlayer()
        }
    }

    fun clearField(game: Game) {
        game.clearLocalField()
        resultProperty.set(null)
    }

    private fun Game.isEnd(x: Int, y: Int): Boolean {
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
        val diagonalUpCheck = this.checkInDiagonalUp(x, y)
        val diagonalDownCheck = this.checkInDiagonalDown(x, y)
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

    private fun Game.checkInDiagonalDown(x: Int, y: Int): Boolean {
        var xMinus = x
        var yMinus = y
        var xPlus = x
        var yPlus = y

        val diagonalElements = mutableListOf<SimpleObjectProperty<GameMark>>()
        while (xMinus >= 0 && yMinus >= 0) {
            diagonalElements.add(this.field[xMinus][yMinus])
            --xMinus
            --yMinus
        }
        while (xPlus < this.fieldSize && yPlus < this.field[0].size) {
            diagonalElements.add(this.field[xPlus][yPlus])
            ++xPlus
            ++yPlus
        }

        return diagonalElements.all {
            it.value == diagonalElements[0].value
        }
    }

    private fun Game.checkInDiagonalUp(x: Int, y: Int): Boolean {
        var xMinus = x
        var yMinus = y
        var xPlus = x
        var yPlus = y

        val diagonalElements = mutableListOf<SimpleObjectProperty<GameMark>>()
        while (xPlus < this.fieldSize && yMinus >= 0) {
            diagonalElements.add(this.field[xPlus][yMinus])
            ++xPlus
            --yMinus
        }
        while (xMinus >= 0 && yPlus < this.field[0].size) {
            diagonalElements.add(this.field[xMinus][yPlus])
            --xMinus
            ++yPlus
        }

        return diagonalElements.all {
            it.value == diagonalElements[0].value
        }
    }
}
