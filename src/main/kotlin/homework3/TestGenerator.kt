package homework3

import com.squareup.kotlinpoet.*

class TestGenerator(private val testClass: TestClass) {

    private fun generateFunction(function: TestFunction) =
        FunSpec.builder(function.name)
            .addAnnotation(ClassName("org.junit.jupiter.api", "Test"))
            .build()

    private fun generateClass(className: String, functions: List<TestFunction>) =
        TypeSpec.classBuilder(className)
            .addModifiers(KModifier.INTERNAL)
            .addFunctions(functions.map { generateFunction(it) } )
            .build()

    fun generateFile() =
        FileSpec.builder(testClass.packageName, testClass.className + "Test")
            .addType(generateClass(testClass.className, testClass.functions))
            .build()

    val file: FileSpec
        get() = FileSpec.builder(testClass.packageName, "${testClass.className}Test")
            .addType(generateClass(testClass.className, testClass.functions))
            .build()
}
