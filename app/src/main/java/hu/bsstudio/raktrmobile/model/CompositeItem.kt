package hu.bsstudio.raktrmobile.model

data class CompositeItem(
    override var barcode: String,
    override var id: Long,
    override var name: String,
    var devices: List<Device>,
    var location: Location
) : Scannable()