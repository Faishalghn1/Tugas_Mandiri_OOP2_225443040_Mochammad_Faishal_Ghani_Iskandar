/**
 * Kelas pengendali (controller) yang mengintegrasikan seluruh entitas dalam
 * sistem transportasi online: [Vehicle], [Driver], [Customer], [Order], dan
 * [Payment].
 *
 * Seluruh koleksi data disimpan sebagai properti privat ([vehicles], [drivers],
 * [customers], [orders], [payments]) sehingga hanya dapat diakses/dimodifikasi
 * melalui metode publik yang disediakan kelas ini — ini adalah bentuk enkapsulasi
 * pada level sistem, mencegah data diubah sembarangan dari luar.
 *
 * @property name Nama sistem/layanan transportasi.
 */
class TransportSystem(val name: String) {

    /** Daftar seluruh kendaraan yang terdaftar di sistem. Bersifat privat. */
    private val vehicles: MutableList<Vehicle> = mutableListOf()

    /** Daftar seluruh driver yang terdaftar di sistem. Bersifat privat. */
    private val drivers: MutableList<Driver> = mutableListOf()

    /** Daftar seluruh customer yang terdaftar di sistem. Bersifat privat. */
    private val customers: MutableList<Customer> = mutableListOf()

    /** Daftar seluruh pesanan yang pernah dibuat di sistem. Bersifat privat. */
    private val orders: MutableList<Order> = mutableListOf()

    /** Daftar seluruh transaksi pembayaran yang pernah diproses. Bersifat privat. */
    private val payments: MutableList<Payment> = mutableListOf()

    // ---------------------------------------------------------------------
    // Metode Manajemen
    // ---------------------------------------------------------------------

    /**
     * Mendaftarkan kendaraan baru ke dalam sistem.
     * @param vehicle Kendaraan yang akan ditambahkan.
     */
    fun addVehicle(vehicle: Vehicle) {
        vehicles.add(vehicle)
    }

    /**
     * Mendaftarkan driver baru ke dalam sistem.
     * @param driver Driver yang akan ditambahkan.
     */
    fun addDriver(driver: Driver) {
        drivers.add(driver)
    }

    /**
     * Mendaftarkan customer baru ke dalam sistem.
     * @param customer Customer yang akan ditambahkan.
     */
    fun addCustomer(customer: Customer) {
        customers.add(customer)
    }

    /**
     * Mencari kendaraan berdasarkan nomor plat.
     * @param plateNumber Nomor plat kendaraan yang dicari.
     * @return Objek [Vehicle] jika ditemukan, `null` jika tidak ada.
     */
    fun findVehicle(plateNumber: String): Vehicle? {
        return vehicles.find { it.plateNumber == plateNumber }
    }

    /**
     * Mencari driver berdasarkan ID.
     * @param id ID driver yang dicari.
     * @return Objek [Driver] jika ditemukan, `null` jika tidak ada.
     */
    fun findDriver(id: String): Driver? {
        return drivers.find { it.id == id }
    }

    /**
     * Mencari customer berdasarkan ID.
     * @param id ID customer yang dicari.
     * @return Objek [Customer] jika ditemukan, `null` jika tidak ada.
     */
    fun findCustomer(id: String): Customer? {
        return customers.find { it.id == id }
    }

    // ---------------------------------------------------------------------
    // Metode Operasi
    // ---------------------------------------------------------------------

    /**
     * Membuat pesanan baru antara seorang customer dan driver.
     *
     * Mencari customer dan driver berdasarkan ID; jika keduanya ditemukan,
     * sebuah [Order] baru dibuat dan ditambahkan ke daftar pesanan sistem.
     *
     * @param customerId ID customer pemesan.
     * @param driverId ID driver yang dipesan.
     * @param pickup Lokasi penjemputan.
     * @param dest Lokasi tujuan.
     * @param distance Jarak tempuh perjalanan dalam kilometer.
     * @return [Order] yang baru dibuat, atau `null` jika customer/driver tidak ditemukan.
     */
    fun createOrder(
        customerId: String,
        driverId: String,
        pickup: String,
        dest: String,
        distance: Double
    ): Order? {
        val customer = findCustomer(customerId) ?: return null
        val driver = findDriver(driverId) ?: return null

        val order = Order(
            id = "ORD${(orders.size + 1).toString().padStart(3, '0')}",
            customer = customer,
            driver = driver,
            pickupLocation = pickup,
            destination = dest,
            distanceKm = distance
        )
        orders.add(order)
        return order
    }

    /**
     * Memproses pembayaran untuk sebuah pesanan berdasarkan ID pesanan.
     *
     * Mencari pesanan, membuat objek [Payment] baru dengan metode pembayaran
     * yang diberikan, memprosesnya, lalu menyimpan transaksi ke daftar payments.
     *
     * @param orderId ID pesanan yang akan dibayar.
     * @param method Metode pembayaran yang digunakan.
     * @param paidAmount Nominal yang dibayarkan.
     * @return [PaymentResult] hasil dari proses pembayaran.
     */
    fun processPayment(orderId: String, method: PaymentMethod, paidAmount: Double): PaymentResult {
        val order = orders.find { it.id == orderId }
            ?: return PaymentResult.Failed("Pesanan dengan ID $orderId tidak ditemukan", 404)

        val payment = Payment(order, method)
        val result = payment.processPayment(paidAmount)
        payments.add(payment)
        return result
    }

    /**
     * Menyelesaikan sebuah pesanan berdasarkan ID.
     * @param orderId ID pesanan yang akan diselesaikan.
     * @return `true` jika berhasil diselesaikan, `false` jika pesanan tidak ditemukan atau gagal diselesaikan.
     */
    fun completeOrder(orderId: String): Boolean {
        val order = orders.find { it.id == orderId } ?: return false
        return order.completeTrip()
    }

    /**
     * Membatalkan sebuah pesanan berdasarkan ID.
     * @param orderId ID pesanan yang akan dibatalkan.
     * @param reason Alasan pembatalan.
     * @return `true` jika berhasil dibatalkan, `false` jika pesanan tidak ditemukan atau gagal dibatalkan.
     */
    fun cancelOrder(orderId: String, reason: String): Boolean {
        val order = orders.find { it.id == orderId } ?: return false
        return order.cancelTrip(reason)
    }

    // ---------------------------------------------------------------------
    // Metode Laporan
    // ---------------------------------------------------------------------

    /** Mencetak daftar seluruh kendaraan yang terdaftar di sistem (polimorfisme pada displayInfo). */
    fun displayAllVehicles() {
        println("--- Daftar Semua Kendaraan ($name) ---")
        vehicles.forEach { it.displayInfo(); println() }
    }

    /** Mencetak daftar seluruh driver yang terdaftar di sistem. */
    fun displayAllDrivers() {
        println("--- Daftar Semua Driver ($name) ---")
        drivers.forEach { it.displayInfo(); println() }
    }

    /** Mencetak daftar seluruh customer yang terdaftar di sistem. */
    fun displayAllCustomers() {
        println("--- Daftar Semua Customer ($name) ---")
        customers.forEach { it.displayInfo(); println() }
    }

    /** Mencetak daftar seluruh pesanan yang pernah dibuat di sistem. */
    fun displayAllOrders() {
        println("--- Daftar Semua Pesanan ($name) ---")
        orders.forEach { it.displayOrder(); println() }
    }

    /**
     * Mencetak laporan total pendapatan dari seluruh pesanan yang berstatus
     * [OrderStatus.Completed].
     */
    fun displayRevenueReport() {
        val completedOrders = orders.filter { it.status is OrderStatus.Completed }
        val totalRevenue = completedOrders.sumOf { it.getTotalFare() }
        println("--- Laporan Pendapatan ($name) ---")
        println("Jumlah pesanan selesai : ${completedOrders.size}")
        println("Total pendapatan       : Rp${"%,.2f".format(totalRevenue)}")
    }
}
