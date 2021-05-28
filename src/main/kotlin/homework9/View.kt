package homework9

import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.stage.StageStyle
import tornadofx.*


class SingleCell : Fragment() {
    val markProperty: SimpleObjectProperty<GameMark> by param()

    override val root = stackpane {
        label(" ")
        style {
            prefWidth = 200.px
            prefHeight = 200.px
            padding = box(5.px)
            borderColor += box(Color.GRAY)
        }
    }

    init {
        markProperty.onChange {
            root.clear()
            when (it) {
                null -> {}
                else -> root += label(it.markValue)
            }
        }
    }
}

class GameGrid : View() {
    private val controller: GameController by inject()
    val game: Game by param()

    override val root: GridPane = gridpane {
        alignment = Pos.CENTER
    }

    init {
        with(root) {
            for (x in 0..2) {
                row {
                    for (y in 0..2) {
                        val cell = find<SingleCell>(mapOf(SingleCell::markProperty to game.getCell(x, y)))
                        cell.apply {
                            this.root.onLeftClick {
                                if (game.getCell(x, y).value == null && controller.resultProperty.value == null) {
                                    controller.makeTurn(x, y, game)
                                }
                            }
                        }
                        this += cell
                    }
                }
            }
        }
    }
}

class ResultWindow : Fragment() {
    val result: String by param()
    override val root = label(result)
}

class GameView : View() {
    private val controller: GameController by inject()
    val game: Game by param()

    override val root = borderpane {
        top {
            hbox {
                button("PRESS").action {
                    game.getGameMatrix()
                }
                button("BACK TO MENU").action {
                    close()
                    find<MainMenu>().openWindow()
                }
                button("CLEAR FIELD").action {
                    controller.clearField(game)
                }
            }
        }
        center = find<GameGrid>(mapOf(GameGrid::game to game)).root
    }

    init {
        controller.resultProperty.onChange {
            if (controller.resultProperty.value != null) {
                val resultWindow = find<ResultWindow>(mapOf(ResultWindow::result to controller.resultProperty.value))
                resultWindow.openModal(stageStyle = StageStyle.UTILITY)
            }
        }
    }
}

class MainMenu : View() {
    //private val controller: GameController by inject()
    //private val toggleGroup = ToggleGroup()
    //private val newGameMode = UserGameMode(UserPlayer("User1", Cross()), UserPlayer("User2", Nought()))
    private val newGameMode2 = RandBotGameMode(UserPlayer("Felix", Cross()))
    private val newGame = Game(gameMode = newGameMode2)

    override val root = borderpane {
        prefWidth = 500.0
        prefHeight = 500.0

        top {
            label("TIC TAC TOE")
        }
        /*center {
            hbox {
                togglebutton("CROSS", toggleGroup).setOnAction {
                    controller.currentPlayer = controller.crossPlayer
                }
                togglebutton("NOUGHT", toggleGroup).setOnAction {
                    controller.currentPlayer = controller.noughtPlayer
                }
            }
        }*/
    }

    init {
        with(root) {
            bottom {
                button("START").setOnAction {
                    close()
                    find<GameView>(mapOf(GameView::game to newGame)).openWindow()
                }
            }
        }
    }
}
