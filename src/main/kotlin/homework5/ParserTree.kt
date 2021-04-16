package homework5

interface ParserTreeNode {
    fun calculate(): Int
    fun outputValue(height: Int): String
}

class OperandNode(private val value: Int) : ParserTreeNode {
    override fun calculate(): Int = value
    override fun outputValue(height: Int): String =
        ".".repeat(height) + this.value.toString() + "\n"
}

class OperatorNode(
    private val operator: String?,
    private var leftChild: ParserTreeNode,
    private var rightChild: ParserTreeNode
) : ParserTreeNode {
    override fun calculate(): Int {
        return when (operator) {
            OperationType.PLUS.value -> leftChild.calculate() + rightChild.calculate()
            OperationType.MINUS.value -> leftChild.calculate() - rightChild.calculate()
            OperationType.MULTIPLY.value -> leftChild.calculate() * rightChild.calculate()
            OperationType.DIVIDE.value -> {
                val leftValue = leftChild.calculate()
                val rightValue = rightChild.calculate()
                if (rightValue == 0) {
                    throw IllegalArgumentException("Division by zero")
                } else {
                    leftValue / rightValue
                }
            }
            else -> throw IllegalArgumentException("Your expression is incorrect")
        }
    }

    override fun outputValue(height: Int): String {
        return ".".repeat(height) + this.operator + "\n" +
                this.leftChild.outputValue(height * 2) +
                this.rightChild.outputValue(height * 2)
    }
}

class ParserTree(inputExpression: List<String>) {
    private val root = parseExpression(inputExpression).treeRoot

    fun getResult(): Int = root.calculate()
    fun getOutputTree(): String = root.outputValue(1)

    data class ParserData(val treeRoot: ParserTreeNode, val currentList: List<String>)

    private fun parseExpression(expression: List<String>): ParserData {
        return when {
            expression.first().toIntOrNull() != null -> {
                ParserData(OperandNode(expression.first().toInt()), expression.drop(1))
            }
            else -> {
                val operator = expression.first()
                if (operationContains(operator) == null) {
                    throw IllegalArgumentException("Your operators or operands are incorrect")
                }
                var currentData = parseExpression(expression.drop(1))
                val left = currentData.treeRoot
                currentData = parseExpression(currentData.currentList)
                val right = currentData.treeRoot
                return ParserData(OperatorNode(operator, left, right), currentData.currentList)
            }
        }
    }
}
