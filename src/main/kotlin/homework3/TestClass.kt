package homework3

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TestFunction(
    val name: String
)

@Serializable
data class TestClass(

    @SerialName("package name")
    val packageName: String,

    @SerialName("class name")
    val className: String,

    @SerialName("functions")
    val functions: List<TestFunction>
)
