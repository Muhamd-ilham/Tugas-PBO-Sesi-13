import java.sql.*;
import java.util.Scanner;

public class TokoRetailCLI {
    // 1. PERUBAHAN DI SINI: Menambahkan port 3307 pada DB_URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3307/toko_retail";
    static final String USER = "root";
    static final String PASS = ""; // Kosongkan saja kalau di phpMyAdmin tidak pakai password

    static Connection conn;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            int pilihan = -1;
            while (pilihan != 0) {
                tampilkanMenu();
                System.out.print("Pilihan : ");
                
                try {
                    pilihan = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Input tidak valid. Masukkan angka.");
                    continue;
                }

                switch (pilihan) {
                    case 1: tampilSemuaData(); break;
                    case 2: tambahData(); break;
                    case 3: cariData(); break;
                    case 4: ubahData(); break;
                    case 5: hapusData(); break;
                    case 0: System.out.println("Keluar dari program..."); break;
                    default: System.out.println("Pilihan tidak tersedia!");
                }
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gagal terhubung ke database. Cek konfigurasi Port dan Password.");
        }
    }

    static void tampilkanMenu() {
        System.out.println("\n===========================");
        System.out.println("     MENU TOKO RETAIL");
        System.out.println("===========================");
        System.out.println("1. Tampil Semua Data");
        System.out.println("2. Tambah Data");
        System.out.println("3. Cari Data");
        System.out.println("4. Ubah Data");
        System.out.println("5. Hapus Data");
        System.out.println("0. Keluar");
        System.out.println("===========================");
    }

    static void tampilSemuaData() {
        try {
            Statement stmt = conn.createStatement();
            // 2. PERUBAHAN DI SINI: Menggunakan tbl_barang
            String sql = "SELECT * FROM tbl_barang"; 
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n=======================================================");
            System.out.println("               DAFTAR BARANG TOKO RETAIL");
            System.out.println("=======================================================");
            System.out.printf("| %-3s | %-6s | %-20s | %-8s | %-4s |\n", "#", "Kode", "Nama Barang", "Harga", "Stok");
            System.out.println("=======================================================");

            int no = 1;
            while (rs.next()) {
                String kode = rs.getString("kode");
                String nama = rs.getString("nama_barang");
                int harga = rs.getInt("harga");
                int stok = rs.getInt("stok");
                System.out.printf("| %-3d | %-6s | %-20s | %-8d | %-4d |\n", no++, kode, nama, harga, stok);
            }
            System.out.println("=======================================================");
            System.out.println("Total: " + (no - 1) + " barang");

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void tambahData() {
        try {
            System.out.println("\n--- Tambah Data Barang ---");
            System.out.print("Masukkan Kode        : ");
            String kode = scanner.nextLine();
            System.out.print("Masukkan Nama Barang : ");
            String nama = scanner.nextLine();
            System.out.print("Masukkan Harga       : ");
            int harga = Integer.parseInt(scanner.nextLine());
            System.out.print("Masukkan Stok        : ");
            int stok = Integer.parseInt(scanner.nextLine());

            // 2. PERUBAHAN DI SINI: Menggunakan tbl_barang
            String sql = "INSERT INTO tbl_barang (kode, nama_barang, harga, stok) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, kode);
            pstmt.setString(2, nama);
            pstmt.setInt(3, harga);
            pstmt.setInt(4, stok);

            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("Data berhasil ditambahkan!");
            pstmt.close();
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Gagal menambah data. Pastikan input harga dan stok berupa angka.");
        }
    }

    static void cariData() {
        try {
            System.out.println("\n--- Cari Data Barang ---");
            System.out.print("Masukkan Kode Barang : ");
            String kode = scanner.nextLine();

            // 2. PERUBAHAN DI SINI: Menggunakan tbl_barang
            String sql = "SELECT * FROM tbl_barang WHERE kode = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, kode);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n--- Hasil Pencarian ---");
                System.out.println("Kode        : " + rs.getString("kode"));
                System.out.println("Nama Barang : " + rs.getString("nama_barang"));
                System.out.println("Harga       : " + rs.getInt("harga"));
                System.out.println("Stok        : " + rs.getInt("stok"));
            } else {
                System.out.println("Data barang dengan kode " + kode + " tidak ditemukan.");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void ubahData() {
        try {
            System.out.println("\n--- Ubah Data Barang ---");
            System.out.print("Masukkan Kode Barang yang ingin diubah : ");
            String kode = scanner.nextLine();

            System.out.print("Masukkan Nama Barang Baru : ");
            String nama = scanner.nextLine();
            System.out.print("Masukkan Harga Baru       : ");
            int harga = Integer.parseInt(scanner.nextLine());
            System.out.print("Masukkan Stok Baru        : ");
            int stok = Integer.parseInt(scanner.nextLine());

            // 2. PERUBAHAN DI SINI: Menggunakan tbl_barang
            String sql = "UPDATE tbl_barang SET nama_barang=?, harga=?, stok=? WHERE kode=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nama);
            pstmt.setInt(2, harga);
            pstmt.setInt(3, stok);
            pstmt.setString(4, kode);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Data berhasil diubah!");
            } else {
                System.out.println("Gagal mengubah data. Kode barang tidak ditemukan.");
            }
            pstmt.close();
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Gagal mengubah data.");
        }
    }

    static void hapusData() {
        try {
            System.out.println("\n--- Hapus Data Barang ---");
            System.out.print("Masukkan Kode Barang yang ingin dihapus : ");
            String kode = scanner.nextLine();

            // 2. PERUBAHAN DI SINI: Menggunakan tbl_barang
            String sql = "DELETE FROM tbl_barang WHERE kode = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, kode);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Data berhasil dihapus!");
            } else {
                System.out.println("Gagal menghapus data. Kode barang tidak ditemukan.");
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}