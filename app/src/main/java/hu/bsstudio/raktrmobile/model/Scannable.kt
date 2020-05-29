package hu.bsstudio.raktrmobile.model

abstract class Scannable{
    abstract var id: Long
    abstract var name: String
    abstract var barcode: String
    abstract val objType: String
}