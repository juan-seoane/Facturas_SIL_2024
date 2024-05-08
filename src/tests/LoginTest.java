package tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import controladores.fxcontrollers.Acceso;
import javafx.application.Application;
import modelo.base.Config;
import ui.Splash;

public class LoginTest {
	@Test
	public void splashOK(){

		Splash window = new Splash();

        window.run();
        window.setAlwaysOnTop(true);
        window.setVisible(false);
		assertNotNull(window);
	}

	@Test
	public void loginAdminOK(){
		Thread thread = new Thread(() -> {
            Application.launch(Acceso.class);
        });
        thread.start();

		do{
			System.out.println("");
		}
		while ( Config.getConfigActual()==null);

		assertTrue(Config.getConfigActual().usuario.equals("admin"));
	}
/*
	@Test
	void loginDesconocidoCreaCredsOK(){
		assertTrue(/* Credenciales usuario nuevo creadas );
	}

	@Test
	void controladorArrancaOK(){
		assertNotNull(/* Controlador Ppal Obj );
	}

	@Test
	void panelControlArrancaOK(){
		assertNotNull(/* Panel de Control Obj );
	}
*/
}
