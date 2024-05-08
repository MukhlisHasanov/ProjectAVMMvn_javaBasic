package avm.repository;

import avm.products.MovieProduct;

import java.sql.*;
import java.util.*;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/Cinema
 * @author Rodion
 * @version Apr-2024
 */
public class MovieRepository implements ProductRepository<MovieProduct>{
  //  private Map<Integer, MovieProduct> movieMap;

    public String AvmDB;
    private final String SQL_INSERT = "INSERT INTO movie (name, genre, price, quantity) VALUES (?, ?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE movie SET name = ?, genre = ?, price = ?, quantity = ? WHERE id = ?";
    private final String SQL_FIND_BY_ID = "SELECT * FROM movie WHERE id = ?";
    private final String SQL_FIND_ALL = "SELECT * FROM movie";
    private final String SQL_DELETE_BY_ID = "DELETE FROM movie WHERE id = ?";

//    public MovieRepository() {
//   //     movieMap = new HashMap<>();
//    }

    public MovieRepository(String AvmDB) {
        this.AvmDB = AvmDB;
    }

    @Override
    public Collection<MovieProduct> findAll() {
        Collection<MovieProduct> movies = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(AvmDB);
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                movies.add(new MovieProduct(
                        rs.getString("name"),
                        rs.getString("genre"),
                        rs.getFloat("price"),
                        rs.getInt("quantity")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movies;
    }


    @Override
    public void save(MovieProduct movieProduct) {
        try (Connection connection = DriverManager.getConnection(AvmDB)) {
            if (movieProduct.getId() == null) {
                try (PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, movieProduct.getName());
                    ps.setString(2, movieProduct.getGenre());
                    ps.setFloat(3, movieProduct.getPrice());
                    ps.setInt(4, movieProduct.getQuantity());
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            Integer movieProductId = rs.getInt(1);
                            System.out.println(movieProductId);
                        }
                    }
                }
            } else {
                try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
                    ps.setString(1, movieProduct.getName());
                    ps.setString(2, movieProduct.getGenre());
                    ps.setFloat(3, movieProduct.getPrice());
                    ps.setInt(4, movieProduct.getQuantity());
                    ps.setInt(5, movieProduct.getId());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public MovieProduct findById(Integer id) {
        MovieProduct movieProduct = null;
        try (Connection connection = DriverManager.getConnection(AvmDB);
             PreparedStatement psm = connection.prepareStatement(SQL_FIND_BY_ID)) {
            psm.setInt(1, id);
            ResultSet rs = psm.executeQuery();
            if (rs.next()) {
                movieProduct = new MovieProduct(rs.getInt("id"),
                        rs.getString("genre"),
                        rs.getString("name"),
                        rs.getFloat("price"),
                        rs.getInt("quantity"));
            }
            return movieProduct;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection connection = DriverManager.getConnection(AvmDB);
             PreparedStatement ps = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initMovie() {
        List<MovieProduct> movieProducts = new ArrayList<>(List.of(
                new MovieProduct("Avatar", "Fantasy", 12.50f, 200),
                new MovieProduct("Back to the Future 3D", "Fantasy", 10.50f, 200),
                new MovieProduct("Taxi_1", "Comedy", 10, 200),
                new MovieProduct("Taxi_2", "Comedy", 10, 200),
                new MovieProduct("Johnny English", "Comedy", 11, 200),
                new MovieProduct("Crocodile Dundee", "Comedy", 12.50f, 200),
                new MovieProduct("Crocodile Dundee2", "Comedy", 12.50f, 200),
                new MovieProduct("Beethoven", "Comedy", 10, 200),
                new MovieProduct("Beethoven2", "Comedy", 10, 200),
                new MovieProduct("Nu Pogodi", "Animation", 8, 200)
        ));
        movieProducts.forEach(movieProduct -> save(movieProduct));
    }
}
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("\nMovie Repository:\n");
//        movieMap.forEach((Integer, movieProduct) -> {
//            sb.append(movieProduct).append("\n");
//        });
//        return sb.toString();
//    }
//}
