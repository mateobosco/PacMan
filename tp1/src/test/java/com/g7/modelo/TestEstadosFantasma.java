package com.g7.modelo;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.*;

import com.g7.modelo.ConfiguracionTicks;
import com.g7.modelo.Posicion;
import com.g7.modelo.fantasma.Fantasma;
import com.g7.modelo.fantasma.estados.Cazador;
import com.g7.modelo.fantasma.estados.Configuracion;
import com.g7.modelo.fantasma.estados.EstadosFantasma;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Dimension;
import com.g7.modelo.laberinto.Laberinto;

/**
 * Test de cambios de estado de los fantasmas
 */
public class TestEstadosFantasma{
	private static int ciclosHastaEvolucionDeCazadorNivelUnoACazadorNivelDos = 20;
	private static int ciclosHastaEvolucionDeCazadorNivelDosACazadorNivelTres = 20;
	private static int ciclosHastaEvolucionDePresaACazadorNivelUno = 40;
	private static int ciclosHastaEvolucionDeMuertoACazadorNivelUno = 10;
	
	@Before
	public void setUp() {
		Configuracion.getConfiguracion();
	}
	
    @Test
    public void fantasmaCreadoSeEncuentraEnEstadoCazadorUno() {
        Fantasma unFantasma = crearFantasmaEnNivelUno();
        
        assertEquals(EstadosFantasma.CAZADOR_NIVEL_UNO, ((Cazador)unFantasma.getEstado()).getDescripcionEspecifica() );
    }
    
    @Test
    public void fantasmaCazadorNivelUnoSeConvierteANivelDosLuegoDe10Ciclos(){
        Fantasma unFantasma = crearFantasmaEnNivelUno();
        
        for (int i = 0; i < TestEstadosFantasma.ciclosHastaEvolucionDeCazadorNivelUnoACazadorNivelDos; i++){
            unFantasma.vivir(new Posicion(0,0));
        }
        assertEquals(EstadosFantasma.CAZADOR_NIVEL_DOS, ((Cazador)unFantasma.getEstado()).getDescripcionEspecifica() );
    }
    
    @Test
    public void fantasmaCazadorNivelDosSeConvierteANivelTresLuegoDe10Ciclos(){
        Fantasma unFantasma = crearFantasmaEnNivelDos();
        
        for (int i = 0; i < TestEstadosFantasma.ciclosHastaEvolucionDeCazadorNivelDosACazadorNivelTres; i++){
        	unFantasma.vivir(new Posicion(0,0));
        }
        assertEquals(EstadosFantasma.CAZADOR_NIVEL_TRES, ((Cazador)unFantasma.getEstado()).getDescripcionEspecifica() );
    }
    
    
    @Test
    public void fantasmaCazadorNivelUnoSeConvierteAPresa() {
        Fantasma unFantasma = crearFantasmaEnNivelUno();
        
        unFantasma.convertirAPresa();
        
        assertEquals(EstadosFantasma.PRESA, unFantasma.estado() );
    }
    
    @Test
    public void fantasmaCazadorNivelDosSeConvierteAPresa(){
        Fantasma unFantasma = crearFantasmaEnNivelDos();
        
        unFantasma.convertirAPresa();
        
        assertEquals(EstadosFantasma.PRESA, unFantasma.estado() );
    }

    @Test
    public void fantasmaCazadorNivelTresSeConvierteAPresa(){
        Fantasma unFantasma = crearFantasmaEnNivelTres();
        
        unFantasma.convertirAPresa();
        
        assertEquals(EstadosFantasma.PRESA, unFantasma.estado() );
    }    
    
    @Test
    public void fantasmaPresaSeConvierteANivelUnoLuegoDe40Ciclos(){
        Fantasma unFantasma = crearFantasmaEnNivelUno();
        
        unFantasma.convertirAPresa();
        
        for(int i = 0; i < TestEstadosFantasma.ciclosHastaEvolucionDePresaACazadorNivelUno; i++){
            unFantasma.vivir(new Posicion(0,0));
        }
        
        assertEquals(EstadosFantasma.CAZADOR_NIVEL_UNO, ((Cazador)unFantasma.getEstado()).getDescripcionEspecifica() );
    }

    @Test
    public void fantasmaPresaSeMuere() {
        Fantasma unFantasma = crearFantasmaEnNivelUno();
        
        unFantasma.convertirAPresa();
        unFantasma.morir();
        
        assertEquals(EstadosFantasma.MUERTO, unFantasma.estado() );
    }
    
    @Test
    public void fantasmaMuertoPasaACazadorNivelUnoLuegoDe10Ciclos(){
        Fantasma unFantasma = crearFantasmaMuerto();
        
        for(int i = 0; i < TestEstadosFantasma.ciclosHastaEvolucionDeMuertoACazadorNivelUno; i++){
            unFantasma.vivir(new Posicion(0,0));
        }
        
        assertEquals(EstadosFantasma.CAZADOR_NIVEL_UNO, ((Cazador)unFantasma.getEstado()).getDescripcionEspecifica() );
    }

    @Test
    public void fantasmaMuertoNoPasaAPresa() {
        Fantasma unFantasma = crearFantasmaMuerto();
        
        unFantasma.convertirAPresa();
        
        assertEquals(EstadosFantasma.MUERTO, unFantasma.estado() );
    }

    @Test
    public void fantasmaCazadorNivelUnoNoMuere() {
        Fantasma unFantasma = crearFantasmaEnNivelUno();
        
        unFantasma.morir();
        
        assertEquals(EstadosFantasma.CAZADOR_NIVEL_UNO, ((Cazador)unFantasma.getEstado()).getDescripcionEspecifica() );
    }
    
    @Test
    public void fantasmaCazadorNivelDosNoMuere(){
        Fantasma unFantasma = crearFantasmaEnNivelDos();
        
        unFantasma.morir();
        
        assertEquals(EstadosFantasma.CAZADOR_NIVEL_DOS, ((Cazador)unFantasma.getEstado()).getDescripcionEspecifica() );
    }


    @Test
    public void fantasmaCazadorNivelTresNoMuere(){
        Fantasma unFantasma = crearFantasmaEnNivelTres();
        
        unFantasma.morir();
        
        assertEquals(EstadosFantasma.CAZADOR_NIVEL_TRES, ((Cazador)unFantasma.getEstado()).getDescripcionEspecifica() );
    }
    
    
    private Fantasma crearFantasmaMuerto() {
        Fantasma unFantasma = crearFantasmaEnNivelUno();
        unFantasma.convertirAPresa();
        unFantasma.morir();
        return unFantasma;
    }
    
    private Fantasma crearFantasmaEnNivelUno() {
    	ConfiguracionTicks confTicks = new ConfiguracionTicks();
    	Laberinto laberinto = crearLaberintoCompletoDeNPosiciones(10);
        Fantasma unFantasma = new Fantasma(laberinto,new Posicion(numeroAleatorio(5),numeroAleatorio(5)),confTicks);
        return unFantasma;
    }
    
    private Fantasma crearFantasmaEnNivelDos(){
        Fantasma unFantasma = crearFantasmaEnNivelUno();
        for (int i = 0; i < TestEstadosFantasma.ciclosHastaEvolucionDeCazadorNivelUnoACazadorNivelDos; i++){
            unFantasma.vivir(new Posicion(0,0));
        }
        return unFantasma;
    }
    
    private Fantasma crearFantasmaEnNivelTres(){
        Fantasma unFantasma = crearFantasmaEnNivelDos();
        for (int i = 0; i < TestEstadosFantasma.ciclosHastaEvolucionDeCazadorNivelDosACazadorNivelTres; i++){
            unFantasma.vivir(new Posicion(0,0));
        }
        return unFantasma;
    }
    
    private int numeroAleatorio(int max) {
		return (int) Math.round(100.0*Math.random()) % max;
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
    
}
