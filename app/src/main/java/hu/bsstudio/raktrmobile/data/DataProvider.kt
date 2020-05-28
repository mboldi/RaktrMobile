package hu.bsstudio.raktrmobile.data

import android.util.Log
import hu.bsstudio.raktrmobile.model.*
import hu.bsstudio.raktrmobile.network.interactor.DeviceInteractor

class DataProvider {

    private val deviceInteractor =
        DeviceInteractor()

    fun getDevices(
        onSuccess: (List<Device>) -> Unit
    ) {
        deviceInteractor.getDevices(onSuccess, { Log.e("network","couldn't get devices")})
    }

    fun getCompositeItems(): List<CompositeItem> {
        val loc110 = Location(2L, "110")

        val devices: MutableList<Device> = mutableListOf()
        devices += Device(
            1L,
            "BSS320-1",
            "B-1111",
            Category(1L, "Videó"),
            Location(3L, "110"),
            "Sony",
            1,
            "11223344",
            DeviceStatus.GOOD,
            "PMW-320",
            4500000,
            8000
        )
        devices += Device(
            2L,
            "EX3",
            "B-1121",
            Category(1L, "Videó"),
            Location(2L, "Páncél"),
            "Sony",
            1,
            "11223344",
            DeviceStatus.GOOD,
            "PMW-EX3",
            2500000,
            4500
        )

        val items = listOf<CompositeItem>(
            CompositeItem("B-WLRack-01", 1L, "Wireless rack", devices, loc110),
            CompositeItem("B-kozvszett-01", 2L, "Közvetítőszett", devices, loc110)
        )

        return items
    }

    fun getRents(): List<Rent> {
        val rentItems: MutableList<RentItem> = mutableListOf()

        rentItems += RentItem(
            3L,
            BackStatus.OUT,
            2,
            Device(
                1L,
                "BSS320-1",
                "B-1111",
                Category(1L, "Videó"),
                Location(3L, "110"),
                "Sony",
                1,
                "11223344",
                DeviceStatus.GOOD,
                "PMW-320",
                4500000,
                8000
            )
        )

        rentItems += RentItem(
            2L,
            BackStatus.BACK,
            1,
            Device(
                2L,
                "EX3",
                "B-1121",
                Category(1L, "Videó"),
                Location(2L, "Páncél"),
                "Sony",
                1,
                "11223344",
                DeviceStatus.GOOD,
                "PMW-EX3",
                2500000,
                4500
            )
        )

        val rentItems2: MutableList<RentItem> = mutableListOf()

        rentItems2 += RentItem(
            1L,
            BackStatus.OUT,
            1,
            Device(
                2L,
                "EX3",
                "B-1121",
                Category(1L, "Videó"),
                Location(2L, "Páncél"),
                "Sony",
                1,
                "11223344",
                DeviceStatus.GOOD,
                "PMW-EX3",
                2500000,
                4500
            )
        )

        val devices: MutableList<Device> = mutableListOf()
        devices += Device(
            1L,
            "BSS320-1",
            "B-1111",
            Category(1L, "Videó"),
            Location(3L, "110"),
            "Sony",
            1,
            "11223344",
            DeviceStatus.GOOD,
            "PMW-320",
            4500000,
            8000
        )
        devices += Device(
            2L,
            "EX3",
            "B-1121",
            Category(1L, "Videó"),
            Location(2L, "Páncél"),
            "Sony",
            1,
            "11223344",
            DeviceStatus.GOOD,
            "PMW-EX3",
            2500000,
            4500
        )

        rentItems2 += RentItem(
            5L,
            BackStatus.OUT,
            1,
            CompositeItem("B-WLRack-01", 1L, "Wireless rack", devices, Location(2L, "Páncél"))
        )

        val rents = mutableListOf<Rent>()
        rents += (Rent(
            "2020.02.27.",
            "TEDxRajk",
            rentItems2,
            "2020.02.28.",
            1,
            "Benedek",
            "Benedek",
            "2020.02.26."
        ))
        rents += (Rent(
            "",
            "Régi valami",
            rentItems,
            "2019.04.30.",
            1,
            "ifjBoldi",
            "idBoldi",
            "2019.04.28."
        ))
        rents += (Rent(
            "",
            "Simonyi konferencia",
            rentItems,
            "2020.04.30.",
            1,
            "ifjBoldi",
            "idBoldi",
            "2020.04.28."
        ))

        return rents
    }
}