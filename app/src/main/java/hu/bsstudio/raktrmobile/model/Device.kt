package hu.bsstudio.raktrmobile.model

import java.io.Serializable

data class Device(
    override var id: Long,
    override var name: String,
    override var barcode: String,
    var category: Category,
    var location: Location,
    var maker: String,
    var quantity: Int,
    var serial: String,
    var status: DeviceStatus,
    var type: String,
    var value: Int,
    var weight: Int
) : Scannable(), Serializable