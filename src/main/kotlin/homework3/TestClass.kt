package homework3

import kotlinx.serialization.Serializable

@Serializable
data class TestFunction(
    val name: String
)

@Serializable
data class TestClass(
    val packageName: String,
    val className: String,
    val functions: List<TestFunction>
)