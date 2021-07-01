package test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import logica.Empresa;

public class TestTP2 {
	Empresa emp;
	double carga, ctoViaje;

	@Before
	public void setUp() throws Exception {
		emp = new Empresa("30112223334","La Santafesina");
		emp.agregarDestino("Cordoba", 350);
		emp.agregarDestino("Corrientes", 900);
		emp.agregarDestino("Parana", 30);
		emp.agregarDestino("Jujuy", 1200);
		emp.agregarDestino("Buenos aires",300);
	}
	
	@Test
	public void testCargarDepTercerizFrio() {
		emp.agregarDepTercerizFrio(23, 50);
		emp.agregarDepTercerizFrio(40, 25);
		emp.agregarTrailer("AC314PI", 12000, 60, true, 5, 100);
		emp.asignarDestino("AC314PI", "Cordoba");
		emp.incorporarPaquete("Cordoba", 100, 5, true);
		emp.incorporarPaquete("Cordoba", 250, 10, true);
		emp.incorporarPaquete("Cordoba", 150, 8, true);
		emp.incorporarPaquete("Cordoba", 50, 2.5, false);
		emp.incorporarPaquete("Cordoba", 300, 15, true);
		emp.incorporarPaquete("Cordoba", 400, 12, true);
		emp.incorporarPaquete("Cordoba", 125, 5, false);
		carga = emp.cargarTransporte("AC314PI");
		emp.iniciarViaje("AC314PI");
		ctoViaje = emp.obtenerCostoViaje("AC314PI");
		
		assertEquals(ctoViaje,1889.5,10.0);
	}
	
	@Test
	public void testEnviarTranspReemplazo() {
		emp.agregarDeposito(40000, false, false);
		emp.incorporarPaquete("Parana", 100, 5, false);
		emp.incorporarPaquete("Parana", 400, 12, false);
		emp.incorporarPaquete("Parana", 50, 8, false);
		//camion averiado
		emp.agregarFlete("AB271NE", 8000, 30, 3, 2, 200);
		emp.asignarDestino("AB271NE", "Parana");
		emp.cargarTransporte("AB271NE");
		emp.iniciarViaje("AB271NE");	
		//otro vacio igual
		emp.agregarFlete("AD235NP",9000, 40, 10, 3, 100);
		emp.asignarDestino("AD235NP","Parana");
		
		assertEquals(emp.enviarTranspReemplazo("AB271NE"),"AD235NP");
	}
	
	@Test
	public void testNoSeEncontroReemplazo() {
		emp.agregarDepTercerizFrio(10000,12);
		emp.incorporarPaquete("Corrientes", 100, 5, true);
		emp.incorporarPaquete("Corrientes", 400, 12,true);
		emp.incorporarPaquete("Corrientes", 50, 8,true);
		//transporte averiado
		emp.agregarMegaTrailer("AD161AU", 18000, 120, true, 10, 150, 500, 300);
		emp.asignarDestino("AD161AU", "Corrientes");
		emp.cargarTransporte("AD161AU");
		emp.iniciarViaje("AD161AU");
		//otro transporte con cargaMaxima menor
		emp.agregarMegaTrailer("AA444PR", 17999, 120, true, 10, 150, 500, 300);
		emp.asignarDestino("AA444PR","Corrientes");
		//otro transporte con capacidad menor
		emp.agregarMegaTrailer("BB6688I", 18000, 119, true, 10, 150, 500, 300);
		emp.asignarDestino("BB6688I","Corrientes");
		//otro transporte con distinto tipo de frio
		emp.agregarMegaTrailer("XC4242Y", 18000, 120,false, 10, 150, 500, 300);
		emp.asignarDestino("XC4242Y","Corrientes");
		//otro transporte con distinto destino
		emp.agregarMegaTrailer("AB555MN", 18000, 120,true, 10, 150, 500, 300);
		emp.asignarDestino("AB555MN","Jujuy");
		assertEquals(emp.enviarTranspReemplazo("AD161AU"),null);
	}
	
	@Test
	public void testcantTranspEnViaje() {
		emp.agregarDeposito(30000, false, false);
		emp.agregarDepTercerizFrio(80000, 50);
		emp.incorporarPaquete("Cordoba", 100, 5, true);
		emp.incorporarPaquete("Cordoba", 200, 5, true);
		emp.incorporarPaquete("Jujuy", 100, 2, false );
		emp.incorporarPaquete("Parana", 250, 10,false);
		emp.incorporarPaquete("Corrientes", 150, 1, true);
		emp.incorporarPaquete( "Buenos aires", 450, 3, true);
		//TrailerComun
		emp.agregarTrailer("AC314PI", 1000, 50, true, 5, 100);
		emp.asignarDestino("AC314PI", "Cordoba");
		emp.cargarTransporte("AC314PI");
		emp.iniciarViaje("AC314PI");
		//megaTrailer
		emp.agregarMegaTrailer("SD5435U", 10000,200, false, 10,100,5,50);
		emp.asignarDestino("SD5435U", "Jujuy");
		emp.cargarTransporte("SD5435U");
		emp.iniciarViaje("SD5435U");
		//Flete
		emp.agregarFlete("AB555MT", 5000, 20, 4, 2, 300);
		emp.asignarDestino("AB555MT", "Parana");
		emp.cargarTransporte("AB555MT");
		emp.iniciarViaje("AB555MT");
		//megaTrailer que no esta en viaje
		emp.agregarMegaTrailer("FG5435Y", 10000,200, true, 10,100,5,50);
		emp.asignarDestino("FG5435Y", "Corrientes");
		emp.cargarTransporte("FG5435Y");
		//TrailerComun
		emp.agregarTrailer("ERW5454", 1000, 50, true, 5, 100);
		emp.asignarDestino("ERW5454", "Buenos aires");
		emp.cargarTransporte("ERW5454");
		emp.iniciarViaje("ERW5454");
	
		assertEquals("{Fletes=1, Mega trailer=1, Trailer Comun=2}",
				emp.cantTranspEnViaje());
	}
	
	@Test
	public void testcantPaquetesEnDepFrio() {
		emp.agregarDepTercerizFrio(8,12);
		emp.incorporarPaquete("Cordoba", 100, 2, true );
		emp.incorporarPaquete("Cordoba", 150, 1, true );
		emp.incorporarPaquete("Corrientes", 100, 2, true );
		emp.incorporarPaquete("Jujuy", 100, 2, true );
		emp.incorporarPaquete("Jujuy", 150, 1, true );
		//dep propio
		emp.agregarDeposito(40, true, true);
		emp.incorporarPaquete("Parana", 100, 5, true);
		emp.incorporarPaquete("Parana", 250, 10, true);
		emp.incorporarPaquete("Parana", 150, 8, true);
		emp.incorporarPaquete("Jujuy", 400, 12, true);
		//dep propio
		emp.agregarDeposito(60, true, true);
		emp.incorporarPaquete("Cordoba", 250, 10, true);
		emp.incorporarPaquete("Parana", 150, 8, true);
		emp.incorporarPaquete("Cordoba", 400, 12, true);

		assertEquals("{Depositos propios=7, Depositos tercerizados=5}",
				emp.cantPaqEnDepFrio());
	}
	
}
