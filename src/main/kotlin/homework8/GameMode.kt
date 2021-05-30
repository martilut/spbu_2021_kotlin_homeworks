package homework8

import homework8.playerTypes.*

interface GameMode {
    companion object {
        fun getMark(userPlayer: UserPlayer): GameMark {
            return when(userPlayer.playerMark.name) {
                "cross" -> Nought()
                else -> Cross()
            }
        }
    }
    val userPlayer: UserPlayer
    val opponentPlayer: Player
    val opponentType: Player.Type
        get() = opponentPlayer.playerType
}

class UserGameMode(override val userPlayer: UserPlayer, private val _opponentPlayer: UserPlayer) : GameMode {
    override val opponentPlayer = _opponentPlayer
}

class RandBotGameMode(override val userPlayer: UserPlayer) : GameMode {
    override val opponentPlayer = RandomBotPlayer(GameMode.getMark(userPlayer))
}

class EasyBotGameMode(override val userPlayer: UserPlayer) : GameMode {
    override val opponentPlayer = EasyBotPlayer(GameMode.getMark(userPlayer))
}

class HardBotGameMode(override val userPlayer: UserPlayer) : GameMode {
    override val opponentPlayer = HardBotPlayer(GameMode.getMark(userPlayer))
}
