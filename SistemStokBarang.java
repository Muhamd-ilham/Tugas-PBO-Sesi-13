import java.sql.*;
import java.util.Scanner;

public class SistemStokBarang {

    static final String JDBC = "com.mysql.cj.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3306/toko_retail";
    static final String USER = "root";
    static final String PASS = "";

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        while(true){

            System.out.println("\n========================================");
            System.out.println(" SISTEM MANAJEMEN BARANG JDBC");
            System.out.println("========================================");

            System.out.println("\n MASTER DATA");
            System.out.println(" 1. Tambah Barang (Procedure)");
            System.out.println(" 2. Tampilkan Semua Barang (Select)");
            System.out.println(" 3. Cari Barang");
            System.out.println(" 4. Update Barang");
            System.out.println(" 5. Hapus Barang");

            System.out.println("\n DATABASE OBJECT");
            System.out.println(" 6. Tampilkan Semua Barang (View)");
            System.out.println(" 7. Hitung Total Asset (Function)");
            System.out.println(" 8. Lihat Log Trigger (tbl_log)");

            System.out.println("\n UTILITAS");
            System.out.println(" 9. Hapus Semua Data Barang");

            System.out.println("\n 0. Keluar");

            System.out.println("========================================");
            System.out.print("Pilih Menu : ");

            int pilih = Integer.parseInt(input.nextLine());

            switch(pilih){

                case 1:
                    tambahBarang();
                    break;

                case 2:
                    tampilBarang();
                    break;

                case 3:
                    cariBarang();
                    break;

                case 4:
                    updateBarang();
                    break;

                case 5:
                    hapusBarang();
                    break;

                case 6:
                    lihatView();
                    break;

                case 7:
                    hitungTotalAsset();
                    break;

                case 8:
                    lihatLogTrigger();
                    break;

                case 9:
                    hapusSemuaBarang();
                    break;

                case 0:
                    System.out.println("Program selesai...");
                    System.exit(0);

                default:
                    System.out.println("Menu tidak tersedia");
            }
        }
    }

    static Connection getConnection() throws Exception{

        Class.forName(JDBC);

        return DriverManager.getConnection(
                URL,
                USER,
                PASS
        );
    }

    // ==========================
    // 1. PROCEDURE
    // ==========================

    static void tambahBarang(){

        System.out.println("\nTAMBAH BARANG");

        try(Connection con = getConnection()){

            System.out.print("Kode Barang : ");
            String kode = input.nextLine();

            System.out.print("Nama Barang : ");
            String nama = input.nextLine();

            System.out.print("Harga Barang : ");
            int harga = Integer.parseInt(input.nextLine());

            System.out.print("Stok Barang : ");
            int stok = Integer.parseInt(input.nextLine());

            CallableStatement cs =
                    con.prepareCall(
                    "{call tambah_barang(?,?,?,?)}");

            cs.setString(1,kode);
            cs.setString(2,nama);
            cs.setInt(3,harga);
            cs.setInt(4,stok);

            cs.execute();

            System.out.println("Data berhasil disimpan");

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // ==========================
    // 2. SELECT
    // ==========================

    static void tampilBarang(){

        try(Connection con = getConnection()){

            Statement st = con.createStatement();

            ResultSet rs =
                    st.executeQuery(
                    "SELECT * FROM tbl_barang");

            System.out.println("\nDAFTAR BARANG");

            while(rs.next()){

                System.out.println(
                    rs.getString("kode_barang")
                    +" | "+
                    rs.getString("nama_barang")
                    +" | "+
                    rs.getInt("harga_barang")
                    +" | "+
                    rs.getInt("stok_barang")
                );
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // ==========================
    // 3. SEARCH
    // ==========================

    static void cariBarang(){

        try(Connection con = getConnection()){

            System.out.print("Nama Barang : ");
            String cari = input.nextLine();

            PreparedStatement ps =
                    con.prepareStatement(
                    "SELECT * FROM tbl_barang WHERE nama_barang LIKE ?");

            ps.setString(1,"%"+cari+"%");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                System.out.println(
                    rs.getString("kode_barang")
                    +" | "+
                    rs.getString("nama_barang")
                );
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // ==========================
    // 4. UPDATE
    // ==========================

    static void updateBarang(){

        try(Connection con = getConnection()){

            System.out.print("Kode Barang : ");
            String kode = input.nextLine();

            System.out.print("Nama Baru : ");
            String nama = input.nextLine();

            System.out.print("Harga Baru : ");
            int harga = Integer.parseInt(input.nextLine());

            System.out.print("Stok Baru : ");
            int stok = Integer.parseInt(input.nextLine());

            PreparedStatement ps =
                    con.prepareStatement(
                    "UPDATE tbl_barang SET nama_barang=?, harga_barang=?, stok_barang=? WHERE kode_barang=?");

            ps.setString(1,nama);
            ps.setInt(2,harga);
            ps.setInt(3,stok);
            ps.setString(4,kode);

            ps.executeUpdate();

            System.out.println("Data berhasil diupdate");

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // ==========================
    // 5. DELETE
    // ==========================

    static void hapusBarang(){

        try(Connection con = getConnection()){

            System.out.print("Kode Barang : ");
            String kode = input.nextLine();

            PreparedStatement ps =
                    con.prepareStatement(
                    "DELETE FROM tbl_barang WHERE kode_barang=?");

            ps.setString(1,kode);

            ps.executeUpdate();

            System.out.println("Data berhasil dihapus");

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // ==========================
    // 6. VIEW
    // ==========================

    static void lihatView(){

        try(Connection con = getConnection()){

            Statement st = con.createStatement();

            ResultSet rs =
                    st.executeQuery(
                    "SELECT * FROM v_barang");

            while(rs.next()){

                System.out.println(
                    rs.getString("kode_barang")
                    +" | "+
                    rs.getString("nama_barang")
                    +" | Total Nilai : "+
                    rs.getInt("total_nilai")
                );
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // ==========================
    // 7. FUNCTION
    // ==========================

    static void hitungTotalAsset(){

        try(Connection con = getConnection()){

            Statement st = con.createStatement();

            ResultSet rs =
                    st.executeQuery(
                    "SELECT total_asset() AS total");

            if(rs.next()){

                System.out.println(
                    "\nTotal Asset : Rp "
                    + rs.getLong("total")
                );
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // ==========================
    // 8. TRIGGER LOG
    // ==========================

    static void lihatLogTrigger(){

        try(Connection con = getConnection()){

            Statement st = con.createStatement();

            ResultSet rs =
                    st.executeQuery(
                    "SELECT * FROM log_barang ORDER BY id DESC");

            while(rs.next()){

                System.out.println(
                    rs.getInt("id")
                    +" | "+
                    rs.getString("aksi")
                    +" | "+
                    rs.getString("kode_barang")
                    +" | "+
                    rs.getString("nama_barang")
                    +" | "+
                    rs.getTimestamp("waktu")
                );
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // ==========================
    // 9. UTILITAS
    // ==========================

    static void hapusSemuaBarang(){

        try(Connection con = getConnection()){

            PreparedStatement ps =
                    con.prepareStatement(
                    "DELETE FROM tbl_barang");

            int jumlah = ps.executeUpdate();

            System.out.println(
                    jumlah +
                    " data berhasil dihapus");

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}