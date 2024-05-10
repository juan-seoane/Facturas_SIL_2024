package controladores;

import modelo.base.Config;
import modelo.base.FicheroAutomatico;
import ui.ventanas.VentanaAutosave;
import ui.visores.VisorNotas;
import controladores.fxcontrollers.Acceso;
import controladores.fxcontrollers.PanelControl;

import java.io.IOException;
import javax.swing.JOptionPane;

public class Controlador extends Thread {
    
	public static final int NAV = 0;
    public static final int INGR = 1;
        
    public static final int FACT = 1;
    public static final int DIST = 2;
    public static final int CAJA = 3;
    public static final int NOTAS = 4;
    public static final int CONFIG = 5;
        
//	  private static ControladorFicheros cfch;
    private static ControladorFacturas cfct;
   
//    private static ControladorDistribuidores cd;
//    private static ControladorCaja ccj;
    public static PanelControl pc;
    private static VisorNotas notas;
    public static int seccion = FACT;

    public static String usuario;

// TODO: 09/04/24 - Reorganizar los hilos que genera (ControladorFCT, ControladorDIST, etc...)
// TODO: 09/04/24 - Consultar cómo se puede hacer Singleton	
    public Controlador() throws IOException{
        Controlador.usuario = Acceso.getUsuario();
        System.out.println("El usuario es "+ Controlador.usuario);
        if(Config.getConfig(Controlador.usuario)!=null){
            System.out.println("Procediendo a arrancar el Controlador de Facturas del Usuario "+ Controlador.usuario);
            arrancarCfct();
 /*           
            cd = ControladorDistribuidores.getControlador();
            cd.start();
            ccj.start();
            
            //TODO: 06/04/2024 Tuve que cambiar el controlador de la clase PanelControl a public       
            pc.setVisible(true);
*/
        }

	}

    private ControladorFacturas arrancarCfct() {
        
        Controlador.cfct = new ControladorFacturas();
        Controlador.cfct.setName("Contr_FCT");
        Controlador.cfct.start();

        return Controlador.cfct;
    }
    /*
    public ControladorFicheros getControladorFicheros(){
	
		return Controlador.cfch;
	}
	
	public ControladorFacturas getControladorFacturas(){
	
		return Controlador.cfct;
	}
        
    public ControladorCaja getControladorCaja(){
	
		return Controlador.ccj;
	}
*/        
    public static void reset() throws IOException{
            
        if(Config.getConfig(Controlador.usuario)!=null){
            cfct = ControladorFacturas.getControlador();
//            cd = ControladorDistribuidores.getControlador();
//            ccj = ControladorCaja.getControlador();
            pc = PanelControl.getPanelControl();
//            pc.setVisible(true);
            
        }
    }
/*        
    public static void verNotas(){
        seccion = NOTAS;
        notas = new VisorNotas();
        notas.getpanelfacturas().getpanelprincipal().setLayout(new GridBagLayout());
        notas.getpaneldistribuidores().getpanelprincipal().setLayout(new GridBagLayout());
        notas.getpanelCajas().getpanelprincipal().setLayout(new GridBagLayout());
        
        ArrayList<Button> botones = new ArrayList<Button>();
        
        for (final Factura f : new Fichero<Factura>(Config.getConfig().getRutaFCT()).leer()){
            String nota = f.getNota().getTexto().toString();
            //JOptionPane.showMessageDialog(null,"Nota "+f.getID()+" "+nota);
            if (!nota.trim().toUpperCase().equals("NUEVA NOTA") & !nota.trim().toUpperCase().equals("")){
                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                panel.add(f.getNota().format(5,30),BorderLayout.CENTER);
                Button nuevoboton = new Button("FCT "+f.getID());
                botones.add(nuevoboton);
                panel.add(nuevoboton,BorderLayout.EAST);
                notas.getpanelfacturas().getpanelprincipal().add(panel);
            }
        }            
        for (final RazonSocial rs : new Fichero<RazonSocial>(Config.getConfig().getRutaRS()).leer()){
            String nota = (rs.getNota().getTexto().toString()!=null?rs.getNota().getTexto().toString():"");
            //JOptionPane.showMessageDialog(null,"Nota "+f.getID()+" "+nota);
            if (!nota.trim().toUpperCase().equals("NUEVA NOTA") & !nota.trim().toUpperCase().equals("")){
                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                panel.add(rs.getNota().format(5,30),BorderLayout.CENTER);
                Button nuevoboton = new Button("RS "+ rs.getID());
                botones.add(nuevoboton);
                panel.add(nuevoboton,BorderLayout.EAST);
                notas.getpaneldistribuidores().getpanelprincipal().add(panel);
            }
        }
        for (final Caja c : new Fichero<Caja>(Config.getConfig().getRutaCJA()).leer()){
            String nota = (c.getNota().getTexto().toString()!=null?c.getNota().getTexto().toString():"");
            //JOptionPane.showMessageDialog(null,"Nota "+f.getID()+" "+nota);
            if (!nota.trim().toUpperCase().equals("NUEVA NOTA") & !nota.trim().toUpperCase().equals("")){
                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                panel.add(c.getNota().format(5,30),BorderLayout.CENTER);
                Button nuevoboton = new Button("CJA "+ c.getID());
                botones.add(nuevoboton);
                panel.add(nuevoboton,BorderLayout.EAST);
                notas.getpanelCajas().getpanelprincipal().add(panel);
            }
        }
        
        for (Button b : botones){
            b.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                            //JOptionPane.showMessageDialog(null,"Ha seleccionado ver la factura "+((Button)ae.getSource()).getLabel().trim());
                            if (((Button)ae.getSource()).getLabel().trim().split(" ")[0].equals("FCT")){
                                int index = Integer.parseInt(((Button)ae.getSource()).getLabel().trim().split(" ")[1]);
                                index--;
                                verFactura(index);
                            }
                            if (((Button)ae.getSource()).getLabel().trim().split(" ")[0].equals("RS")){
                                int index = Integer.parseInt(((Button)ae.getSource()).getLabel().trim().split(" ")[1]);
                                index--;
//                                    System.out.println("indice de la razon social seleccionada: "+index);
                                verDistribuidor(index);
                            }
                            if (((Button)ae.getSource()).getLabel().trim().split(" ")[0].equals("CJA")){
                                int index = Integer.parseInt(((Button)ae.getSource()).getLabel().trim().split(" ")[1]);
                                index--;
//                                    System.out.println("indice de la razon social seleccionada: "+index);
                                verCaja(index);
                            }
                        }
                });
        }
        notas.pack();
        notas.setVisible(true);
    }
    
    public static void verFactura(int index){
        PanelControl.pulsarboton(1);
        seccion = FACT;
/*        notas.dispose();
        cfct.visible(true);
        cfct.visorVisible(true);
        cfct.actualizarVisor(index); 
    }
    
    public static void verDistribuidor(int index){
        PanelControl.pulsarboton(2);
        seccion = DIST;
 /*       notas.dispose();
        cd.visible(true);
        cd.verRS(index); 
    }
    
    public static void verCaja(int index){
        PanelControl.pulsarboton(2);
        seccion = CAJA;
        notas.dispose();
        ccj.visible(true);
        ccj.verCaja(index); 
    }
*/
    @Override
    public void run() {
        
        while(true){
            try {
                if (!PanelControl.getPanelControl().botonpulsado()){
                    System.out.print("");
                }
                else if (PanelControl.getPanelControl().botonpulsado()){
                    switch (PanelControl.getPanelControl().seleccion()){
                        case 1 :
                            seccion = FACT;
//                        cfct.visible(true);
//                        ControladorDistribuidores.setEstado(0);
//                        cd.visible(false);
//                        ccj.visible(false);
                            PanelControl.reset();
                            break;
                        case 2 :
                            seccion = DIST;
//                        cfct.visible(false);
//                        ControladorDistribuidores.setEstado(1);
//                        cd.visible(true);
//                        ccj.visible(false);
                            PanelControl.reset();
                            break;
                        case 3 :
                            seccion = NOTAS;
//                        cfct.visible(false);
//                        ControladorDistribuidores.setEstado(0);
//                        cd.visible(false);
//                        ccj.visible(false);
//                        verNotas();                            
                            PanelControl.reset();
                            break;
                        case 4 :
                            seccion = CONFIG;
//                        cfct.visible(false);
//                        ControladorDistribuidores.setEstado(0);
//                        cd.visible(false);
//                        ccj.visible(false);
//                        VentanaConfig vc = new VentanaConfig();
//                        vc.setVisible(true);
                            PanelControl.reset();
                            break;
                        case 5:
                            seccion = CAJA;
//                        ccj.visible(true);
//                        cfct.visible(false);
//                        ControladorDistribuidores.setEstado(0);
//                        cd.visible(false);                           
                            PanelControl.reset();
                            break;
                        case 6:  
//                            autosave();
                            PanelControl.reset();
                            break;
                        case 7:
                            if (PanelControl.getModo() == NAV){
                                if (seccion == FACT){
//                                cfct.visible(true);
                                }
                            }
                            else {
                                if (seccion == FACT){
//                                cfct.visible(true);
                                }
                            }
                            PanelControl.reset();
                            break;
                        default:
                            PanelControl.reset();
                            break;       
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
    }
        
    private void autosave() throws NullPointerException, IOException{
        
        boolean[] opciones =  new boolean[3];

        VentanaAutosave v = new VentanaAutosave();
        v.setVisible(true);
        while(!v.ok()){
                System.out.println("");
            }
        if (v.fact()) {opciones[0] = true;System.out.println("fact");} else {opciones[0] = false;}
        if (v.dist()) {opciones[1] = true;System.out.println("dist");} else {opciones[1] = false;}
        if (v.caja()) {opciones[2] = true;System.out.println("caja");} else {opciones[2] = false;}
        v.dispose();  

       if (opciones[0] || opciones[1] || opciones[2])
           copiaseguridad(opciones);
    }
    
    private boolean copiaseguridad(boolean[] opciones) throws NullPointerException, IOException{
        FicheroAutomatico auto = new FicheroAutomatico(opciones, Config.getConfig(Controlador.usuario).getUsuario(),Config.getConfig(Controlador.usuario).configData.getAño().getAño(),Config.getConfig(Controlador.usuario).configData.getAño().getTrimestre());
        String [] rutas = auto.getRutas();
        System.out.println(rutas[0]);
        System.out.println(rutas[1]);
        System.out.println(rutas[2]);
        
        if (opciones[0])
//            cfct.autosave(rutas[0]);
        if (opciones[1])
//            cd.autosave(rutas[1]);
        if (opciones[2])
//            ccj.autosave(rutas[2]);
        
        JOptionPane.showMessageDialog(null, "Los ficheros automáticos han sido guardados en: \n"+rutas[0]+"\n"+rutas[1]+"\n"+rutas[2]);
        return true;
    }

    public static void quit(){
        System.exit(0);
    }
}