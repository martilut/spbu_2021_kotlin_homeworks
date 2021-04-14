package homework5

class ExpressionTreeNode(private var value: Int = 0) {
    private var isCounted: Boolean = false
    private var operator: Operation? = null
    private var leftChild: ExpressionTreeNode? = null
    private var rightChild: ExpressionTreeNode? = null

    private fun performOperation(leftValue: Int, rightValue: Int): Int {
        return when(operator) {
            Operation.PLUS -> leftValue + rightValue
            Operation.MINUS -> leftValue - rightValue
            Operation.MULTIPLY -> leftValue * rightValue
            Operation.DIVIDE -> {
                if (rightValue == 0) {
                    throw IllegalArgumentException("Division by zero")
                } else {
                    leftValue / rightValue
                }
            }
            else -> throw IllegalArgumentException("Your expression is incorrect")
        }
    }

    private fun addNewNode(lexeme: String, list: MutableList<ExpressionTreeNode>) {
        val value = lexeme.toIntOrNull()
        val operation = operationContains(lexeme)
        when {
            value != null -> {
                val parserNode = ExpressionTreeNode(value)
                parserNode.isCounted = true
                list.add(parserNode)
            }
            operation != null -> {
                val parserNode = ExpressionTreeNode()
                parserNode.operator = operation
                list.add(parserNode)
            }
            else -> throw IllegalArgumentException("Your operators or operands are incorrect")
        }
    }

    private fun updateNodeList(list: MutableList<ExpressionTreeNode>) {
        val secondNode = list.removeLast()
        val firstNode = list.removeLast()
        val parentNode = list.removeLast()
        try {
            parentNode.performOperation(firstNode.value, secondNode.value)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(e.message)
        }
        parentNode.leftChild = firstNode
        parentNode.rightChild = secondNode
        parentNode.isCounted = true
        list.add(parentNode)
    }

    private fun isReadyToUpdate(list: MutableList<ExpressionTreeNode>): Boolean {
        val size = list.size
        return size > 2 && list[size - 1].isCounted
                && list[size - 2].isCounted
                && !(list[size - 3].isCounted)
    }

    fun getParseTreeRoot(expression: List<String>): ExpressionTreeNode {
        val nodeList = mutableListOf<ExpressionTreeNode>()
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

    fun getResultRecursive(): Int {
        val leftValue = this.leftChild?.getResultRecursive()
        val rightValue = this.rightChild?.getResultRecursive()
        return when (this.operator) {
            null -> this.value
            else -> performOperation(leftValue!!, rightValue!!)
        }
    }

    fun outputTreeRecursive(height: Int, point: String, text: MutableList<String>): MutableList<String> {
        if (this.operator != null) {
            text.add(point.repeat(height))
            text.add(this.operator!!.value)
            text.add("\n")
            this.leftChild?.outputTreeRecursive(height * 2, point, text)
            this.rightChild?.outputTreeRecursive(height * 2, point, text)
        } else {
            text.add(point.repeat(height))
            text.add(this.value.toString())
            text.add("\n")
        }
        return text
    }
}

class ExpressionTree {
    private var root: ExpressionTreeNode = ExpressionTreeNode()

    fun buildParseTree(expression: List<String>) {
        root = root.getParseTreeRoot(expression)
    }

    fun getResult(): Int {
        return root.getResultRecursive()
    }

    fun outputTree(): MutableList<String> {
        val text = mutableListOf<String>()
        return root.outputTreeRecursive(1, ".", text)
    }
}
