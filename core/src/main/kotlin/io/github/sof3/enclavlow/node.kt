package io.github.sof3.enclavlow

/**
 * A node of data flow
 */
sealed class Node


/**
 * A publicly visible node that could appear in the contract
 */
sealed class PublicNode : Node() {
    protected abstract val name: String

    override fun toString() = name
}

/**
 * Data to caller through return path
 */
object ReturnScope : PublicNode() {
    override val name: String
        get() = "<return>"
}

/**
 * Data to caller through throw path
 */
object ThrowScope : PublicNode() {
    override val name: String
        get() = "<throw>"
}

/**
 * Data to static scope
 *
 * Data from static scope are always considered insensitive.
 */
object StaticScope : PublicNode() {
    override val name: String
        get() = "<static>"
}

/**
 * Data from/to `this`
 */
object ThisScope : PublicNode() {
    override val name: String
        get() = "this"
}

/**
 * Data source from a parameter
 */
class ParamNode(private val index: Int) : PublicNode() {
    override val name: String
        get() = "param$index"

    override fun equals(other: Any?) = other is ParamNode && index == other.index

    override fun hashCode() = index.hashCode()
}

/**
 * Data source from a local variable explicitly declared as `@Source`
 */
object ExplicitSource : PublicNode() {
    override val name: String
        get() = "<@Source>"
}

/**
 * Data source from a local variable explicitly declared as `@Sink`
 */
object ExplicitSink : PublicNode() {
    override val name: String
        get() = "<@Sink>"
}

/**
 * A private node only considered within method local analysis
 */
sealed class PrivateNode : Node()

/**
 * Data from/to a variable with jimple name `name`
 */
class LocalVarNode(val name: String) : PrivateNode() {
    init {
        println("Constructed $name")
    }

    override fun toString() = name

    override fun equals(other: Any?) = other is LocalVarNode && name == other.name

    override fun hashCode() = name.hashCode()
}

/**
 * Flows to ControlFlow indicates the current control flow contains data
 */
class ControlNode(val parent: ControlNode?, private val branchId: Int) : PrivateNode() {
    override fun toString(): String {
        val stack = mutableListOf<Int>()
        getStack(stack)
        return "<control ${stack.joinToString(".")}>"
    }

    private fun getStack(list: MutableList<Int>) {
        parent?.getStack(list)
        list.add(branchId)
    }

    override fun equals(other: Any?) = this === other

    override fun hashCode() = System.identityHashCode(this)
}
