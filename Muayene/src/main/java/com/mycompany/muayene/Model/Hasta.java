package com.mycompany.muayene.Model;

import com.mycompany.muayene.DBUtils.CRUDShortcuts;
import com.mycompany.muayene.DBUtils.Mssql;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author Mustafa
 */
@Getter
@Setter
public class Hasta extends Kisi {
    private String tani;
    private Recete recete;

    public Hasta(String ad, String soyad, long tcKimlikNo, String tani, Recete recete) {
        super(ad, soyad, tcKimlikNo);
        this.tani = tani;
        this.recete = recete;
    }

    @Override
    public String toString() {
        return "{" +
                " \"Hasta\":" +
                super.toString() + "," +
                " \"tani\": \"" + tani + '\"' + "," +
                " \"recete\": " + recete  +
                "} ";
    }


    //Static functions
    private static String[] columns = {"kisi_id, tani", "receteNo"};
    public static void create(Hasta hasta) {
        Kisi.create(hasta);
        int id = Kisi.readId(hasta);
        if (id == -1) return;
        String receteNo = Recete.create(hasta.getRecete());
        String query = CRUDShortcuts.createQuery("Hasta", columns, new String[]{id+"", hasta.getTani(), receteNo});
        CRUDShortcuts.executeUpdate(query);
        //Burada recete nesnesini de veritabanına kaydet
    }

    //This should return more than one Hasta object with same tcKimlikNo
    public static List<Hasta> readHastaFromTCKimlikNo(long tcKimlikno) {
        List<Hasta> hasta = new ArrayList<>();
        String query =
                "SELECT ad, soyad, tcKimlikno, tani, receteNo\n" +
                        "FROM Kisi\n" +
                        "RIGHT JOIN Hasta\n" +
                        "ON Hasta.kisi_id = Kisi.id\n" +
                        "WHERE tcKimlikno="+ tcKimlikno +";";
        try {
            Connection connection = Mssql.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String ad = result.getString("ad");
                String soyad = result.getString("soyad");
                String tani = result.getString("tani");
                String receteNo = result.getString("receteNo");
                hasta.add(new Hasta(ad, soyad, tcKimlikno, tani, Recete.read(receteNo)));
            }
            result.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasta;
    }

    public static List<Hasta> read() {
        List<Hasta> hastalar = new ArrayList<>();
        String query =
                    "SELECT ad, soyad, tcKimlikno, tani, receteNo\n" +
                        "FROM Kisi\n" +
                        "RIGHT JOIN Hasta\n" +
                        "ON Hasta.kisi_id = Kisi.id;";
        try {
            Connection connection = Mssql.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String receteNo = result.getString("receteNo");
                hastalar.add(new Hasta(
                        result.getString("ad"),
                        result.getString("soyad"),
                        result.getLong("tcKimlikno"),
                        result.getString("tani"),
                        Recete.read(receteNo)
                        )
                );
            }
            result.close();
            connection.close();
            return hastalar;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hastalar;
    }

    public static void update(Hasta hasta) {
        Kisi.update(hasta);
        String query = CRUDShortcuts.updateQuery("Hasta", new String[]{"tani"}, new String[]{hasta.getTani()}, new String[]{"kisi_id"}, new String[]{Kisi.readId(hasta)+""});
        CRUDShortcuts.executeUpdate(query);
    }

    public static void delete(Hasta hasta) {
        int kisi_id = Kisi.readId(hasta);
        String query = CRUDShortcuts.deleteQuery("Hasta", "kisi_id", kisi_id+"");
        CRUDShortcuts.executeUpdate(query);
    }

}
