import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ShowData {

    static final String jdbc = "com.mysql.cj.jdbc.Driver";
    static String url = "jdbc:mysql://localhost/toko_retail";
    static String username = "root";
    static String password = "";

    static Connection conn;
    static ResultSet rs;
    static Statement state;

    public static void main(String[] args) {

        try {
            Class.forName(jdbc);
            conn = DriverManager.getConnection(url, username, password);

            String query = "select * from tbl_barang";
            state = conn.createStatement();
            rs = state.executeQuery(query);

            int nomor = 0;

            while (rs.next()) {
                nomor++;

                System.out.println("No:" + nomor);
                System.out.println("Kode Barang:" + rs.getString("kode_barang"));
                System.out.println("Nama Barang:" + rs.getString("nama_barang"));
                System.out.println("Harga:" + rs.getInt("harga_barang"));
                System.out.println("Stok:" + rs.getInt("stok_barang"));
                System.out.println("------------------------------------------");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}