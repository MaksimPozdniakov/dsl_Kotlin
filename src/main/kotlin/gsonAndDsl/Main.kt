package gsonAndDsl

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import java.io.File

// Функция для создания JsonObject с помощью DSL
fun jsonObject(block: JsonObject.() -> Unit): JsonObject = JsonObject().apply(block)

// Функция для создания JsonArray с помощью DSL
fun jsonArray(elements: List<Any?>): JsonArray = JsonArray().apply {
    elements.forEach {
        when (it) {
            is Number -> add(it)
            is String -> add(it)
            is Boolean -> add(it)
            is JsonObject -> add(it)
            is JsonArray -> add(it)
            null -> add(JsonNull.INSTANCE)
            else -> throw IllegalArgumentException("Unsupported type: ${it?.javaClass}")
        }
    }
}

// Функция, сериализующая список объектов Person в JsonArray
fun serializePersons(persons: List<Person>): JsonArray {
    return JsonArray().apply {
        persons.forEach { person ->
            add(jsonObject {
                addProperty("name", person.name)
                add("phones", jsonArray(person.phones))
                add("emails", jsonArray(person.emails))
            })
        }
    }
}

fun main() {
    val gson = GsonBuilder().setPrettyPrinting().create()

    // Создадим список объектов Person
    val persons = listOf(
        Person("Maksim", mutableListOf("89267157342", "89267157342"), mutableListOf("z@z.ru", null)),
        Person("Ivan", mutableListOf("1234567890", "9876543210"), mutableListOf("ivan@example.com", null))
    )

    // Сериализуем список объектов Person в JsonArray
    val personsJson = serializePersons(persons)

    // Сериализуем JsonArray в строку JSON с красивым форматированием
    val prettyJsonString = gson.toJson(personsJson)

    // Запишем отформатированную JSON строку в файл
    val pathToFile = "C:\\Users\\PMPav\\OneDrive\\Рабочий стол\\Third block of study\\Seminars\\zzzzzzz\\Test\\src\\" +
            "main\\kotlin\\gsonAndDsl\\output.json" // Изменим путь, чтобы работало на любой машине
    File(pathToFile).writeText(prettyJsonString)

    println("JSON content written to $pathToFile")
}
