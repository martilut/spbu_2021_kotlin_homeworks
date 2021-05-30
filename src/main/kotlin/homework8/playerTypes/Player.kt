package homework8.playerTypes

import homework8.Game
import homework8.GameMark

interface Player {
    enum class Type {
        USER, BOT
    }
    val name: String
    val playerType: Type
    var playerMark: GameMark
    fun getCoordinates(game: Game): Game.Coordinates?
}
