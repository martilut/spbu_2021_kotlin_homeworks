package homework8.playerTypes

import homework8.gameModel.Cross
import homework8.gameModel.Game
import homework8.gameModel.GameMark
import homework8.gameModel.Nought
import javafx.beans.property.SimpleObjectProperty

class SmartBotPlayer(override var playerMark: GameMark) : Player {
    override var score = 0
    override val name = "Easy"
    override val playerType = Player.Type.SMARTBOT

    override fun getCoordinates(game: Game): Game.Coordinates? {
        val rowCheck = checkInRows(game)
        val columnCheck = checkInColumns(game)
        val diagonalDownCheck = checkInDiagonalDown(game)
        val diagonalUpCheck = checkInDiagonalUp(game)
        return when {
            rowCheck != null -> rowCheck
            columnCheck != null -> columnCheck
            diagonalDownCheck != null -> diagonalDownCheck
            diagonalUpCheck != null -> diagonalUpCheck
            else -> game.getRandomCoordinates()
        }
    }

    data class GameMarkCount(val gameMark: GameMark) {
        var count = 0
        var emptyCellIndex = 0
    }

    private fun checkInLine(line: List<SimpleObjectProperty<GameMark>>): GameMarkCount? {
        var emptyCellIndex: Int? = null
        val crosses = GameMarkCount(Cross())
        val noughts = GameMarkCount(Nought())
        for (i in line.indices) {
            if (line[i].value != null) {
                when(line[i].value.markValue) {
                    crosses.gameMark.markValue -> ++crosses.count
                    else -> ++noughts.count
                }
            } else {
                emptyCellIndex = i
            }
        }
        return when {
            emptyCellIndex == null -> null
            crosses.count == line.size - 1 -> {
                crosses.emptyCellIndex = emptyCellIndex
                crosses
            }
            noughts.count == line.size - 1 -> {
                noughts.emptyCellIndex = emptyCellIndex
                noughts
            }
            else -> null
        }
    }

    private fun checkInRows(game: Game): Game.Coordinates? {
        var answer: Game.Coordinates? = null
        for (rowIndex in game.field.indices) {
            val result = checkInLine(game.field[rowIndex])
            when {
                result == null -> {}
                result.gameMark.markValue == playerMark.markValue -> {
                    answer = Game.Coordinates(rowIndex, result.emptyCellIndex)
                }
                result.gameMark.markValue != playerMark.markValue && answer == null -> {
                    answer = Game.Coordinates(rowIndex, result.emptyCellIndex)
                }
            }
        }
        return answer
    }

    private fun checkInColumns(game: Game): Game.Coordinates? {
        var answer: Game.Coordinates? = null
        for (columnIndex in game.field.indices) {
            val column = mutableListOf<SimpleObjectProperty<GameMark>>()
            for (element in game.field) {
                column.add(element[columnIndex])
            }
            val result = checkInLine(column)
            when {
                result == null -> {}
                result.gameMark.markValue == playerMark.markValue -> {
                    answer = Game.Coordinates(result.emptyCellIndex, columnIndex)
                }
                result.gameMark.markValue != playerMark.markValue && answer == null -> {
                    answer = Game.Coordinates(result.emptyCellIndex, columnIndex)
                }
            }
        }
        return answer
    }

    private fun checkInDiagonalDown(game: Game): Game.Coordinates? {
        var answer: Game.Coordinates? = null
        val diagonal = mutableListOf<SimpleObjectProperty<GameMark>>()
        for (i in 0 until game.fieldSize) {
            diagonal.add(game.field[i][i])
        }
        val result = checkInLine(diagonal)
        when {
            result == null -> {}
            result.gameMark.markValue == playerMark.markValue -> {
                answer = Game.Coordinates(result.emptyCellIndex, result.emptyCellIndex)
            }
            result.gameMark.markValue != playerMark.markValue && answer == null -> {
                answer = Game.Coordinates(result.emptyCellIndex, result.emptyCellIndex)
            }
        }
        return answer
    }

    private fun checkInDiagonalUp(game: Game): Game.Coordinates? {
        var answer: Game.Coordinates? = null
        val diagonal = mutableListOf<SimpleObjectProperty<GameMark>>()
        for (i in 0 until game.fieldSize) {
            diagonal.add(game.field[game.fieldSize - 1 - i][i])
        }
        val result = checkInLine(diagonal)
        when {
            result == null -> {}
            result.gameMark.markValue == playerMark.markValue -> {
                answer = Game.Coordinates(game.fieldSize - 1 - result.emptyCellIndex, result.emptyCellIndex)
            }
            result.gameMark.markValue != playerMark.markValue && answer == null -> {
                answer = Game.Coordinates(game.fieldSize - 1 - result.emptyCellIndex, result.emptyCellIndex)
            }
        }
        return answer
    }
}
