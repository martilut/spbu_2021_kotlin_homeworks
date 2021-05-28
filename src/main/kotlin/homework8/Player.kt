package homework8

import kotlin.random.Random

enum class PlayerType {
    USER, RANDBOT, EASYBOT, HARDBOT
}

interface Player {
    val name: String
    val playerType: PlayerType
    var playerMark: GameMark
    fun getCoordinates(emptyCells: List<Game.Coordinates>): Game.Coordinates?
}

class UserPlayer(override val name: String, override var playerMark: GameMark) : Player {
    override val playerType = PlayerType.USER
    override fun getCoordinates(emptyCells: List<Game.Coordinates>): Game.Coordinates? = null
}

class RandomBotPlayer(override var playerMark: GameMark) : Player {
    override val name = "Random"
    override val playerType = PlayerType.RANDBOT

    override fun getCoordinates(emptyCells: List<Game.Coordinates>): Game.Coordinates? {
        return when(emptyCells.size) {
            0 -> null
            else -> emptyCells[Random.nextInt(0, emptyCells.size)]
        }
    }
}

class EasyBotPlayer(override var playerMark: GameMark) : Player {
    override val name = "Easy"
    override val playerType = PlayerType.EASYBOT

    override fun getCoordinates(emptyCells: List<Game.Coordinates>): Game.Coordinates? {
        TODO("Not yet implemented")
    }
}

class HardBotPlayer(override var playerMark: GameMark) : Player {
    override val name = "Hard"
    override val playerType = PlayerType.HARDBOT

    override fun getCoordinates(emptyCells: List<Game.Coordinates>): Game.Coordinates? {
        TODO("Not yet implemented")
    }
}
