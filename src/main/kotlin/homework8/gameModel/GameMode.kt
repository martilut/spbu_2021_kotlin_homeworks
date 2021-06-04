package homework8.gameModel

import homework8.playerTypes.Player
import homework8.playerTypes.UserPlayer
import homework8.playerTypes.RandomBotPlayer
import homework8.playerTypes.SmartBotPlayer

interface GameMode {
    val userPlayer: UserPlayer
    val opponentPlayer: Player

    companion object {
        fun getOpponentMark(userPlayer: UserPlayer): GameMark {
            return when (userPlayer.playerMark.name) {
                "cross" -> Nought()
                else -> Cross()
            }
        }
    }
}

class UserGameMode(override val userPlayer: UserPlayer, override val opponentPlayer: UserPlayer) : GameMode

class RandBotGameMode(override val userPlayer: UserPlayer) : GameMode {
    override val opponentPlayer = RandomBotPlayer(GameMode.getOpponentMark(userPlayer))
}

class SmartBotGameMode(override val userPlayer: UserPlayer) : GameMode {
    override val opponentPlayer = SmartBotPlayer(GameMode.getOpponentMark(userPlayer))
}
