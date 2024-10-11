class Archive(
    override val name: String,
    override val list: MutableList<Note> = mutableListOf()
) : MenuType<Note> {

    override val typeString: String
        get() = "\nСписок заметок в архиве \"$name\":\n1. Создать заметку"

    override val typeName: String
        get() = "Архив"
}
