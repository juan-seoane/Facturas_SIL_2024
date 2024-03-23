import ocr.OcrApp;
/*
 * Se trata de una prueba en la que se extrae solamente el total de la factura en .png
 * Hubo que ajustar las dimensiones un par de veces para que no hubiera errores
 */
/*TODO: esta prueba parce indicar la necesidad de una Base de Datos donde se registren los tipos de facturas 
y las coordenadas a escanear y el tipo de dato que se extrae, ademas de una GUI que se encargue de ello */
public class OcrTest {
    
    static String respuesta;
    static OcrApp nuevoTrabajo;

    //ruta de la imagen
    static String ruta = "escaneos/test1.png";

    // Coordenadas y medidas del trozo a escanear
    static int stX = 600;
    static int stY = 550;
    static int wi  = 85;
    static int hei = 50;

    public static void main(String[] args) {

        OcrTest.nuevoTrabajo = new OcrApp(OcrTest.ruta, OcrTest.stX, OcrTest.stY, OcrTest.wi, OcrTest.hei); 
        OcrTest.respuesta = OcrTest.nuevoTrabajo.getOCRResult();
        System.out.println("Documento Extraído:");
        System.out.println("-------------------");
        System.out.println(OcrTest.respuesta);

        System.exit(0);
    }

}
