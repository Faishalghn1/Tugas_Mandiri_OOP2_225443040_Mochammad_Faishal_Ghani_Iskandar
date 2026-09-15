/**
 * Merepresentasikan kendaraan jenis motor, mewarisi [Vehicle].
 *
 * Motor memiliki tarif yang lebih murah daripada tarif dasar kendaraan umum,
 * serta properti tambahan berupa kapasitas mesin dan ketersediaan helm.
 *
 * @property engineCapacity Kapasitas mesin motor dalam cc.
 * @property hasHelmet Menandakan apakah motor dilengkapi helm untuk penumpang.
 */
class Motorcycle(
    plateNumber: String,
    brand: String,
    model: String,
    year: Int,
    val engineCapacity: Int,
    val hasHelmet: Boolean,
    isAvailable: Boolean = true
) : Vehicle(plateNumber, brand, model, year, isAvailable) {

    /**
     * Mengembalikan jenis kendaraan sebagai `"Motor"`.
     */
    override fun getType(): String {
        return "Motor"
    }

    /**
     * Menghitung tarif khusus motor: `3000 + (distanceKm * 1500)`.
     *
     * @param distanceKm Jarak tempuh dalam kilometer.
     * @return Total tarif dalam Rupiah.
     */
    override fun calculateFare(distanceKm: Double): Double {
        return 3000.0 + (distanceKm * 1500.0)
    }

    /**
     * Mencetak info dasar kendaraan (memanggil [Vehicle.displayInfo] lewat `super`)
     * lalu menambahkan informasi khusus motor: kapasitas mesin dan ketersediaan helm.
     */
    override fun displayInfo() {
        super.displayInfo()
        println("Kapasitas Mesin : ${engineCapacity}cc")
        println("Tersedia Helm   : ${if (hasHelmet) "Ya" else "Tidak"}")
    }
}
