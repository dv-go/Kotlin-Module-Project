class ArchiveList (
    override val list: MutableList<Archive> = mutableListOf()
): MenuType<Archive> {

    override val typeString: String
        get() = "\nСписок архивов:\n1. Создать архив"

    override val typeName: String
        get() = "Список архивов"

    override val name: String
        get() = TODO("Not yet implemented")
}