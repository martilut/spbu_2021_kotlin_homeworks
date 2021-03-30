package homework3

import com.charleskorn.kaml.Yaml
import java.io.File

fun main() {
    val yamlText = File("src/main/resources/testFile.yaml").readText()
    val yamlInfo = Yaml.default.decodeFromString(TestClass.serializer(), yamlText)
    TestGenerator(yamlInfo).generateFile().writeTo(File("src/main/kotlin/"))
}