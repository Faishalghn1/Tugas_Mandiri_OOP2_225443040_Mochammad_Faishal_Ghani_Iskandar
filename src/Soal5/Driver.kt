/**
 * Merepresentasikan seorang pengemudi (driver) dalam sistem transportasi online.
 *
 * Setiap driver memiliki satu kendaraan yang digunakan untuk melayani pesanan
 * serta status aktif yang menentukan apakah driver tersebut sedang siap bekerja.
 *
 * @property id Identitas unik driver.
 * @property name Nama lengkap driver.
 * @property phone Nomor telepon driver.
 * @property vehicle Kendaraan yang dimiliki/dikendarai oleh driver ini.
 * @property isActive Status aktif driver. Nilai default adalah `true`.
 */
class Driver(
    val id: String,
    val name: String,
    val phone: String,
    val vehicle: Vehicle,
    var isActive: Boolean = true
) {

    /**
     * Mencetak seluruh informasi driver, termasuk informasi kendaraannya.
     */
    fun displayInfo() {
        println("=== Info Driver ===")
        println("ID       : $id")
        println("Nama     : $name")
        println("Telepon  : $phone")
        println("Status   : ${if (isActive) "Aktif" else "Tidak Aktif"}")
        vehicle.displayInfo()
    }

    /**
     * Memeriksa apakah driver dapat menerima pesanan baru.
     *
     * Sebuah pesanan hanya dapat diterima jika driver dalam keadaan aktif
     * DAN kendaraannya tersedia.
     *
     * @return `true` jika pesanan dapat diterima, `false` jika tidak.
     */
    fun acceptOrder(): Boolean {
        return isActive && vehicle.isAvailable
    }
}
