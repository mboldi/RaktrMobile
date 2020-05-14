package hu.bsstudio.raktrmobile.model

data class Rent(
    var actBackDate: String,
    var destination: String,
    var devices: MutableList<Device>,
    var expBackDate: String,
    var id: Long,
    var issuer: String,
    var renter: String,
    var outDate: String
)