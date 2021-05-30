package homework8.playerTypes

import homework8.gameModel.Game
import homework8.gameModel.GameMark

class RandomBotPlayer(override var playerMark: GameMark) : Player {
    override val name = "Random"
    override val playerType = Player.Type.BOT

    override fun getCoordinates(game: Game): Game.Coordinates? = game.getRandomCoordinates()
}
