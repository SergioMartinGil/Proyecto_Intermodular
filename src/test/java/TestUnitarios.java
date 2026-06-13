import ExcepcionesPersonalizadas.ExcepcionTorneoInvalido;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestUnitarios {

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void afterAll() {

    }

    @BeforeAll
    static void beforeAll() {

    }


    @Test
    public void crearTorneoCorrecto(){

        //Resultado ESPERADO
        Torneo torneo = Torneo.crearTorneo("Liga rusa", "2027");

        //Resultado ACTUAL
        assertEquals("Liga russa", torneo.getNombre());
        assertEquals("2027", torneo.getTemporada());

        System.out.println("Prueba con exitosa");

    }



    public void crearTorneoNombreVacio() {

        Exception exception = assertThrows(
                ExcepcionTorneoInvalido.class,
                () -> Torneo.crearTorneo("", "2027")
        );

        assertEquals(
                "El nombre del torneo no puede estar vacío.",
                exception.getMessage()
        );
    }

    public void crearEquipoCorrecto(){

        //Resultado Esperado
        Equipo equi1 = new Equipo("FranciaFC");

        //Resultado Acutual
        assertEquals("FranciaFC", equi1.getNombre());

        System.out.println("Prueba  exitosa");
    }
}
