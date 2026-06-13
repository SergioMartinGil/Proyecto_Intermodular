import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MetodosDAOGuay {


    //METODOS DE TORNEO
    public static int insertarTorneo(String nombre, String temporada) throws SQLException {

        String sql = "INSERT INTO Torneos(nombre, temporada) VALUES (?,?)";

        Connection conexion = ConexionDB.getConnection();

        PreparedStatement ps = conexion.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setString(1, nombre);
        ps.setString(2, temporada);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1);
        }

        return -1;
    }

    public static int guardarTorneo(String nombre, String temporada) throws SQLException {

        String sql = "INSERT INTO Torneos(nombre, temporada) VALUES (?, ?)";

        Connection conexion = ConexionDB.getConnection();

        PreparedStatement ps = conexion.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setString(1, nombre);
        ps.setString(2, temporada);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1);
        }

        return -1;
    }

    public void visualizarTorneos(Connection conn) {

        String sql = """
            SELECT *
            FROM Torneos
            """;

        try (
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                System.out.println(
                        rs.getInt("id_torneo") + " - " +
                                rs.getString("nombre") + " (" +
                                rs.getString("temporada") + ")"
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    //METODOS DE EQUIPO
    public void insertarEquipo(Connection conn, Equipo equipo) {
        // SQL's preparadas
        String sqlPreparada1 = """
                INSERT INTO Equipos(nombre) VALUES (?)
                """;
        try (
                PreparedStatement insertPreparado = conn.prepareStatement(sqlPreparada1);
        ) {
            insertPreparado.setString(1, equipo.getNombre());
            // INSERT INTO equipo  VALUES (null,nombre,?,?)
            insertPreparado.executeUpdate();
            System.out.println("Inserción correcta");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int guardarEquipo(String nombre) throws SQLException {

        String sql = "INSERT INTO Equipos(nombre) VALUES (?)";

        Connection conexion = ConexionDB.getConnection();

        PreparedStatement ps = conexion.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setString(1, nombre);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1);
        }

        return -1;
    }

    public ArrayList<Equipo> listarEquipos(Connection conn) {
        String sqlPreparada1 = """
                SELECT * 
                FROM Equipos
                """;
        //Instanciamos el array que voy a devolver
        ArrayList<Equipo> Equipos = new ArrayList<>();
        try (PreparedStatement selectPreparado = conn.prepareStatement(sqlPreparada1);
             ResultSet cursor = selectPreparado.executeQuery();) {
            while (cursor.next()) {
                Equipo equipos = new Equipo(
                        cursor.getString("nombre")
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Equipos;
    }

    public void visualizarEquipos(Connection conn) {
        String sqlPreparada2 = """
                SELECT * 
                FROM Equipos
                """;
        try (PreparedStatement selectPreparada = conn.prepareStatement(sqlPreparada2);
             ResultSet cursor = selectPreparada.executeQuery();) {
            while (cursor.next()) {
                Equipo equipos = new Equipo(
                        cursor.getString("nombre")
                );
                System.out.println(equipos);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


}


