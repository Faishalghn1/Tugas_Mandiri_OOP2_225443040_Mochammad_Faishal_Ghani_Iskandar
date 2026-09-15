/**
 * Interface (kontrak) yang harus dipenuhi oleh setiap metode pembayaran
 * dalam sistem, seperti [CreditCard], [QRIS], dan [Cash].
 *
 * Digunakan sebagai basis polimorfisme: berbagai implementasi metode
 * pembayaran dapat diperlakukan secara seragam melalui tipe [PaymentMethod].
 */
interface PaymentMethod {

    /** Nama metode pembayaran, contoh: "Kartu Kredit", "QRIS", "Tunai". */
    val name: String

    /**
     * Memproses pembayaran sejumlah nominal tertentu.
     *
     * @param amount Nominal yang akan dibayarkan.
     * @return [PaymentResult] hasil dari proses pembayaran.
     */
    fun processPayment(amount: Double): PaymentResult

    /**
     * Menghitung biaya tambahan (fee) dari metode pembayaran ini.
     *
     * Secara default tidak dikenakan biaya tambahan (`0.0`), dapat
     * di-override oleh implementasi yang membutuhkan biaya tambahan.
     *
     * @param amount Nominal transaksi yang menjadi dasar perhitungan fee.
     * @return Nominal biaya tambahan.
     */
    fun getFee(amount: Double): Double = 0.0
}

/**
 * Implementasi [PaymentMethod] untuk pembayaran menggunakan kartu kredit.
 *
 * Nomor kartu divalidasi pada saat objek dibuat: harus berjumlah minimal 16 digit.
 *
 * @property cardNumber Nomor kartu kredit yang digunakan.
 * @throws IllegalArgumentException jika nomor kartu kurang dari 16 digit.
 */
class CreditCard(private val cardNumber: String) : PaymentMethod {

    override val name: String = "Kartu Kredit"

    init {
        require(cardNumber.length >= 16) {
            "Nomor kartu kredit tidak valid, minimal 16 digit."
        }
    }

    /**
     * Menghitung biaya tambahan kartu kredit sebesar 2% dari nominal transaksi.
     */
    override fun getFee(amount: Double): Double = amount * 0.02

    /**
     * Memproses pembayaran menggunakan kartu kredit dan mengembalikan hasilnya
     * sebagai [PaymentResult.Success].
     */
    override fun processPayment(amount: Double): PaymentResult {
        val totalWithFee = amount + getFee(amount)
        val maskedCard = "**** **** **** ${cardNumber.takeLast(4)}"
        println("Memproses pembayaran Kartu Kredit ($maskedCard) sebesar Rp${"%,.2f".format(totalWithFee)}")
        return PaymentResult.Success(
            transactionId = "TRX-CC-${System.nanoTime()}",
            timestamp = java.time.LocalDateTime.now().toString()
        )
    }
}

/**
 * Implementasi [PaymentMethod] untuk pembayaran menggunakan QRIS.
 *
 * Kode QR divalidasi pada saat objek dibuat: harus berjumlah minimal 10 karakter.
 *
 * @property qrCode Kode QR yang dipindai untuk transaksi.
 * @throws IllegalArgumentException jika kode QR kurang dari 10 karakter.
 */
class QRIS(private val qrCode: String) : PaymentMethod {

    override val name: String = "QRIS"

    init {
        require(qrCode.length >= 10) {
            "Kode QR tidak valid, minimal 10 karakter."
        }
    }

    /**
     * Menghitung biaya tambahan QRIS sebesar 0.5% dari nominal transaksi.
     */
    override fun getFee(amount: Double): Double = amount * 0.005

    /**
     * Memproses pembayaran menggunakan QRIS dan mengembalikan hasilnya
     * sebagai [PaymentResult.Success].
     */
    override fun processPayment(amount: Double): PaymentResult {
        val totalWithFee = amount + getFee(amount)
        println("Memproses pembayaran QRIS (kode: $qrCode) sebesar Rp${"%,.2f".format(totalWithFee)}")
        return PaymentResult.Success(
            transactionId = "TRX-QR-${System.nanoTime()}",
            timestamp = java.time.LocalDateTime.now().toString()
        )
    }
}

/**
 * Implementasi [PaymentMethod] untuk pembayaran tunai.
 *
 * Pembayaran tunai tidak dikenakan biaya tambahan apa pun.
 */
class Cash : PaymentMethod {

    override val name: String = "Tunai"

    /**
     * Tidak ada biaya tambahan untuk pembayaran tunai.
     */
    override fun getFee(amount: Double): Double = 0.0

    /**
     * Memproses pembayaran tunai dan mengembalikan hasilnya sebagai
     * [PaymentResult.Success].
     */
    override fun processPayment(amount: Double): PaymentResult {
        println("Memproses pembayaran Tunai sebesar Rp${"%,.2f".format(amount)}")
        return PaymentResult.Success(
            transactionId = "TRX-CASH-${System.nanoTime()}",
            timestamp = java.time.LocalDateTime.now().toString()
        )
    }
}
