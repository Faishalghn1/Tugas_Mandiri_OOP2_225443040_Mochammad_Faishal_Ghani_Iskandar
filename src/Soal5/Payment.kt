/**
 * Merepresentasikan pembayaran untuk sebuah [Order].
 *
 * Metode pembayaran kini direpresentasikan melalui interface [PaymentMethod]
 * (menggantikan `_method: String` pada versi sebelumnya) sehingga proses
 * pembayaran bersifat polimorfik: perilaku [processPayment] bergantung pada
 * implementasi konkret ([CreditCard], [QRIS], atau [Cash]) yang digunakan.
 *
 * @property order Pesanan yang dibayar.
 * @property method Metode pembayaran yang digunakan untuk transaksi ini.
 */
class Payment(val order: Order, var method: PaymentMethod) {

    /** Nominal pembayaran, otomatis diisi dari total tarif pesanan. Bersifat privat. */
    private var _amount: Double = order.getTotalFare()

    /** Status pembayaran. `private set` agar hanya bisa diubah dari dalam kelas ini. */
    var isPaid: Boolean = false
        private set

    /** Menyimpan hasil ([PaymentResult]) dari transaksi terakhir yang diproses. */
    var lastResult: PaymentResult = PaymentResult.Pending
        private set

    /**
     * Mengambil nominal pembayaran (belum termasuk fee metode pembayaran).
     * @return Nominal pembayaran dalam Rupiah.
     */
    fun getAmount(): Double = _amount

    /**
     * Memproses pembayaran melalui [method] yang sedang digunakan.
     *
     * Pembayaran hanya diproses jika belum lunas sebelumnya. Hasil dari
     * [PaymentMethod.processPayment] disimpan sebagai [lastResult], dan jika
     * hasilnya berupa [PaymentResult.Success], status [isPaid] diubah menjadi `true`.
     *
     * @param paidAmount Nominal yang dibayarkan oleh pelanggan.
     * @return [PaymentResult] hasil dari proses pembayaran.
     */
    fun processPayment(paidAmount: Double): PaymentResult {
        if (isPaid) {
            return PaymentResult.Failed("Pesanan ini sudah lunas sebelumnya", 409)
        }
        if (paidAmount < _amount) {
            val result = PaymentResult.Failed(
                reason = "Nominal pembayaran kurang dari tagihan",
                errorCode = 400
            )
            lastResult = result
            return result
        }

        val result = method.processPayment(_amount)
        if (result is PaymentResult.Success) {
            isPaid = true
        }
        lastResult = result
        return result
    }

    /**
     * Mencetak detail pembayaran ke konsol, termasuk hasil transaksi terakhir.
     */
    fun displayPayment() {
        println("=== Detail Pembayaran ===")
        println("Order ID   : ${order.id}")
        println("Nominal    : Rp${"%,.2f".format(_amount)}")
        println("Metode     : ${method.name}")
        println("Status     : ${if (isPaid) "Lunas" else "Belum Lunas"}")
        println("Hasil      : ${lastResult.display()}")
    }
}
