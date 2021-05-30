package homework8.gameModel

import homework8.playerTypes.Player
import homework8.playerTypes.UserPlayer
import homework8.playerTypes.RandomBotPlayer
import homework8.playerTypes.EasyBotPlayer

interface GameMode {
    val userPlayer: UserPlayer
    val opponentPlayer: Player
    val opponentType: Player.Type
        get() = opponentPlayer.playerType

    companion object {
        fun getOpponentMark(userPlayer: UserPlayer): GameMark {
            return when(userPlayer.playerMark.name) {
                "cross" -> Nought()
                else -> Cross()
            }
        }
    }
}

class UserGameMode(override val userPlayer: UserPlayer, private val _opponentPlayer: UserPlayer) : GameMode {
    override val opponentPlayer = _opponentPlayer
}

class RandBotGameMode(override val userPlayer: UserPlayer) : GameMode {
    override val opponentPlayer = RandomBotPlayer(GameMode.getOpponentMark(userPlayer))
}

class EasyBotGameMode(override val userPlayer: UserPlayer) : GameMode {
    override val opponentPlayer = EasyBotPlayer(GameMode.getOpponentMark(userPlayer))
}
