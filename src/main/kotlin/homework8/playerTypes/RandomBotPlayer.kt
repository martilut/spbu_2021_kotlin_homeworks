package homework8.playerTypes

import homework8.gameModel.Game
import homework8.gameModel.GameMark

class RandomBotPlayer(override var playerMark: GameMark) : Player {
    override var score = 0
    override val name = "Easy Bot"
    override val playerType = Player.Type.RANDBOT

    override fun getCoordinates(game: Game): Game.Coordinates? = game.getRandomCoordinates()
}
