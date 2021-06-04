package test2fix

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.http.userAgent
import it.skrape.core.htmlDocument
import it.skrape.selects.DocElement
import it.skrape.selects.html5.a
import it.skrape.selects.html5.article
import it.skrape.selects.html5.div
import javafx.scene.control.TextArea
import kotlinx.coroutines.runBlocking
import tornadofx.App
import tornadofx.Controller
import tornadofx.View
import tornadofx.action
import tornadofx.button
import tornadofx.hbox
import tornadofx.launch
import tornadofx.textarea
import tornadofx.vbox

data class Quote(val id: Int, val date: String, val text: String) {
    override fun toString(): String {
        return "#$id - $date\n$text\n\n"
    }
}

class QuoteView : View("Цитатник") {
    private val controller = BashController()
    private lateinit var textArea: TextArea

    override val root = vbox {
        hbox {
            button("Показать лучшие цитаты за все время") {
                action {
                    runAsync { controller.getBest() } ui { quotes ->
                        textArea.text = quotes.joinToString("")
                    }
                }
            }
            button("Показать последние цитаты") {
                action {
                    runAsync { controller.getLatest() } ui { quotes ->
                        textArea.text = quotes.joinToString("")
                    }
                }
            }
            button("Показать случайную цитату") {
                action {
                    runAsync { controller.getRandom() } ui { quotes ->
                        textArea.text = quotes.joinToString("")
                    }
                }
            }
        }
        textArea = textarea()
    }
}

class BashController : Controller() {
    private val client = HttpClient(CIO)

    private fun DocElement.parseQuote(): Quote {
        val idString = a {
            withClass = "quote__header_permalink"
            findFirst { text }
        }
        val date = div {
            withClass = "quote__header_date"

            findFirst { text }
        }
        val text = div {
            withClass = "quote__body"

            findFirst { ownText }
        }
        return Quote(idString.trimStart('#').toInt(), date, text)
    }

    private fun getQuotesFromPage(url: String) = runBlocking {
        val htmlContent = client.get<String>(url) {
            userAgent(USER_AGENT)
        }

        htmlDocument(htmlContent) {
            article {
                withClass = "quote"

                findAll {
                    map {
                        it.parseQuote()
                    }
                }
            }
        }
    }

    fun getBest() = getQuotesFromPage(BEST_URL)

    fun getLatest() = getQuotesFromPage(LATEST_URL)

    fun getRandom() = getQuotesFromPage(RANDOM_URL)

    companion object {
        const val BEST_URL = "https://bash.im/byrating"
        const val LATEST_URL = "https://bash.im/"
        const val RANDOM_URL = "https://bash.im/random"
        const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0"
    }
}

class QuoteApp : App(QuoteView::class)

fun main(args: Array<String>) {
    launch<QuoteApp>(args)
}
