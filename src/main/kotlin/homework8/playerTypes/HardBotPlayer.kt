package homework8.playerTypes

import homework8.Game
import homework8.GameMark

class HardBotPlayer(override var playerMark: GameMark) : Player {
    override val name = "Hard"
    override val playerType = Player.Type.BOT

    override fun getCoordinates(game: Game): Game.Coordinates? {
        TODO("Not yet implemented")
    }
}