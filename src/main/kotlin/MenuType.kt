interface MenuType<T> {
    val typeString: String
    val typeName: String
    val name: String
    val list: MutableList<T>
}