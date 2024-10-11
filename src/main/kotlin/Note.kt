class Note(
    override val name: String,
    val content: String
) : MenuType<Nothing> {

    override val list: MutableList<Nothing>
        get() = mutableListOf()

    override val typeString: String
        get() = "Просмотр заметки: \"$name\"\nСодержимое: \"$content\""

    override val typeName: String
        get() = "Заметка"
}
