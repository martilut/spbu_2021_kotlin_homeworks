@file:Suppress("MagicNumber")

package homework8.gameView

import homework8.gameModel.Game
import homework8.gameModel.GameController
import homework8.gameModel.GameMark
import homework8.playerTypes.Player
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.stage.StageStyle
import tornadofx.Fragment
import tornadofx.View
import tornadofx.action
import tornadofx.borderpane
import tornadofx.box
import tornadofx.button
import tornadofx.center
import tornadofx.clear
import tornadofx.gridpane
import tornadofx.hbox
import tornadofx.label
import tornadofx.listmenu
import tornadofx.onChange
import tornadofx.onLeftClick
import tornadofx.plusAssign
import tornadofx.px
import tornadofx.row
import tornadofx.stackpane
import tornadofx.style
import tornadofx.top

class SingleCell : Fragment() {
    val markProperty: SimpleObjectProperty<GameMark> by param()

    override val root = stackpane {
        label(" ")
        style {
            prefWidth = 150.px
            prefHeight = 150.px
            padding = box(5.px)
            borderColor += box(Color.GRAY)
            fontSize = 50.px
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
            for (x in 0 until game.fieldSize) {
                row {
                    for (y in 0 until game.fieldSize) {
                        val cell = find<SingleCell>(mapOf(SingleCell::markProperty to game.getCell(x, y)))
                        cell.apply {
                            this.root.onLeftClick {
                                controller.makeTurn(x, y, game)
                            }
                        }
                        this += cell
                    }
                }
            }
        }
        controller.resultProperty.onChange {
            if (controller.resultProperty.value == null && controller.isBotFirst()) {
                controller.makeTurnBot(game)
            }
        }
    }
}

class ResultWindow : Fragment() {
    val result: String by param()
    override val root = hbox {
        label(result).style {
            fontSize = 10.px
        }
    }
}

class GameView : View() {
    private val controller: GameController by inject()
    val game: Game by param()

    override val root = borderpane {
        top {
            hbox {
                alignment = Pos.CENTER
                button("BACK TO MENU").action {
                    close()
                    find<MainMenu>().openWindow()
                }
                button("CLEAR FIELD").action {
                    if (controller.resultProperty.value != null) {
                        controller.clearField(game)
                    }
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

    override fun onDock() {
        controller.clearField(game)
        if (controller.isBotFirst()) {
            controller.makeTurnBot(game)
        }
    }
}

class ChooseGameMark : Fragment() {
    private val mainMenu: MainMenu by inject()

    override val root = hbox {
        style {
            fontSize = 50.px
            alignment = Pos.CENTER
        }
        button("X").setOnAction {
            close()
            mainMenu.performWhenSelected(true)
        }
        button("O").setOnAction {
            close()
            mainMenu.performWhenSelected(false)
        }
    }
}

class MainMenu : View() {
    private val controller: GameController by inject()
    private val game = Game()
    private var playerType: Player.Type = Player.Type.USER

    override val root = borderpane {
        prefWidth = 400.0
        prefHeight = 400.0

        center {
            listmenu {
                item(text = "User vs User") {
                    onLeftClick {
                        playerType = Player.Type.USER
                        performWhenSelected()
                    }
                }
                item(text = "User vs Easy Bot") {
                    onLeftClick {
                        playerType = Player.Type.RANDBOT
                        replaceWith<ChooseGameMark>()
                    }
                }
                item(text = "User vs Hard Bot") {
                    onLeftClick {
                        playerType = Player.Type.SMARTBOT
                        replaceWith<ChooseGameMark>()
                    }
                }
                item(text = "Exit") {
                    onLeftClick {
                        close()
                    }
                }
            }
        }
    }

    fun performWhenSelected(isUserFirst: Boolean = true) {
        controller.changeGameMode(playerType, isUserFirst)
        close()
        find<GameView>(mapOf(GameView::game to game)).openModal(stageStyle = StageStyle.UNIFIED)
    }

    override fun onUndock() {
        if (controller.gameModeProperty.value != null) {
            controller.clearField(game)
        }
    }
}
