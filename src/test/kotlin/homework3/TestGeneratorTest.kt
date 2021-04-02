package homework3

import com.charleskorn.kaml.MissingRequiredPropertyException
import com.charleskorn.kaml.Yaml
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File

internal class TestGeneratorTest {

    companion object {
        @JvmStatic
        fun correctData(): List<Arguments> = listOf(
            Arguments.of(
                "correctData/JustSomeClassTest.kt",
                "correctData/JustSomeClassYaml.yaml"
                ),
            Arguments.of(
                "correctData/JustAnotherClassTest.kt",
                "correctData/JustAnotherClassYaml.yaml"
                )
        )
        @JvmStatic
        fun incorrectData(): List<Arguments> = listOf(
            Arguments.of("incorrectData/NoClassNameConfig.yaml"),
            Arguments.of("incorrectData/NoFunctionsConfig.yaml"),
            Arguments.of("incorrectData/NoPackageConfig.yaml")
        )
    }

    @MethodSource("correctData")
    @ParameterizedTest(name = "test{index}, {1}")
    fun generateCorrectTestFile(expectedPath: String, inputPath: String) {
        val expectedFile = File(TestClass::class.java.getResource(expectedPath).path)
        val inputFile = File(TestClass::class.java.getResource(inputPath).path)
        val yamlInfo = Yaml.default.decodeFromString(TestClass.serializer(), inputFile.readText())
        val testFile = TestGenerator(yamlInfo).generateFile()
        assertEquals(expectedFile.readText().replace("\r\n", "\n"), testFile.toString())
    }

    @MethodSource("incorrectData")
    @ParameterizedTest(name = "test{index}, {1}")
    fun generateIncorrectTestFile(path: String) {
        assertThrows(MissingRequiredPropertyException::class.java) {
            val inputFile = File(TestClass::class.java.getResource(path).path)
            val yamlInfo = Yaml.default.decodeFromString(TestClass.serializer(), inputFile.readText())
            TestGenerator(yamlInfo).generateFile()
        }
    }
}
