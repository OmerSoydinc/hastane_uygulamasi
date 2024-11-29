package com.mycompany.muayene.DBUtils;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Mustafa
 */
public class Mssql {
    private static String databaseName = "Hastane";
    private static String connectionURL = "jdbc:sqlserver://localhost;databaseName="+databaseName+";integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
    private static String userName = "test";
    private static String password = "123456";
    private static String firstConnectionURL = "jdbc:sqlserver://localhost;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

    public static void makeFirstConnection() {
        try {
            if (!SQLServerDriver.isRegistered()) SQLServerDriver.register();
            Connection connection = DriverManager.getConnection(firstConnectionURL, userName, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM master.dbo.sysdatabases;");
            boolean databaseExists = false;
            while (resultSet.next()) {
                if (resultSet.getString("name").equals(databaseName)) {
                    databaseExists = true;
                    break;
                }
            }
            if (!databaseExists){
                createDatabase(connection);
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("\nVeritabanına bağlanılamadı.\n\n\n");
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {
        try {

            if (!SQLServerDriver.isRegistered()) SQLServerDriver.register();
            return DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    

    private static void createDatabase(Connection connection) {
        List<String> queries = new ArrayList<>();
        queries.add("CREATE DATABASE "+ databaseName + ";");
        queries.add("USE "+ databaseName + ";");
        queries.add("CREATE TABLE [dbo].[Doktor](\n" +
                "\t[id] [int] IDENTITY(0,1) NOT NULL,\n" +
                "\t[sicilNo] [varchar](50) NOT NULL,\n" +
                "\t[kisi_id] [int] NOT NULL,\n" +
                " CONSTRAINT [doktor_id] PRIMARY KEY CLUSTERED \n" +
                "(\n" +
                "\t[id] ASC\n" +
                ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]\n" +
                ") ON [PRIMARY];");
        queries.add("CREATE TABLE [dbo].[Hasta](\n" +
                "\t[id] [int] IDENTITY(0,1) NOT NULL,\n" +
                "\t[kisi_id] [int] NOT NULL,\n" +
                "\t[tani] [varchar](255) NULL,\n" +
                "\t[receteNo] [int] NULL\n" +
                ") ON [PRIMARY];");
        queries.add("CREATE TABLE [dbo].[Ilac](\n" +
                "\t[id] [int] IDENTITY(0,1) NOT NULL,\n" +
                "\t[ilacAdi] [varchar](50) NOT NULL,\n" +
                " CONSTRAINT [ilac_id] PRIMARY KEY CLUSTERED \n" +
                "(\n" +
                "\t[id] ASC\n" +
                ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]\n" +
                ") ON [PRIMARY];");
        queries.add("CREATE TABLE [dbo].[Kisi](\n" +
                "\t[id] [int] IDENTITY(0,1) NOT NULL,\n" +
                "\t[ad] [varchar](50) NOT NULL,\n" +
                "\t[soyad] [varchar](50) NOT NULL,\n" +
                "\t[tcKimlikno] [bigint] NOT NULL,\n" +
                " CONSTRAINT [kisi_id] PRIMARY KEY CLUSTERED \n" +
                "(\n" +
                "\t[id] ASC\n" +
                ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]\n" +
                ") ON [PRIMARY];");
        queries.add("CREATE TABLE [dbo].[Recete](\n" +
                "\t[receteNo] [int] IDENTITY(0,1) NOT NULL,\n" +
                "\t[receteTarihi] [datetime] NOT NULL,\n" +
                "\t[onaylandi] [bit] NOT NULL,\n" +
                " CONSTRAINT [receteNo] PRIMARY KEY CLUSTERED \n" +
                "(\n" +
                "\t[receteNo] ASC\n" +
                ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]\n" +
                ") ON [PRIMARY];");
        queries.add("CREATE TABLE [dbo].[YazilanIlaclar](\n" +
                        "\t[ilac_id] [int] NOT NULL,\n" +
                        "\t[receteNo] [int] NOT NULL\n" +
                        ") ON [PRIMARY];");
        try {
            for (String query : queries) {
                Statement statement = connection.createStatement();
                statement.execute(query);
                statement.close();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
