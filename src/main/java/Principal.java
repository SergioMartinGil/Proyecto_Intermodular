import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Principal {
    public static void main(String[] args) {

        System.out.println("Proyecto de futboleros");
        MetodosDAOGuay torneobd = new MetodosDAOGuay();
        Torneo t1 = new Torneo("Liga", "2025");
        MetodosDAOGuay daoguay = new MetodosDAOGuay();
        try(
                Connection conn = ConexionDB.getConnection();
                ){
            System.out.println("Conexion con la base de datos Correcta!");
            ArrayList<Equipo> equipos = daoguay.listarEquipos(conn);
            for(Equipo a : equipos){
                System.out.println(a);
            }

            //Insertar Equipo

            Equipo futboleroFC = new Equipo("AlcorconFC");
            Equipo cdm = new Equipo("CDMFPFUTBOL");
            daoguay.insertarEquipo(conn, cdm);
            System.out.println("CDM encontrado!");
            daoguay.visualizarEquipos(conn);

            Torneo t2 = new Torneo("liga2", "2028");
            t1.mostrarClasificacion();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
