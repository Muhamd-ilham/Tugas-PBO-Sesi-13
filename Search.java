import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Search {

    static final String JDBC = "com.mysql.cj.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3307/toko_retail";
    static final String USERNAME = "root";
    static final String PASSWORD = "";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Cari Nama Barang : ");
        String namaBarang = sc.nextLine();

        String query = "SELECT * FROM tbl_barang WHERE nama_barang LIKE ?";

        try {
            Class.forName(JDBC);

            Connection con = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD);

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, "%" + namaBarang + "%");

            ResultSet rs = ps.executeQuery();

            int nomor = 0;

            while (rs.next()) {
                nomor++;

                System.out.println("Nomor       : " + nomor);
                System.out.println("Kode Barang : " + rs.getString("kode_barang"));
                System.out.println("Nama Barang : " + rs.getString("nama_barang"));
                System.out.println("Harga       : " + rs.getInt("harga_barang"));
                System.out.println("Stok        : " + rs.getInt("stok_barang"));
                System.out.println("------------------------------------");
            }

            if (nomor == 0) {
                System.out.println("Data tidak ditemukan");
            } else {
                System.out.println("Total Barang : " + nomor);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        sc.close();
    }
}