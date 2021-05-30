package homework8.playerTypes

import homework8.Cross
import homework8.Game
import homework8.GameMark
import homework8.Nought
import javafx.beans.property.SimpleObjectProperty

class EasyBotPlayer(override var playerMark: GameMark) : Player {
    override val name = "Easy"
    override val playerType = Player.Type.BOT

    override fun getCoordinates(game: Game): Game.Coordinates? {
        TODO("Not yet implemented")
    }

    data class GameMarkCount(val gameMark: GameMark, val count: Int): Comparable<GameMarkCount> {
        override fun compareTo(other: GameMarkCount): Int {
            return when {
                this.count > other.count -> 1
                this.count == other.count -> 0
                else -> -1
            }
        }
    }

    /*fun checkInRows(game: Game) {
        val row: List<SimpleObjectProperty<GameMark>>? = null
        for (i in 0 until game.fieldSize) {
            val currentGameMark = checkInLine(game.field[i])
            when {
                currentGameMark.count == game.fieldSize - 1 -> {

                }
                else -> {}
            }
        }
    }*/

    fun checkInLine(line: List<SimpleObjectProperty<GameMark>>): GameMarkCount {
        val crossCount = GameMarkCount(
            Cross(),
            line.count {
                it.name == "cross"
            }
        )
        val noughtCount = GameMarkCount(
            Nought(),
            line.count {
                it.name == "nought"
            }
        )
        return maxOf(crossCount, noughtCount)
    }
}