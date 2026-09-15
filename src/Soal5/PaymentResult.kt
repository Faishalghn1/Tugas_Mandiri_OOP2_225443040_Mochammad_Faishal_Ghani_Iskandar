/**
 * Merepresentasikan hasil dari sebuah proses pembayaran.
 *
 * Dideklarasikan sebagai `sealed class` karena himpunan kemungkinan hasil
 * pembayaran telah diketahui dan terbatas sepenuhnya ([Success], [Failed],
 * [Pending]), sehingga kompiler dapat memastikan blok `when` yang menangani
 * [PaymentResult] bersifat ekshaustif (mencakup semua kemungkinan) tanpa
 * perlu klausa `else`.
 */
sealed class PaymentResult {

    /**
     * Mengembalikan representasi teks dari hasil pembayaran ini.
     * @return String deskripsi hasil pembayaran.
     */
    abstract fun display(): String

    /**
     * Merepresentasikan pembayaran yang berhasil diproses.
     *
     * @property transactionId Nomor identitas transaksi.
     * @property timestamp Waktu transaksi dilakukan.
     */
    data class Success(val transactionId: String, val timestamp: String) : PaymentResult() {
        override fun display(): String {
            return "BERHASIL - ID Transaksi: $transactionId, Waktu: $timestamp"
        }
    }

    /**
     * Merepresentasikan pembayaran yang gagal diproses.
     *
     * @property reason Alasan kegagalan pembayaran.
     * @property errorCode Kode error yang menyertai kegagalan.
     */
    data class Failed(val reason: String, val errorCode: Int) : PaymentResult() {
        override fun display(): String {
            return "GAGAL - Alasan: $reason (Kode Error: $errorCode)"
        }
    }

    /**
     * Merepresentasikan pembayaran yang masih dalam status menunggu/diproses.
     */
    object Pending : PaymentResult() {
        override fun display(): String {
            return "PENDING - Pembayaran sedang diproses"
        }
    }
}
