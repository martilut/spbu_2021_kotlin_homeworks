package homework3

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.FileSpec

class TestGenerator(private val testClass: TestClass) {

    private fun generateFunction(function: TestFunction) =
        FunSpec.builder(function.name)
            .addAnnotation(ClassName("org.junit.jupiter.api", "Test"))
            .build()

    private fun generateClass(className: String, functions: List<TestFunction>) =
        TypeSpec.classBuilder(className)
            .addModifiers(KModifier.INTERNAL)
            .addFunctions(functions.map {generateFunction(it)})
            .build()

    fun generateFile() =
        FileSpec.builder(testClass.packageName, testClass.className + "Test")
            .addType(generateClass(testClass.className, testClass.functions))
            .build()
}
