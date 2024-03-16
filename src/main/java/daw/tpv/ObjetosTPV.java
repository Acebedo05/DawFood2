package daw.tpv;

/**
 * Clase que contiene un método para inicializar un TPV.
 * @author acebedo
 */
public class ObjetosTPV {

    // Método para inicializar un TPV.
    public static FuncionesTPV inicializarTPV() {
        // Crear instancias de AtributosTPV y FuncionesTPV
        AtributosTPV atributosTPV = new AtributosTPV("Estepona", null);
        
        // Crear una instancia de FuncionesTPV con los atributos recién creados.
        FuncionesTPV funcionesTPV = new FuncionesTPV(atributosTPV);

        // Devolver la instancia de FuncionesTPV recién creada.
        return funcionesTPV;
    }

}
