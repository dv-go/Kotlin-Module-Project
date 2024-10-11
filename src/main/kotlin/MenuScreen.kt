import java.util.Objects
import java.util.Scanner

class MenuScreen<T>(
    val menuTypeInstance: MenuType<T>,
    private val userPath: UserPath
) {
    val menuItems: MutableList<Pair<String, () -> Unit>> = mutableListOf()

    init {
        initializeMenuItems()
    }

    fun initializeMenuItems() {
        menuItems.clear()

        menuTypeInstance.list.forEach { item ->
            val name = when (item) {
                is Archive -> item.name
                is Note -> item.name
                else -> throw IllegalArgumentException("Неизвестный тип элемента")
            }
            menuItems.add(name to {
                if (item is Archive) {
                    val newScreen = MenuScreen(item, userPath)
                    userPath.pathItems.add(newScreen)
                } else if (item is Note) {
                    val noteScreen = MenuScreen(object : MenuType<Nothing> {
                        override val name: String = item.name
                        override val typeString: String = "\nПросмотр заметки: \"${item.name}\"\n1. Содержимое: \"${item.content}\""
                        override val typeName: String = "Заметка"
                        override val list: MutableList<Nothing> = mutableListOf()
                    }, userPath)

                    noteScreen.menuItems.clear()

                    userPath.pathItems.add(noteScreen)
                    noteScreen.printMenu()
                }
            })
        }
    }


    fun refreshMenu() {
        initializeMenuItems()
    }

    fun printMenu() {
        println(this.menuTypeInstance.typeString)
        this.menuItems.forEachIndexed { index, pair ->
        println("${index + 2}. ${pair.first}")
        }
        println("${this.menuItems.size + 2}. Выход")
    }

    val scanner = Scanner(System.`in`)

    fun readMenuSelection(): Int {
        val menuSize = menuItems.size + 2
        while (true) {
            println("\nВведите номер пункта меню:")
            val input = scanner.nextLine()
            val number = input.toIntOrNull()
            if (number == null) {
                println("\nПожалуйста, введите цифру.")
                continue
            }
            if (number in 1..menuSize) {
                return number
            } else {
                println("\nТакого пункта нет. Пожалуйста, выберите номер от 1 до $menuSize.")
            }
        }
    }

    fun readName(): String {
        while (true) {
            val input = scanner.nextLine().trim()
            if (input.isNotEmpty()) {
                return input
            } else {
                println("Название не может быть пустым. Пожалуйста, введите название заново.")
            }
        }
    }

    fun readContent(): String {
        while (true) {
            println("\nВведите содержимое заметки:")
            val input = scanner.nextLine().trim()
            if (input.isNotEmpty()) {
                return input
            } else {
                println("Содержимое не может быть пустым. Пожалуйста, введите текст заново.")
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MenuScreen<*>) return false

        return menuTypeInstance == other.menuTypeInstance
    }

    override fun hashCode(): Int {
        return Objects.hash(menuTypeInstance)
    }
}