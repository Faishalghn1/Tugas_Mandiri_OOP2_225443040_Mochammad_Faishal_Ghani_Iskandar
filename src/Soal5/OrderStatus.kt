/**
 * Merepresentasikan status sebuah [Order] sepanjang siklus hidupnya.
 *
 * Dideklarasikan sebagai `sealed class` (menggantikan representasi `String`
 * yang digunakan sebelumnya) agar setiap status memiliki tipe yang jelas dan
 * kompiler dapat memastikan penanganan `when` bersifat ekshaustif. [Cancelled]
 * dibuat sebagai `data class` karena membawa data tambahan berupa alasan
 * pembatalan, sedangkan status lain yang tidak membawa data cukup dibuat
 * sebagai `object` (singleton).
 */
sealed class OrderStatus {

    /**
     * Mengembalikan representasi teks dari status ini.
     * @return String deskripsi status.
     */
    abstract fun display(): String

    /**
     * Menentukan apakah status ini merupakan status akhir (final) dari sebuah
     * pesanan, yaitu status yang tidak dapat berubah lagi.
     *
     * @return `true` jika status ini adalah [Completed] atau [Cancelled], selain itu `false`.
     */
    fun isFinal(): Boolean {
        return this is Completed || this is Cancelled
    }

    /** Status pesanan sedang menunggu driver/konfirmasi. */
    object Waiting : OrderStatus() {
        override fun display(): String = "Menunggu"
    }

    /** Status pesanan sedang berjalan/dalam perjalanan. */
    object OnGoing : OrderStatus() {
        override fun display(): String = "Berjalan"
    }

    /** Status pesanan telah selesai. */
    object Completed : OrderStatus() {
        override fun display(): String = "Selesai"
    }

    /**
     * Status pesanan dibatalkan.
     * @property reason Alasan pembatalan pesanan.
     */
    data class Cancelled(val reason: String) : OrderStatus() {
        override fun display(): String = "Dibatalkan (Alasan: $reason)"
    }
}
