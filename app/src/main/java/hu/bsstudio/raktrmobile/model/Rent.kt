package hu.bsstudio.raktrmobile.model

import java.io.Serializable

data class Rent(
    var actBackDate: String,
    var destination: String,
    var rentItems: MutableList<RentItem>,
    var expBackDate: String,
    var id: Long,
    var issuer: String,
    var renter: String,
    var outDate: String
) : Serializable