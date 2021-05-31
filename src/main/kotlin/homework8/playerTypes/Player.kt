package homework8.playerTypes

import homework8.gameModel.Game
import homework8.gameModel.GameMark

interface Player {
    enum class Type {
        USER, RANDBOT, SMARTBOT
    }
    var score: Int
    val name: String
    val playerType: Type
    var playerMark: GameMark
    fun getCoordinates(game: Game): Game.Coordinates?
}
