import com.google.gson.GsonBuilder
import gsonAPI.Person
import java.io.File

// Функция не нуждается в изменении, т.к. Gson позаботится о форматировании
fun main() {
    val persons = mutableListOf<Person>()
    val phones: MutableList<String?> = mutableListOf("89267157342", "89267157342", "89267157342")
    val emails: MutableList<String?> = mutableListOf("z@z.ru", "z@z.ru", "z@z.ru")
    persons.add(Person("Maksim", phones, emails))
    persons.add(Person("Maksim", phones, emails))

    // Создаем экземпляр Gson с включенным красивым выводом (pretty printing)
    val gson = GsonBuilder().setPrettyPrinting().create()

    // Сериализация списка persons с красивым выводом
    val prettyJsonString = gson.toJson(mapOf("persons" to persons))

    // Записать отформатированную JSON строку в файл
    val pathToFile = "C:\\Users\\PMPav\\OneDrive\\Рабочий стол\\Third block of study\\Seminars\\zzzzzzz\\Test\\src\\main\\kotlin\\test2\\output.json"
    File(pathToFile).writeText(prettyJsonString)

    // Вывод информации о записи файла
    println("Pretty JSON content written to output.json:")
    // println(prettyJsonString) // если нужно вывести в консоль
}
