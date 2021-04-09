package homework.task3.storage

import homework.task3.actions.Action
import homework.task3.actions.InsertToEnd
import homework.task3.actions.InsertToStart
import homework.task3.actions.MoveElement
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.io.File

/**
 * Main class for operations with actions
 * @property elements contains the current list of elements
 * @property performedActions contains the list of actions performed to the elements
 */
class PerformedCommandStorage<T> {
    val elements = mutableListOf<T>()
    val performedActions = mutableListOf<Action<T>>()

    fun makeAction(action: Action<T>) {
        action.makeAction(elements)
        performedActions.add(action)
    }
    fun cancelLastAction() {
        val action: Action<T> = performedActions.last()
        action.cancelAction(elements)
        performedActions.removeLast()
    }

    companion object IntSerialize {
        private val module = SerializersModule {
            polymorphic(Any::class) {
                subclass(IntAsObjectSerializer)
            }
            polymorphic(Action::class) {
                subclass(InsertToStart.serializer(PolymorphicSerializer(Any::class)))
                subclass(InsertToEnd.serializer(PolymorphicSerializer(Any::class)))
                subclass(MoveElement.serializer(PolymorphicSerializer(Any::class)))
            }
        }

        private val format = Json { serializersModule = module }

        private fun makeAllActions(actionList: MutableList<Action<Int>>, elements: MutableList<Int>) {
            for (action in actionList) {
                action.makeAction(elements)
            }
        }

        fun PerformedCommandStorage<Int>.loadFromJson(jsonFile: File) {
            val jsonText = jsonFile.readText()
            val actionList = format.decodeFromString<MutableList<Action<Int>>>(jsonText)
            makeAllActions(actionList, this.elements)
        }

        fun PerformedCommandStorage<Int>.saveToJson(jsonFile: File) {
            val jsonText = format.encodeToString(this.performedActions)
            jsonFile.writeText(jsonText)
        }
    }
}
