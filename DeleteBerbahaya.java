import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class DeleteBerbahaya {

    static final String jdbc = "com.mysql.cj.jdbc.Driver";
    static final String url  = "jdbc:mysql://localhost/toko_retail";
    static final String username = "root";
    static final String password = "";

    static Connection con;
    static Statement st;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Isikan Kode Barang yang akan dihapus: ");
        String kode_barang = sc.nextLine(); 

        // BERBAHAYA: input user langsung digabung ke query
        // kalo user isi: ' OR '1'='1
        // semua terhapus karena query jadi: DELETE FROM tbl_barang WHERE kode_barang='' OR '1'='1'
        String query = "DELETE FROM tbl_barang WHERE kode_barang='" + kode_barang + "'";

        // Cetak query agar kita bisa lihat apa yang sebenarnya dieksekusi
        System.out.println("\n[DEBUG] Query yang dijalankan:");
        System.out.println(query);
        System.out.println();

        try {
            Class.forName(jdbc);
            con = DriverManager.getConnection(url, username, password);
            st  = con.createStatement();

            int barisTerhapus = st.executeUpdate(query);

            if (barisTerhapus > 0) {
                System.out.println("Proses Berhasil. Baris terhapus: " + barisTerhapus);
            } else {
                System.out.println("Data Tidak ditemukan");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}