package hu.bsstudio.raktrmobile.model

data class RentItem(
    var id: Long,
    var backStatus: BackStatus,
    var outQuantity: Int,
    var scannable: Scannable
)