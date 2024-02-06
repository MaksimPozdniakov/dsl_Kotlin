package dsl
// Определяем классы для построения JSON
class JsonObjectBuilder {
    private val jsonObject = mutableMapOf<String, Any?>()

    infix fun String.to(value: Any?) {
        jsonObject[this] = value
    }

    fun jsonObject(build: JsonObjectBuilder.() -> Unit): JsonObjectBuilder =
        JsonObjectBuilder().apply(build)

    fun jsonArray(vararg values: Any?): JsonArrayBuilder =
        JsonArrayBuilder().apply { values.forEach { add(it) } }

    override fun toString(): String =
        jsonObject.map { (key, value) -> "\"$key\":${renderJsonElement(value)}" }
            .joinToString(",", "{", "}")

    private fun renderJsonElement(value: Any?): String = when (value) {
        is String -> "\"$value\""
        is Number, is Boolean -> value.toString()
        is JsonObjectBuilder -> value.toString()
        is JsonArrayBuilder -> value.toString()
        null -> "null"
        else -> throw IllegalArgumentException("Unsupported JSON element type: ${value.javaClass.kotlin}")
    }
}