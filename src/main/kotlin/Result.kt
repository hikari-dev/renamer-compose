data class Result(val info: String, val type: Type) {
    enum class Type {
        ERR, INFO
    }
}