package homework8.gameModel

import homework8.playerTypes.Player
import javafx.beans.property.SimpleObjectProperty
import kotlin.random.Random

class Game(val fieldSize: Int = 3) {
    data class Coordinates(val x: Int, val y: Int)

    val field = List(fieldSize) { List(fieldSize) { SimpleObjectProperty<GameMark>(null) } }
    var fieldFilled = 0

    private fun getEmptyCells(): List<Coordinates> {
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

    fun getRandomCoordinates(): Coordinates? {
        val emptyCells = this.getEmptyCells()
        return when (emptyCells.size) {
            0 -> null
            else -> emptyCells[Random.nextInt(0, emptyCells.size)]
        }
    }

    fun setCellValue(x: Int, y: Int, playerMark: GameMark) {
        field[x][y].set(playerMark)
        fieldFilled += 1
    }

    fun clearLocalField() {
        for (x in 0 until fieldSize) {
            for (y in 0 until fieldSize) {
                field[x][y].set(null)
            }
        }
        fieldFilled = 0
    }

    fun getCell(x: Int, y: Int): SimpleObjectProperty<GameMark> = field[x][y]

    fun getStatistics(firstPlayer: Player, secondPlayer: Player): String {
        return "${firstPlayer.name} : ${firstPlayer.score}\n" + "${secondPlayer.name} : ${secondPlayer.score}"
    }

    fun checkInRow(rowIndex: Int): Boolean {
        return this.field[rowIndex].all {
            it.value == this.field[rowIndex][0].value
        }
    }

    fun checkInColumn(columnIndex: Int): Boolean {
        val columnElements = mutableListOf<SimpleObjectProperty<GameMark>>()
        for (element in this.field) {
            columnElements.add(element[columnIndex])
        }
        return columnElements.all {
            it.value == columnElements[0].value
        }
    }

    fun checkInDiagonalDown(): Boolean {
        val diagonalElements = mutableListOf<SimpleObjectProperty<GameMark>>()
        for (i in 0 until this.fieldSize) {
            diagonalElements.add(this.field[i][i])
        }
        return diagonalElements.all {
            it.value == diagonalElements[0].value
        }
    }

    fun checkInDiagonalUp(): Boolean {
        val diagonalElements = mutableListOf<SimpleObjectProperty<GameMark>>()
        for (i in 0 until this.fieldSize) {
            diagonalElements.add(this.field[this.fieldSize - 1 - i][i])
        }
        return diagonalElements.all {
            it.value == diagonalElements[0].value
        }
    }
}
