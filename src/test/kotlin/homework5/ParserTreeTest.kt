package homework5

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File

internal class ParserTreeTest {

    companion object {
        @JvmStatic
        fun expressionsToCalculate(): List<Arguments> = listOf(
            Arguments.of("(+ 200 3)", 203),
            Arguments.of("(- 70 40)", 30),
            Arguments.of("(/ 144 12)", 12),
            Arguments.of("(* 100 1423)", 142300),
            Arguments.of("(/ (* (+ 2 2) 3)) 4)", 3),
            Arguments.of("(* (+ (/ 6 2) (- 3 4)) 2)", 4),
        )

        @JvmStatic
        fun expressionsToPrint(): List<Arguments> = listOf(
            Arguments.of("(+ 200 3)", "1"),
            Arguments.of("(- 70 40)", "2"),
            Arguments.of("(/ 144 12)", "3"),
            Arguments.of("(* 100 1423)", "4"),
            Arguments.of("(/ (* (+ 2 2) 3)) 4)", "5"),
            Arguments.of("(* (+ (/ 6 2) (- 3 4)) 2)", "6"),
        )
    }

    @MethodSource("expressionsToCalculate")
    @ParameterizedTest(name = "TestResults")
    fun getResult(expression: String, answer: Int) {
        val tree = ParserTree(parseExpression(expression))
        assertEquals(answer, tree.getResult())
    }

    private fun listToString(list: MutableList<String>): String {
        list.removeLast()
        var text = ""
        for (element in list) {
            text += element
        }
        return text
    }

    @MethodSource("expressionsToPrint")
    @ParameterizedTest(name = "OutputResults")
    fun outputTree(expression: String, fileNumber: String) {
        val expectedOutput = File(javaClass.getResource("testParser$fileNumber.txt").path)
            .readText()
            .replace("\r\n", "\n")
        val tree = ParserTree(parseExpression(expression))
        assertEquals(expectedOutput, tree.getOutputTree())
    }
}