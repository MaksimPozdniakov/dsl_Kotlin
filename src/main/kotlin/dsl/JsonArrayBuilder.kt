package dsl
class JsonArrayBuilder {
    private val jsonArray = mutableListOf<Any?>()

    fun add(value: Any?) {
        jsonArray.add(value)
    }

    override fun toString(): String =
        jsonArray.joinToString(",", "[", "]") { renderJsonElement(it) }

    private fun renderJsonElement(value: Any?): String = when (value) {
        is String -> "\"$value\""
        is Number, is Boolean -> value.toString()
        is JsonObjectBuilder -> value.toString()
        is JsonArrayBuilder -> value.toString()
        null -> "null"
        else -> throw IllegalArgumentException("Unsupported JSON element type: ${value.javaClass.kotlin}")
    }
}