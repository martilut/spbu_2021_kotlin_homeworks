package homework9

interface GameMark {
    val name: String
    val markValue: String
}

class Cross : GameMark {
    override val name = "cross"
    override val markValue = "X"
}

class Nought : GameMark {
    override val name = "nought"
    override val markValue = "O"
}