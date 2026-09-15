/**
 * Fungsi utama untuk mendemonstrasikan Soal 5: Integrasi Sistem dan Fungsi main() Lengkap.
 *
 * Menggabungkan seluruh kelas ([Vehicle] beserta subclass-nya, [Driver], [Customer],
 * [Order], [Payment], [PaymentMethod], [PaymentResult], [OrderStatus]) ke dalam
 * satu sistem terintegrasi melalui [TransportSystem], serta menjalankan skenario
 * nyata sesuai kebutuhan bisnis sistem transportasi online.
 */
fun main() {
    println("########## SOAL 5: INTEGRASI SISTEM (TransportSystem) ##########\n")

    // 1. Inisialisasi TransportSystem
    val system = TransportSystem("Go-Transport 2024")

    // 2. Tambah Data: Kendaraan
    val car = Car("B 1234 XYZ", "Toyota", "Innova", 2021, "Bensin", 4)
    val motorcycle = Motorcycle("D 5678 ABC", "Honda", "Beat", 2022, 125, true)
    val truck = Truck("E 9012 DEF", "Hino", "Dutro", 2020, 5.0, 2)
    system.addVehicle(car)
    system.addVehicle(motorcycle)
    system.addVehicle(truck)

    // Tambah Data: Driver
    val driver1 = Driver("D001", "Andi", "08123456789", car)
    val driver2 = Driver("D002", "Budi", "08129876543", motorcycle)
    val driver3 = Driver("D003", "Citra", "08125678901", truck)
    system.addDriver(driver1)
    system.addDriver(driver2)
    system.addDriver(driver3)

    // Tambah Data: Customer
    val customer1 = Customer("C001", "Dewi", "08134567890", "dewi@email.com", 100000.0)
    val customer2 = Customer("C002", "Eko", "08135678901", "eko@email.com", 50000.0)
    val customer3 = Customer("C003", "Fani", "08136789012", "fani@email.com", 200000.0)
    system.addCustomer(customer1)
    system.addCustomer(customer2)
    system.addCustomer(customer3)

    // 3. Tampilkan Data Awal
    system.displayAllVehicles()
    system.displayAllDrivers()
    system.displayAllCustomers()

    // 4-6. Buat 3 Pesanan
    val order1 = system.createOrder("C001", "D001", "Kampus A", "Mall B", 12.0)
    val order2 = system.createOrder("C002", "D002", "Stasiun", "Kantor", 8.0)
    val order3 = system.createOrder("C003", "D003", "Gudang", "Pelabuhan", 25.0)
    println("Order 1 dibuat: ${order1?.id}")
    println("Order 2 dibuat: ${order2?.id}")
    println("Order 3 dibuat: ${order3?.id}\n")

    // 7. Tampilkan Semua Order
    system.displayAllOrders()

    // 8. Proses Pembayaran Order 1: Dewi membayar QRIS, nominal sesuai tagihan
    println("=== Pembayaran Order 1 (Dewi - QRIS) ===")
    val tagihan1 = order1!!.getTotalFare()
    val hasil1 = system.processPayment(order1.id, QRIS("QRISCODE001"), tagihan1)
    println("Hasil: ${hasil1.display()}\n")

    // 9. Proses Pembayaran Order 2: Eko membayar Tunai dengan nominal kurang -> gagal
    println("=== Pembayaran Order 2 (Eko - Tunai, nominal kurang) ===")
    val tagihan2 = order2!!.getTotalFare()
    val hasilGagal = system.processPayment(order2.id, Cash(), tagihan2 - 10000.0)
    println("Hasil (seharusnya gagal): ${hasilGagal.display()}\n")

    // Top-up saldo Eko, lalu bayar lagi dengan Kartu Kredit
    println("=== Top Up Saldo Eko & Bayar Ulang dengan Kartu Kredit ===")
    customer2.topUp(50000.0)
    println("Saldo Eko setelah top up: Rp${"%,.2f".format(customer2.balance)}")
    val hasil2 = system.processPayment(order2.id, CreditCard("4444555566667777"), tagihan2)
    println("Hasil: ${hasil2.display()}\n")

    // 10. Selesaikan Order 1
    println("=== Selesaikan Order 1 ===")
    println("completeOrder(order1.id) -> ${system.completeOrder(order1.id)}\n")

    // 11. Batalkan Order 3 dengan alasan "Hujan deras"
    println("=== Batalkan Order 3 ===")
    println("cancelOrder(order3.id, \"Hujan deras\") -> ${system.cancelOrder(order3!!.id, "Hujan deras")}\n")

    // 12. Tampilkan Status Akhir Semua Order
    system.displayAllOrders()

    // 13. Tampilkan Laporan Pendapatan
    system.displayRevenueReport()
    println()

    // 14. Demonstrasi Polimorfisme: hitung tarif 15km untuk semua kendaraan
    println("=== Demonstrasi Polimorfisme: calculateFare(15.0) untuk semua kendaraan ===")
    for (v in listOf(car, motorcycle, truck)) {
        println("${v.getType()} (${v.plateNumber}) -> Rp${"%,.2f".format(v.calculateFare(15.0))}")
    }
    println()

    // 15. Demonstrasi Smart Casting: cek apakah driver D001 punya kendaraan Car
    println("=== Demonstrasi Smart Casting ===")
    val vehicleD001 = system.findDriver("D001")?.vehicle
    if (vehicleD001 is Car) {
        println("Driver D001 menggunakan Car dengan fuelType: ${vehicleD001.fuelType}")
    } else {
        println("Driver D001 tidak menggunakan Car")
    }
    println()

    // 16. Demonstrasi Sealed Class OrderStatus
    println("=== Demonstrasi Sealed Class: Semua Kemungkinan OrderStatus ===")
    val semuaStatus: List<OrderStatus> = listOf(
        OrderStatus.Waiting,
        OrderStatus.OnGoing,
        OrderStatus.Completed,
        OrderStatus.Cancelled("Contoh alasan")
    )
    for (s in semuaStatus) {
        when (s) {
            is OrderStatus.Waiting -> println("- ${s.display()}")
            is OrderStatus.OnGoing -> println("- ${s.display()}")
            is OrderStatus.Completed -> println("- ${s.display()}")
            is OrderStatus.Cancelled -> println("- ${s.display()}")
        }
    }
}
