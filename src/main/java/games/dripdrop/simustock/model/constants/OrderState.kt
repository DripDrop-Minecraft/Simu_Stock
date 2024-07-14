package games.dripdrop.simustock.model.constants

enum class OrderState(val state: Int) {
    ABANDONED(-1),
    WAITING_MATCH(0),
    COMPLETED(1)
}