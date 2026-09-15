/**
 * Merepresentasikan sebuah pesanan (order) yang menghubungkan [Customer], [Driver],
 * dan kendaraan yang dipakai.
 *
 * Status pesanan kini direpresentasikan menggunakan sealed class [OrderStatus]
 * (menggantikan `_status: String` pada versi sebelumnya) sehingga status bersifat
 * type-safe dan dapat ditangani secara ekshaustif menggunakan `when`. Total tarif
 * ([_totalFare]) tetap dienkapsulasi dengan modifier `private`.
 *
 * @property id Identitas unik pesanan.
 * @property customer Pelanggan yang membuat pesanan.
 * @property driver Pengemudi yang melayani pesanan.
 * @property pickupLocation Lokasi penjemputan.
 * @property destination Lokasi tujuan.
 * @property distanceKm Jarak tempuh perjalanan dalam kilometer.
 */
class Order(
    val id: String,
    val customer: Customer,
    val driver: Driver,
    val pickupLocation: String,
    val destination: String,
    val distanceKm: Double
) {

    /** Status pesanan saat ini, bertipe [OrderStatus]. Publik namun tetap type-safe. */
    var status: OrderStatus = OrderStatus.Waiting
        private set

    /** Total tarif pesanan. Dihitung otomatis saat objek dibuat, bersifat privat. */
    private var _totalFare: Double = 0.0

    init {
        // Total tarif dihitung berdasarkan kendaraan driver (polymorphic calculateFare) dan jarak tempuh.
        _totalFare = driver.vehicle.calculateFare(distanceKm)
    }

    /**
     * Mengambil total tarif pesanan yang telah dihitung.
     * @return Total tarif dalam Rupiah.
     */
    fun getTotalFare(): Double = _totalFare

    /**
     * Memulai perjalanan. Hanya berhasil jika status saat ini adalah [OrderStatus.Waiting].
     * @return `true` jika berhasil diubah menjadi [OrderStatus.OnGoing], `false` jika gagal.
     */
    fun startTrip(): Boolean {
        if (status is OrderStatus.Waiting) {
            status = OrderStatus.OnGoing
            return true
        }
        return false
    }

    /**
     * Menyelesaikan perjalanan. Hanya berhasil jika status saat ini adalah [OrderStatus.OnGoing].
     * @return `true` jika berhasil diubah menjadi [OrderStatus.Completed], `false` jika gagal.
     */
    fun completeTrip(): Boolean {
        if (status is OrderStatus.OnGoing) {
            status = OrderStatus.Completed
            return true
        }
        return false
    }

    /**
     * Membatalkan pesanan. Tidak dapat dibatalkan jika pesanan sudah [OrderStatus.Completed].
     *
     * @param reason Alasan pembatalan pesanan.
     * @return `true` jika berhasil dibatalkan, `false` jika gagal (karena sudah selesai).
     */
    fun cancelTrip(reason: String): Boolean {
        if (status !is OrderStatus.Completed) {
            status = OrderStatus.Cancelled(reason)
            return true
        }
        return false
    }

    /**
     * Mencetak seluruh detail pesanan: ID, pelanggan, driver, lokasi, jarak, total, dan status.
     */
    fun displayOrder() {
        println("=== Detail Pesanan ===")
        println("ID Pesanan     : $id")
        println("Pelanggan      : ${customer.name}")
        println("Driver         : ${driver.name}")
        println("Dari           : $pickupLocation")
        println("Tujuan         : $destination")
        println("Jarak          : $distanceKm km")
        println("Total Tarif    : Rp${"%,.2f".format(_totalFare)}")
        println("Status         : ${status.display()}")
    }
}
