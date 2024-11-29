package com.mycompany.muayene.Model;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mycompany.muayene.DBUtils.CRUDShortcuts;
import com.mycompany.muayene.DBUtils.Mssql;

/**
 *
 * @author Mustafa
 */
@Getter
public class Doktor extends Kisi {
    private String sicilNo;

    public Doktor(String ad, String soyad, long tcKimlikNo, String sicilNo) {
        super(ad, soyad, tcKimlikNo);
        this.sicilNo = sicilNo;
    }

    @Override
    public String toString() {
        return "{" +
                " \"Doktor\":" +
                super.toString() + "," +
                " \"sicilNo\": \"" + sicilNo + '\"' +
                "} ";
    }

    //Static functions
    private static String[] columns = {"sicilNo, kisi_id"};
    public static void create(Doktor doktor) {
        Kisi.create(doktor);
        int id = Kisi.readId(doktor);
        if (id == -1) return;
        String query = CRUDShortcuts.createQuery("Doktor", columns, new String[]{doktor.getSicilNo(), id+""});
        CRUDShortcuts.executeUpdate(query);
    }
    public static List<Doktor> read() {
        List<Doktor> doktors = new ArrayList<>();
        String query =
                "SELECT ad, soyad, tcKimlikno, sicilNo\n" +
                "FROM Kisi\n" +
                "RIGHT JOIN Doktor\n" +
                "ON Doktor.kisi_id = Kisi.id;";
        try {
            Connection connection = Mssql.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                doktors.add(new Doktor(
                        result.getString("ad"),
                        result.getString("soyad"),
                        result.getLong("tcKimlikno"),
                        result.getString("sicilNo")
                ));
            }
            result.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doktors;
    }

    public static Doktor read(long tcKimlikno) {
        String query =
                "SELECT ad, soyad, tcKimlikno, sicilNo\n" +
                "FROM Kisi\n" +
                "RIGHT JOIN Doktor\n" +
                "ON Doktor.kisi_id = Kisi.id\n" +
                "WHERE tcKimlikno="+ tcKimlikno +";";
        try {
            Connection connection = Mssql.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            if (!result.next()) return null;
            String ad = result.getString("ad");
            String soyad = result.getString("soyad");
            String sicilNo = result.getString("sicilNo");
            result.close();
            connection.close();
            return new Doktor(ad, soyad, tcKimlikno, sicilNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void update(Doktor yeniDoktor) {
        Kisi.update(yeniDoktor);
        String query = CRUDShortcuts.updateQuery("Doktor", new String[]{"sicilNo"}, new String[]{yeniDoktor.getSicilNo()}, new String[]{"kisi_id"}, new String[]{Kisi.readId(yeniDoktor)+""});
        CRUDShortcuts.executeUpdate(query);
    }

    public static void delete(Doktor doktor) {
        Kisi.delete(doktor);
        String query = CRUDShortcuts.deleteQuery("Doktor", new String[]{"sicilNo"}, new String[]{doktor.getSicilNo()});
        CRUDShortcuts.execute(query);
    }
}
