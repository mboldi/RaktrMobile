package hu.bsstudio.raktrmobile.model

import java.io.Serializable

data class RentItem(
    var id: Long,
    var backStatus: BackStatus,
    var outQuantity: Int,
    var scannable: Scannable
) : Serializable