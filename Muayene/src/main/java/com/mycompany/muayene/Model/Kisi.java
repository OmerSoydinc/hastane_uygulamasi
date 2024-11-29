package com.mycompany.muayene.Model;

import com.mycompany.muayene.DBUtils.CRUDShortcuts;
import com.mycompany.muayene.DBUtils.Mssql;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Mustafa
 */
@AllArgsConstructor
@Getter
@Setter
public abstract class Kisi {
    private String ad, soyad;
    private long tcKimlikNo;

    @Override
    public String toString() {
        return "{" +
                " \"Kisi\": {" +
                "\"ad\": \"" + ad + '\"' +
                ", \"soyad\": \"" + soyad + '\"' +
                ", \"tcKimlikNo\": " + tcKimlikNo +
                "}" +
                "}";
    }

    //Static functions
    public static String[] columns = {"ad", "soyad", "tcKimlikno"};

    public static void create(Kisi kisi) {
        String query = CRUDShortcuts.createQuery("Kisi", columns, new String[]{kisi.getAd(), kisi.getSoyad(), String.valueOf(kisi.getTcKimlikNo())});
        query = "IF NOT EXISTS (SELECT id FROM Kisi WHERE tcKimlikno = '" + kisi.getTcKimlikNo() + "') " + query;
        CRUDShortcuts.executeUpdate(query);
    }

    public static int readId(Kisi kisi) {
        String query = CRUDShortcuts.readQuery("Kisi", "tcKimlikno", String.valueOf(kisi.getTcKimlikNo()));
        try {
            Connection connection = Mssql.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            if (!result.next()) {
                return -1;
            }
            int id = result.getInt("id");
            result.close();
            connection.close();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void update(Kisi kisi) {
        String query = CRUDShortcuts.updateQuery("Kisi", columns, new String[]{kisi.getAd(), kisi.getSoyad(), String.valueOf(kisi.getTcKimlikNo())}, new String[]{"id"}, new String[]{String.valueOf(readId(kisi))});
        CRUDShortcuts.executeUpdate(query);
    }

    public static void delete(Kisi kisi) {
        String query = CRUDShortcuts.deleteQuery("Kisi", new String[]{"tcKimlikno"}, new String[]{String.valueOf(kisi.getTcKimlikNo())});
    }
    
}
