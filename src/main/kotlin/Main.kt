fun main() {

    println("\nДобро пожаловать в приложение \"Заметки\"!")
    val userPath = UserPath()

    val archiveList = ArchiveList()

    val firstScreen = MenuScreen(archiveList, userPath)
    userPath.pathItems.add(firstScreen)

    while (true){
        val currentScreen = userPath.pathItems.last()

        currentScreen.printMenu()

        val choice: Int = currentScreen.readMenuSelection()

        when(choice){
            1 -> when(currentScreen.menuTypeInstance){
                is ArchiveList -> {
                    println("\nВведите название нового архива:")
                    val input = currentScreen.readName()
                    archiveList.list.add(Archive(input))
                    println("\nДобавили новый архив с названием ${archiveList.list.last().name}")
                    currentScreen.refreshMenu()
                    continue
                }
                is Archive -> {
                    val currentArchive = currentScreen.menuTypeInstance
                    println("\nВведите название новой заметки:")
                    val noteName = currentScreen.readName()

                    println("\nВведите содержимое новой заметки:")
                    val noteContent = currentScreen.readContent()

                    val newNote = Note(noteName, noteContent)
                    currentArchive.list.add(newNote)

                    println("\nДобавили новую заметку с названием \"${newNote.name}\"\n")

                    currentScreen.refreshMenu()
                    continue
                }

            }
            currentScreen.menuItems.size + 2 -> {
                if (currentScreen.equals(firstScreen)) {
                    println("\nПриложение \"Заметки\" выключается...\n\nВсего доброго!")
                    break
                } else {
                    userPath.pathItems.removeLast()
                }
            }

            else -> {
                val selectedIndex = choice - 2

                if (selectedIndex in currentScreen.menuItems.indices) {
                    val selectedPair = currentScreen.menuItems[selectedIndex]
                    selectedPair.second.invoke()
                } else {
                    println("Неверный выбор, попробуйте снова.")
                }
            }
        }
    }
}