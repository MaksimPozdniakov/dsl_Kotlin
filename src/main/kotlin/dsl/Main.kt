package dsl

import java.io.File

// Методы-помощники для создания JSON
fun json(build: JsonObjectBuilder.() -> Unit): JsonObjectBuilder = JsonObjectBuilder().apply(build)
fun jsonArray(vararg values: Any?): JsonArrayBuilder = JsonArrayBuilder().apply { values.forEach { add(it) } }

fun prettyPrintJson(jsonString: String, indentWidth: Int = 4): String {
    val indentChar = ' '
    val indent = indentChar.toString().repeat(indentWidth)
    var level = 0
    var inString = false
    val prettyPrinted = StringBuilder()

    for (char in jsonString) {
        when (char) {
            '{', '[' -> {
                prettyPrinted.append(char)
                if (!inString) {
                    level++
                    prettyPrinted.append("\n").append(indent.repeat(level))
                }
            }
            '}', ']' -> {
                if (!inString) {
                    level--
                    prettyPrinted.append("\n").append(indent.repeat(level))
                }
                prettyPrinted.append(char)
            }
            ',' -> {
                prettyPrinted.append(char)
                if (!inString) prettyPrinted.append("\n").append(indent.repeat(level))
            }
            '\"' -> {
                prettyPrinted.append(char)
                if (jsonString.getOrNull(prettyPrinted.length - 2) != '\\') {
                    // если кавычка не является частью escape последовательности, меняем флаг
                    inString = !inString
                }
            }
            else -> prettyPrinted.append(char)
        }
    }

    return prettyPrinted.toString()
}

// Пример использования DSL для сериализации списка Person в JSON строку и записи в файл
fun main() {
    val persons = mutableListOf<Person>()
    val phones: MutableList<String?> = mutableListOf("89267157342", "89267157342", "89267157342")
    val emails: MutableList<String?> = mutableListOf("z@z.ru", "z@z.ru", "z@z.ru")
    persons.add(Person("Maksim", phones, emails))
    persons.add(Person("Maksim", phones, emails))

    // Создаем JSON строку с использованием DSL
    val jsonString = json {
        "persons" to jsonArray(*persons.map { person ->
            jsonObject {
                "name" to person.name
                "phones" to jsonArray(*person.phones.toTypedArray())
                "emails" to jsonArray(*person.emails.toTypedArray())
            }
        }.toTypedArray())
    }.toString()

    val prettyJsonString = prettyPrintJson(jsonString)

    // Записать отформатированную JSON строку в файл
    File("C:\\Users\\PMPav\\OneDrive\\Рабочий стол\\Third block of study\\Seminars\\zzzzzzz" +
            "\\Test\\src\\main\\kotlin\\output.json").writeText(prettyJsonString)

    // Вывод информации о записи файла
    println("Pretty JSON content written to output.json:")
//    println(prettyJsonString)
}
