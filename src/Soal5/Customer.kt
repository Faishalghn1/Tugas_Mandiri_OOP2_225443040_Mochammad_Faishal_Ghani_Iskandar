/**
 * Merepresentasikan seorang pelanggan (customer) dalam sistem transportasi online.
 *
 * Customer memiliki saldo (balance) yang dapat digunakan untuk membayar pesanan
 * dan dapat ditambah melalui proses top-up.
 *
 * @property id Identitas unik pelanggan.
 * @property name Nama lengkap pelanggan.
 * @property phone Nomor telepon pelanggan.
 * @property email Alamat email pelanggan.
 * @property balance Saldo yang dimiliki pelanggan. Nilai default adalah `0.0`.
 */
class Customer(
    val id: String,
    val name: String,
    val phone: String,
    val email: String,
    var balance: Double = 0.0
) {

    /**
     * Mencetak seluruh informasi pelanggan ke konsol.
     */
    fun displayInfo() {
        println("=== Info Customer ===")
        println("ID      : $id")
        println("Nama    : $name")
        println("Telepon : $phone")
        println("Email   : $email")
        println("Saldo   : Rp${"%,.2f".format(balance)}")
    }

    /**
     * Menambahkan sejumlah nominal ke saldo pelanggan (top up).
     *
     * @param amount Jumlah nominal yang ditambahkan ke saldo.
     */
    fun topUp(amount: Double) {
        balance += amount
    }

    /**
     * Memeriksa apakah saldo pelanggan mencukupi untuk membayar sejumlah nominal.
     *
     * @param amount Nominal yang akan dibayarkan.
     * @return `true` jika saldo mencukupi (`balance >= amount`), `false` jika tidak.
     */
    fun canPay(amount: Double): Boolean {
        return balance >= amount
    }
}
