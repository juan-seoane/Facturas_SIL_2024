/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.records;

import java.util.Date;
import java.util.GregorianCalendar;

import controladores.Controlador;

/**
 *
 * @author Juan Seoane
 */
public record EntradaCaja(int ID, Fecha fecha, Date date, String origen, double caja, boolean haber, Nota nota) implements Comparable<EntradaCaja> {
/* TODO: 11-04-2024 - ¿Qué sentido tiene crear una entrada permanente (final) con datos temporales?
    public EntradaCaja() {
        this(0, new Fecha(1,1,Config.getConfig(Controlador.usuario).año().año()), new GregorianCalendar().getTime(), new Concepto("ORIGEN").origen(), 0.00, true, new Nota(0,""));
    }
*/
    public int compareTo(EntradaCaja b){
        return (this.fecha().compareTo(b.fecha()));
    }
}
