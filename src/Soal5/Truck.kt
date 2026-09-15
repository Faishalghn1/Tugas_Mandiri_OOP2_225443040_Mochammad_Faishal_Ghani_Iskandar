/**
 * Merepresentasikan kendaraan jenis truk, mewarisi [Vehicle].
 *
 * Truk memiliki tarif paling mahal di antara jenis kendaraan lainnya,
 * serta properti tambahan berupa kapasitas muatan dan jumlah sumbu roda.
 *
 * @property loadCapacity Kapasitas muatan truk dalam ton.
 * @property numberOfAxles Jumlah sumbu (axle) roda truk.
 */
class Truck(
    plateNumber: String,
    brand: String,
    model: String,
    year: Int,
    val loadCapacity: Double,
    val numberOfAxles: Int,
    isAvailable: Boolean = true
) : Vehicle(plateNumber, brand, model, year, isAvailable) {

    /**
     * Mengembalikan jenis kendaraan sebagai `"Truk"`.
     */
    override fun getType(): String {
        return "Truk"
    }

    /**
     * Menghitung tarif khusus truk: `10000 + (distanceKm * 3500)`.
     *
     * @param distanceKm Jarak tempuh dalam kilometer.
     * @return Total tarif dalam Rupiah.
     */
    override fun calculateFare(distanceKm: Double): Double {
        return 10000.0 + (distanceKm * 3500.0)
    }

    /**
     * Mencetak info dasar kendaraan (memanggil [Vehicle.displayInfo] lewat `super`)
     * lalu menambahkan informasi khusus truk: kapasitas muatan dan jumlah sumbu.
     */
    override fun displayInfo() {
        super.displayInfo()
        println("Kapasitas Muatan : $loadCapacity ton")
        println("Jumlah Sumbu     : $numberOfAxles")
    }
}
