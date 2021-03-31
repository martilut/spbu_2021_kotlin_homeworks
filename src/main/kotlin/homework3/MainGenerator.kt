package homework3

import com.charleskorn.kaml.Yaml
import util.scanLine
import java.io.File

fun main() {
    val configPath = scanLine("Enter the path to yaml config: ")
    val savePath = scanLine("Enter the path to save file: ")
    val yamlText = File(configPath).readText()
    val yamlInfo = Yaml.default.decodeFromString(TestClass.serializer(), yamlText)
    TestGenerator(yamlInfo).generateFile().writeTo(File(savePath))
}
