package com.mycompany.muayene.Model;

import com.mycompany.muayene.DBUtils.Mssql;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Mustafa
 */
@Getter
@Setter
public class Recete {

    private Timestamp receteTarihi;
    private List<Ilac> ilaclar;

    public boolean onaylandi;

    public Recete(Timestamp receteTarihi, List<Ilac> ilaclar) {
        this(receteTarihi, ilaclar, false);
    }
    public Recete(Timestamp receteTarihi, List<Ilac> ilaclar, boolean onaylandi) {
        this.receteTarihi = receteTarihi;
        this.ilaclar = ilaclar;
        this.onaylandi = onaylandi;
    }

    public String isOnaylandi(){
        return onaylandi ? "Onaylandı" : "Onaylanmadı";
    }


    @Override
    public String toString() {
        return "{" +
                " \"receteTarihi\": \"" + receteTarihi + '\"' +
                ", \"ilaclar\": " + ilaclar +
                "} ";
    }


    //Static field
    public static String[] columns = {"receteTarihi", "onaylandi"};
    public static String create(Recete recete) {
        Map<Integer, Ilac> ilacMap = Ilac.readIlacMap();
        String query = "INSERT INTO Recete (receteTarihi, onaylandi) VALUES ('" + recete.receteTarihi + "','" + recete.onaylandi + "')";
        try {
            Connection connection = Mssql.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            String receteNo = readReceteNo(recete);
            //receteNo alıp her bir ilac için YazilanIlaclar girisi yapılacak bu işlem için her ilacın id'si de lazım
            for (Ilac ilac : recete.getIlaclar()) {
                Integer ilacId = null;
                for (Map.Entry<Integer, Ilac> entry : ilacMap.entrySet()) {
                    if (entry.getValue().getIlacAdi().equals(ilac.getIlacAdi())) {
                        ilacId = entry.getKey();
                        break;
                    }
                }
                if (ilacId == null) continue;
                query = "INSERT INTO YazilanIlaclar (receteNo, ilac_id) VALUES (" + receteNo + ", " + ilacId + ")";
                statement.execute(query);
            }
            statement.close();
            connection.close();
            return receteNo;
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public static List<Recete> read() {
        List<Recete> receteler = new ArrayList<>();
        Connection connection = Mssql.getConnection();
        String query = "SELECT * FROM Recete";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String receteNo = result.getString("receteNo");
                Timestamp receteTarihi = result.getTimestamp("receteTarihi");
                boolean onaylandi = result.getBoolean("onaylandi");
                receteler.add(new Recete(receteTarihi, Ilac.readIlaclarFromReceteNo(receteNo), onaylandi));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receteler;
    }

    public static Recete read(String receteNo) {
        Connection connection = Mssql.getConnection();
        String query = "SELECT * FROM Recete WHERE receteNo = " + receteNo;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.next()) { return null; }
            Timestamp receteTarihi = resultSet.getTimestamp("receteTarihi");
            boolean onaylandi = resultSet.getBoolean("onaylandi");
            return new Recete(receteTarihi, Ilac.readIlaclarFromReceteNo(receteNo), onaylandi);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }


    public static String readReceteNo(Recete recete) {
        Connection connection = Mssql.getConnection();
        String query = "SELECT receteNo FROM Recete WHERE receteTarihi = '" + recete.getReceteTarihi() + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.next()) { return null; }
            int receteNo = resultSet.getInt("receteNo");
            statement.close();
            connection.close();
            return Integer.toString(receteNo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(Recete recete) {
        String receteNo = readReceteNo(recete);
        if (receteNo == null || receteNo.isEmpty()) return;
        String query = "UPDATE Recete SET onaylandi='" + recete.onaylandi + "' WHERE receteNo=" + receteNo;
        try {
            Connection connection = Mssql.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}