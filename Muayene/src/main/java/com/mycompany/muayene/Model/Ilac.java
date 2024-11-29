package com.mycompany.muayene.Model;

import com.mycompany.muayene.DBUtils.CRUDShortcuts;
import com.mycompany.muayene.DBUtils.Mssql;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Mustafa
 */
@Getter
@AllArgsConstructor
public class Ilac {

    private String ilacAdi;

    @Override
    public String toString() {
        return "{" +
                " \"ilacAdi\": \"" + ilacAdi + '\"' +
                "} ";
    }


    //Static field

    public static String[] columns = {"ilacAdi"};

    public static void create(Ilac ilac) {
        String[] values = {ilac.ilacAdi};
        String query = CRUDShortcuts.createQuery("Ilac", columns, values);
        CRUDShortcuts.execute(query);
    }

    public static List<Ilac> read() {
        List<Ilac> ilaclar = new ArrayList<>();
        Connection connection = Mssql.getConnection();
        String query = CRUDShortcuts.readQuery("Ilac");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ilaclar.add(new Ilac(
                        resultSet.getString("ilacAdi")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ilaclar;
    }

    public static List<Ilac> readIlaclarFromReceteNo(String receteNo) {
        List<Ilac> ilaclar = new ArrayList<>();
        Connection connection = Mssql.getConnection();
        String query = "SELECT * FROM YazilanIlaclar WHERE YazilanIlaclar.receteNo= " + receteNo;
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            List<String> ilacIDs = new ArrayList<>();
            while (result.next()) {
                ilacIDs.add(result.getString("ilac_id"));
            }
            statement.close();
            result.close();
            for (String ilacID : ilacIDs) {
                query = "SELECT Ilac.ilacAdi FROM Ilac WHERE id = " + ilacID;
                statement = connection.createStatement();
                result = statement.executeQuery(query);
                if (result.next()) {
                    ilaclar.add(new Ilac(result.getString("ilacAdi")));
                }
            }
            statement.close();
            result.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return ilaclar;
    }
    public static Map<Integer, Ilac> readIlacMap() {
        Map<Integer, Ilac> ilaclar = new HashMap<>();
        Connection connection = Mssql.getConnection();
        String query = CRUDShortcuts.readQuery("Ilac");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ilaclar.put(
                        resultSet.getInt("id"),
                        new Ilac(resultSet.getString("ilacAdi")
                ));
            }
            statement.close();
            connection.close();
        } catch (Exception e) { e.printStackTrace(); }

        return ilaclar;
    }

    public static void delete(Ilac ilac) {
        String[] whereValues = {ilac.ilacAdi};
        String query = CRUDShortcuts.deleteQuery("Ilac", columns, whereValues);
        CRUDShortcuts.execute(query);
    }
}
