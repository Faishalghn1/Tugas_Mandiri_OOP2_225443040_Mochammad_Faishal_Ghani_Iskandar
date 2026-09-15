/**
 * Merepresentasikan kendaraan jenis mobil, mewarisi [Vehicle].
 *
 * Mobil memiliki tarif yang lebih mahal daripada tarif dasar kendaraan umum,
 * serta properti tambahan berupa jenis bahan bakar dan jumlah pintu.
 *
 * @property fuelType Jenis bahan bakar mobil ("Bensin", "Diesel", atau "Listrik").
 * @property numberOfDoors Jumlah pintu mobil.
 */
class Car(
    plateNumber: String,
    brand: String,
    model: String,
    year: Int,
    val fuelType: String,
    val numberOfDoors: Int,
    isAvailable: Boolean = true
) : Vehicle(plateNumber, brand, model, year, isAvailable) {

    /**
     * Mengembalikan jenis kendaraan sebagai `"Mobil"`.
     */
    override fun getType(): String {
        return "Mobil"
    }

    /**
     * Menghitung tarif khusus mobil: `8000 + (distanceKm * 2500)`.
     *
     * @param distanceKm Jarak tempuh dalam kilometer.
     * @return Total tarif dalam Rupiah.
     */
    override fun calculateFare(distanceKm: Double): Double {
        return 8000.0 + (distanceKm * 2500.0)
    }

    /**
     * Mencetak info dasar kendaraan (memanggil [Vehicle.displayInfo] lewat `super`)
     * lalu menambahkan informasi khusus mobil: jenis bahan bakar dan jumlah pintu.
     */
    override fun displayInfo() {
        super.displayInfo()
        println("Bahan Bakar  : $fuelType")
        println("Jumlah Pintu : $numberOfDoors")
    }
}
