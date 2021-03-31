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
                File(TestClass::class.java.getResource("correctData/JustSomeClassTest.kt").path),
                File(TestClass::class.java.getResource("correctData/JustSomeClassYaml.yaml").path)
                ),
            Arguments.of(
                File(TestClass::class.java.getResource("correctData/JustAnotherClassTest.kt").path),
                File(TestClass::class.java.getResource("correctData/JustAnotherClassYaml.yaml").path)
            )
        )
        @JvmStatic
        fun incorrectData(): List<Arguments> = listOf(
            Arguments.of(File(TestClass::class.java.getResource("incorrectData/NoClassNameConfig.yaml").path)),
            Arguments.of(File(TestClass::class.java.getResource("incorrectData/NoFunctionsConfig.yaml").path)),
            Arguments.of(File(TestClass::class.java.getResource("incorrectData/NoPackageConfig.yaml").path))
        )
    }

    @MethodSource("correctData")
    @ParameterizedTest(name = "test{index}, {1}")
    fun generateCorrectTestFile(expected: File, input: File) {
        val yamlInfo = Yaml.default.decodeFromString(TestClass.serializer(), input.readText())
        val testFile = TestGenerator(yamlInfo).generateFile()
        assertEquals(expected.readText().replace("\r\n", "\n"), testFile.toString())
    }

    @MethodSource("incorrectData")
    @ParameterizedTest(name = "test{index}, {1}")
    fun generateIncorrectTestFile(input: File) {
        assertThrows(MissingRequiredPropertyException::class.java) {
            val yamlInfo = Yaml.default.decodeFromString(TestClass.serializer(), input.readText())
            TestGenerator(yamlInfo).generateFile()
        }
    }
}
