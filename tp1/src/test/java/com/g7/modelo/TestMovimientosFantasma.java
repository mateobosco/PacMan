package com.g7.modelo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.g7.modelo.ConfiguracionTicks;
import com.g7.modelo.Posicion;
import com.g7.modelo.fantasma.Fantasma;
import com.g7.modelo.fantasma.estados.Configuracion;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Dimension;
import com.g7.modelo.laberinto.Laberinto;

/**
 * Test de movimientos del fantasma en los distintos estados.
 */
public class TestMovimientosFantasma{
	
	private static int ciclosHastaEvolucionDeCazadorNivelUnoACazadorNivelDos = 10;
	private static int ciclosHastaEvolucionDeCazadorNivelDosACazadorNivelTres = 10;
	
	private int numeroAleatorioMAX(int n) {
		return (int) Math.round(100.0*Math.random()) % n;
	}
	
	private Fantasma crearFantasmaNivelUno(Posicion posicion) {
    	ConfiguracionTicks confTicks = new ConfiguracionTicks();
    	Laberinto laberinto = crearLaberintoCompletoDeNPosiciones(10);
        Fantasma unFantasma = new Fantasma(laberinto,posicion,confTicks);
    	return unFantasma;
    }
    
    private Fantasma crearFantasmaNivelDos(Posicion posicion){
        Fantasma unFantasma = crearFantasmaNivelUno(posicion);
        for (int i = 0; i < TestMovimientosFantasma.ciclosHastaEvolucionDeCazadorNivelUnoACazadorNivelDos; i++){
            unFantasma.vivir(new Posicion(9,9));
        }
        return unFantasma;
    }
    
    private Fantasma crearFantasmaNivelTres(Posicion posicion){
    	 Fantasma unFantasma = crearFantasmaNivelDos(posicion);
         for (int i = 0; i < TestMovimientosFantasma.ciclosHastaEvolucionDeCazadorNivelDosACazadorNivelTres; i++){
             unFantasma.vivir(new Posicion(9,9));
         }
         return unFantasma;
    }
    
    private Fantasma crearFantasmaNivelPresa(Posicion posicion) {
        Fantasma unFantasma = crearFantasmaNivelUno(posicion);
    	unFantasma.convertirAPresa();
    	return unFantasma;
    }
    
    private Fantasma crearFantasmaNivelMuerto(Posicion posicion) {
        Fantasma unFantasma = crearFantasmaNivelUno(posicion);
        unFantasma.convertirAPresa();
    	unFantasma.morir();
    	return unFantasma;
    }
    
    private Laberinto crearLaberintoCompletoDeNPosiciones(int cantidad){
		Dimension dim = new Dimension(cantidad, cantidad);
		Laberinto laberinto = new Laberinto(dim);
		LinkedList<Celda> celdas = new LinkedList<Celda>();
		for (int i = 0; i < cantidad ; i++){
			for (int j = 0 ; j < cantidad ; j++ ){
				Posicion posicion = new Posicion(i, j);
				Celda celda = new Celda(posicion);
				laberinto.agregarCelda(celda);
				celdas.add(celda);
			}
		}
		for (Celda celda1 : celdas) {
			for (Celda celda2 : celdas) {
				Posicion pos1 = celda1.posicion();
				Posicion pos2 = celda2.posicion();
				if (pos1.getCoordenadaX() == pos2.getCoordenadaX()-1 && pos1.getCoordenadaY() == pos2.getCoordenadaY()){celda1.setCeldaDerecha(celda2);}
				else if (pos1.getCoordenadaX() == pos2.getCoordenadaX()+1 && pos1.getCoordenadaY() == pos2.getCoordenadaY()){celda1.setCeldaIzquierda(celda2);}
				else if (pos1.getCoordenadaY() == pos2.getCoordenadaY()+1 && pos1.getCoordenadaX() == pos2.getCoordenadaX()){celda1.setCeldaArriba(celda2);}
				else if (pos1.getCoordenadaY() == pos2.getCoordenadaY()-1 && pos1.getCoordenadaX() == pos2.getCoordenadaX()){celda1.setCeldaAbajo(celda2);}
			}
		}
		return laberinto;
    }
	
	@Before
	public void setUp() {
		Configuracion.getConfiguracion();
	}
	
	@Test
    public void fantasmaNivelUnoCambiaPosicion(){
		Posicion unaPosicion = new Posicion(numeroAleatorioMAX(5),numeroAleatorioMAX(5));
        Fantasma unFantasma = crearFantasmaNivelUno(unaPosicion);
        unFantasma.vivir(new Posicion(9,9));
        
        assertFalse(unaPosicion.esIgual(unFantasma.posicion()));
    }
	
	@Test
    public void fantasmaNivelDosCambiaPosicion(){
		Posicion unaPosicion = new Posicion(numeroAleatorioMAX(5),numeroAleatorioMAX(5));
        Fantasma unFantasma = crearFantasmaNivelDos(unaPosicion);   
        
        assertFalse(unaPosicion.esIgual(unFantasma.posicion()));
    }
    
	@Test
    public void fantasmaNivelTresCambiaPosicion(){
		Posicion unaPosicion = new Posicion(numeroAleatorioMAX(3),numeroAleatorioMAX(3));
        Fantasma unFantasma = crearFantasmaNivelTres(unaPosicion);   
        
        assertFalse(unaPosicion.esIgual(unFantasma.posicion()));
    }
    
	@Test
    public void fantasmaPresaCambiaPosicion(){
		Posicion unaPosicion = new Posicion(numeroAleatorioMAX(5),numeroAleatorioMAX(5));
        Fantasma unFantasma = crearFantasmaNivelPresa(unaPosicion);
        unFantasma.vivir(new Posicion(9,9));
        
        assertFalse(unaPosicion.esIgual(unFantasma.posicion()));
    }
    
	@Test
    public void fantasmaMuertoVaALaPosicionInicialFantasma(){
		Posicion unaPosicion = new Posicion(0,0);
        Fantasma unFantasma = crearFantasmaNivelMuerto(new Posicion(numeroAleatorioMAX(5),numeroAleatorioMAX(5)));
        unFantasma.vivir(new Posicion(9,9));
        
        assertTrue(unaPosicion.esIgual(unFantasma.posicion()));
    }
    
}
