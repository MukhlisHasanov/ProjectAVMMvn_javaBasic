package avm.repository;

import avm.products.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * AIT-TR, cohort 42.1, Java Basic, Project AVM/General
 * @author Andrey Hein
 * @version Apr-2024
 */
public class ClientRepository {
    private String dbName;

    private final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS client (" +
            " id          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " name        TEXT NOT NULL," +
            " age       INTEGER NOT NULL," +
            " wallet    FLOAT NOT NULL)";
    private final String SQL_INSERT = "INSERT INTO client (name, age, wallet) VALUES (?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE client SET name = ?, age = ?, wallet = ? WHERE id = ?";
    private final String SQL_FIND_BY_ID = "SELECT * FROM client WHERE id = ?";
    private final String SQL_FIND_ALL = "SELECT * FROM client";
    private final String SQL_DELETE_BY_ID = "DELETE FROM client WHERE id = ?";

    public ClientRepository(String dbName) {
        this.dbName = dbName;
    }

    public void save(Client client) {
        try (Connection connection = DriverManager.getConnection(dbName);
             PreparedStatement psi = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psu = connection.prepareStatement(SQL_UPDATE)) {
            if (client.getId() == null) {
                // insert record
                psi.setString(1, client.getName());
                psi.setInt(2, client.getAge());
                psi.setFloat(3, client.getWallet());
                psi.executeUpdate();

                ResultSet rs = psi.getGeneratedKeys();
                if (rs.next()) {
                    client.setId(rs.getInt(1));
                }
            } else {
                // update record
                psu.setString(1, client.getName());
                psu.setInt(2, client.getAge());
                psu.setFloat(3, client.getWallet());
                psu.setInt(4, client.getId());
                psu.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Client findById(Integer id) {
        Client client = null;
        try (Connection connection = DriverManager.getConnection(dbName);
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                client = new Client(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getFloat("wallet"));
            }
            return client;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Integer id) {
        try (Connection connection = DriverManager.getConnection(dbName);
             PreparedStatement ps = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public void initClient() {
//        List<Client> clients = new ArrayList<>(List.of(
//                new Client("Alexander", 56, 1000),
//                new Client("Andrey", 50, 1000),
//                new Client("Valerian", 38, 1000),
//                new Client("Mukhlis", 29, 1000),
//                new Client("Rodion", 24, 1000),
//                new Client("Sergey", 45, 1000)
//        ));
//        clients.forEach(client -> save(client));
//    }

    public Collection<Client> findAll() {
        Collection<Client> clients = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbName);
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                clients.add(new Client(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getFloat("wallet")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clients;
    }
}

//    public void put(Client client) {
//        clientMap.put(client.getId(),client);
//    }
//
//    public Client get(int id) {
//        return clientMap.get(id);
//    }
//
//    public void remove(int id) {
//        clientMap.remove(id);
//    }
//
//    public void initClient() {
//        List<Client> clients = new ArrayList<>(List.of(
//                new Client("Alexander", 56, 1000),
//                new Client("Andrey", 50, 1000),
//                new Client("Valerian", 38, 1000),
//                new Client("Mukhlis", 29, 1000),
//                new Client("Rodion", 24, 1000),
//                new Client("Sergey", 45, 1000)
//        ));
//        clients.forEach(client -> put(client));
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("\nClient list: \n");
//        clientMap.forEach((Integer, client) -> {
//            sb.append(client).append("\n");
//        });
//        return sb.toString();
//    }
//}