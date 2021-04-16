package homework5

interface ParserTreeNode {
    val isEmpty: Boolean
    val isCounted: Boolean
    fun calculate(): Int
    fun getOperator(): String?
    fun outputValue(height: Int): String
}

class OperandNode(private val value: Int, private val isEmptyPrivate: Boolean = false) : ParserTreeNode {
    override val isEmpty: Boolean
        get() = isEmptyPrivate
    override val isCounted: Boolean
        get() = true
    override fun calculate(): Int = value
    override fun getOperator(): String? = null
    override fun outputValue(height: Int): String =
        ".".repeat(height) + this.value.toString() + "\n"
}

class OperatorNode(private val operator: String?) : ParserTreeNode {
    override val isEmpty: Boolean
        get() = false
    private var leftChild: ParserTreeNode = OperandNode(0, true)
    private var rightChild: ParserTreeNode = OperandNode(0, true)

    private var _isCounted = false
    override val isCounted: Boolean
        get() = _isCounted

    fun changeIsCounted() {
        _isCounted = true
    }

    fun changeChildren(newLeft: ParserTreeNode, newRight: ParserTreeNode) {
        leftChild = newLeft
        rightChild = newRight
    }

    override fun calculate(): Int {
        if (leftChild.isEmpty || rightChild.isEmpty) {
            throw IllegalArgumentException("Your expression is incorrect")
        }
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

    override fun getOperator(): String? = operator

    override fun outputValue(height: Int): String {
        if (leftChild.isEmpty || rightChild.isEmpty) {
            throw IllegalArgumentException("Your expression is incorrect")
        }
        return ".".repeat(height) + this.operator + "\n" +
                this.leftChild.outputValue(height * 2) +
                this.rightChild.outputValue(height * 2)
    }
}

class TreeBuilder(expression: List<String>) {
    val treeRoot: ParserTreeNode = getParseTreeRoot(expression)

    private fun addNewNode(lexeme: String, list: MutableList<ParserTreeNode>) {
        val value = lexeme.toIntOrNull()
        when {
            value != null -> list.add(OperandNode(value))
            operationContains(lexeme) != null -> list.add(OperatorNode(lexeme))
            else -> throw IllegalArgumentException("Your operators or operands are incorrect")
        }
    }

    private fun updateNodeList(list: MutableList<ParserTreeNode>) {
        val secondNode = list.removeLast()
        val firstNode = list.removeLast()
        val parentNode = list.removeLast()
        val nodeToAdd = OperatorNode(parentNode.getOperator())
        nodeToAdd.changeChildren(firstNode, secondNode)
        try {
            nodeToAdd.calculate()
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(e.message)
        }
        nodeToAdd.changeIsCounted()
        list.add(nodeToAdd)
    }

    private fun updateNode(node: ParserTreeNode): Boolean = node.isCounted && !node.isEmpty

    private fun isReadyToUpdate(list: MutableList<ParserTreeNode>): Boolean {
        val size = list.size
        return size > 2 && updateNode(list[size - 1]) && updateNode(list[size - 2])
    }

    private fun getParseTreeRoot(expression: List<String>): ParserTreeNode {
        val nodeList = mutableListOf<ParserTreeNode>()
        for (lexeme in expression) {
            addNewNode(lexeme, nodeList)
            while (isReadyToUpdate(nodeList)) {
                updateNodeList(nodeList)
            }
        }
        if (nodeList.size != 1) {
            throw IllegalArgumentException("Your expression is incorrect")
        }
        return nodeList[0]
    }
}

class ParserTree(expression: List<String>) {
    private var root = TreeBuilder(expression).treeRoot

    fun getResult(): Int = root.calculate()

    fun getOutputTree(): String = root.outputValue(1)
}
