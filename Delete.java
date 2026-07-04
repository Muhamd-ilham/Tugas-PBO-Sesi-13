import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Delete {

    static final String jdbc = "com.mysql.cj.jdbc.Driver";
    static final String url = "jdbc:mysql://localhost/toko_retail";
    static final String username = "root";
    static final String password = "";

    static Connection con;
    static ResultSet rs;
    static PreparedStatement ps;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Isikan Kode Barang yang akan dihapus:");
        String kode_barang = sc.next();

        String query = "delete from tbl_barang where kode_barang=?";

        try {
            Class.forName(jdbc);
            con = DriverManager.getConnection(url, username, password);
            ps = con.prepareStatement(query);

            ps.setString(1, kode_barang);

            if (ps.executeUpdate() > 0) {
                System.out.println("Proses Berhasil");
            } else {
                System.out.println("Data Tidak ditemukan");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}