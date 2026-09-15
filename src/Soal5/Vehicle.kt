/**
 * Kelas dasar (superclass) yang merepresentasikan kendaraan dalam sistem
 * transportasi online.
 *
 * Kelas ini dibuka (`open`) agar dapat diwarisi oleh subclass seperti
 * [Car], [Motorcycle], dan [Truck], yang masing-masing memiliki karakteristik
 * dan tarif yang berbeda.
 *
 * @property plateNumber Nomor plat kendaraan.
 * @property brand Merek kendaraan.
 * @property model Model/tipe kendaraan.
 * @property year Tahun produksi kendaraan.
 * @property isAvailable Status ketersediaan kendaraan. Nilai default adalah `true`.
 */
open class Vehicle(
    val plateNumber: String,
    val brand: String,
    val model: String,
    val year: Int,
    var isAvailable: Boolean = true
) {

    /**
     * Mengembalikan jenis kendaraan secara umum.
     *
     * Metode ini bersifat `open` sehingga dapat di-override oleh subclass
     * untuk mengembalikan jenis yang lebih spesifik.
     *
     * @return String jenis kendaraan, default `"Kendaraan Umum"`.
     */
    open fun getType(): String {
        return "Kendaraan Umum"
    }

    /**
     * Mencetak informasi dasar kendaraan ke konsol.
     *
     * Bersifat `open` agar dapat di-override dan diperluas oleh subclass
     * menggunakan `super.displayInfo()`.
     */
    open fun displayInfo() {
        println("=== Info Kendaraan (${getType()}) ===")
        println("Plat Nomor   : $plateNumber")
        println("Merek/Model  : $brand $model")
        println("Tahun        : $year")
        println("Tersedia     : ${if (isAvailable) "Ya" else "Tidak"}")
    }

    /**
     * Menghitung tarif dasar perjalanan berdasarkan jarak tempuh.
     *
     * Rumus tarif dasar: `5000 + (distanceKm * 2000)`. Bersifat `open` agar
     * subclass dapat memberikan rumus tarif yang berbeda.
     *
     * @param distanceKm Jarak tempuh dalam kilometer.
     * @return Total tarif dalam Rupiah.
     */
    open fun calculateFare(distanceKm: Double): Double {
        return 5000.0 + (distanceKm * 2000.0)
    }
}
