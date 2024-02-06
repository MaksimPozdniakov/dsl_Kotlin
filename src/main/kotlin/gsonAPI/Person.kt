package gsonAPI

data class Person(
    val name: String,
    val phones: MutableList<String?>,
    val emails: MutableList<String?>
) {

    // Новая реализация
    // Вторичный конструктор, принимает флаг, указывающий тип контактных данных
    constructor(name: String, contact: MutableList<String?>, contactType: String) : this(
        name,
        phones = if (contactType == "phone") contact else mutableListOf(),
        emails = if (contactType == "email") contact else mutableListOf()
    )

    override fun toString(): String {
        return buildString {
            append(name)

            if (phones.isNotEmpty()) {
                append(", Phone: $phones")
            }
            if (emails.isNotEmpty()) {
                append(", Email: $emails")
            }
        }
    }
}