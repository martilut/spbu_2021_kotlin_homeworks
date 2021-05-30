package homework8.playerTypes

import homework8.Game
import homework8.GameMark
import kotlin.random.Random

class RandomBotPlayer(override var playerMark: GameMark) : Player {
    override val name = "Random"
    override val playerType = Player.Type.BOT

    override fun getCoordinates(game: Game): Game.Coordinates? {
        val emptyCells = game.getEmptyCells()
        return when(emptyCells.size) {
            0 -> null
            else -> emptyCells[Random.nextInt(0, emptyCells.size)]
        }
    }
}