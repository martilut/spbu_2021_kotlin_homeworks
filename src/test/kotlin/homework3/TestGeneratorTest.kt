package homework3

import com.charleskorn.kaml.Yaml
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File

internal class TestGeneratorTest {

    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of(
                File("src/main/kotlin/homework3/JustSomeClassTest.kt"),
                File("src/test/resources/JustSomeClassYaml.yaml")
                ),
            Arguments.of(
                File("src/main/kotlin/homework3/JustAnotherClassTest.kt"),
                File("src/test/resources/JustAnotherClassYaml.yaml")
            )
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "test{index}, {1}")
    fun generateTestFile(expected: File, input: File) {
        val yamlInfo = Yaml.default.decodeFromString(TestClass.serializer(), input.readText())
        val testFile = TestGenerator(yamlInfo).generateFile()
        assertEquals(expected.readText().replace("\r\n", "\n"), testFile.toString())
    }
}