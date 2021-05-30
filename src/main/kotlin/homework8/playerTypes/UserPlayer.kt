package homework8.playerTypes

import homework8.Game
import homework8.GameMark

class UserPlayer(override val name: String, override var playerMark: GameMark) : Player {
    override val playerType = Player.Type.USER
    override fun getCoordinates(game: Game): Game.Coordinates? = null
}
