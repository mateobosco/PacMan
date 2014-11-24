package com.g7.modelo;

import com.g7.modelo.fantasma.estados.Cazador;
import com.g7.modelo.fantasma.estados.Estado;
import com.g7.modelo.fantasma.estados.EstadosFantasma;

public class ConfiguracionTicks {
	
	private float cantidadDeMovimientosPacManEnUnTick;
	private float cantidadDeMovimientosFantasmaNivelUnoEnUnTick;
	private float cantidadDeMovimientosFantasmaNivelDosEnUnTick;
	private float cantidadDeMovimientosFantasmaNivelTresEnUnTick;
	private float cantidadDeMovimientosFantasmaPresaEnUnTick;
	private float cantidadDeMovimientosFantasmaMuertoEnUnTick;
	private float cantidadDeMovimientosFrutaEnUnTick;

	public float getCantidadDeMovimientosPacManEnUnTick() {
		return cantidadDeMovimientosPacManEnUnTick;
	}

	public void setCantidadDeMovimientosPacManEnUnTick(
			float cantidadDeMovimientosPacManEnUnTick) {
		this.cantidadDeMovimientosPacManEnUnTick = cantidadDeMovimientosPacManEnUnTick;
	}

	public float getCantidadDeMovimientosFantasmaEnUnTick(Estado estado) {
		if(estado.getDescripcion().equals(EstadosFantasma.PRESA))
			return cantidadDeMovimientosFantasmaPresaEnUnTick;
		if(estado.getDescripcion().equals(EstadosFantasma.CAZADOR)){
			if(((Cazador)estado).getDescripcionEspecifica().equals(EstadosFantasma.CAZADOR_NIVEL_UNO))
				return cantidadDeMovimientosFantasmaNivelUnoEnUnTick;
			if(((Cazador)estado).getDescripcionEspecifica().equals(EstadosFantasma.CAZADOR_NIVEL_DOS))
				return cantidadDeMovimientosFantasmaNivelDosEnUnTick;
			if(((Cazador)estado).getDescripcionEspecifica().equals(EstadosFantasma.CAZADOR_NIVEL_TRES))
				return cantidadDeMovimientosFantasmaNivelTresEnUnTick;
		}
		if(estado.getDescripcion().equals(EstadosFantasma.MUERTO))
			return cantidadDeMovimientosFantasmaMuertoEnUnTick;
		return 0.0f;
	}

	public void setCantidadDeMovimientosFantasmaNivelUnoEnUnTick(
			float cantidadDeMovimientosFantasmaNivelUnoEnUnTick) {
		this.cantidadDeMovimientosFantasmaNivelUnoEnUnTick = cantidadDeMovimientosFantasmaNivelUnoEnUnTick;
	}

	public void setCantidadDeMovimientosFantasmaNivelDosEnUnTick(
			float cantidadDeMovimientosFantasmaNivelDosEnUnTick) {
		this.cantidadDeMovimientosFantasmaNivelDosEnUnTick = cantidadDeMovimientosFantasmaNivelDosEnUnTick;
	}
	
	public void setCantidadDeMovimientosFantasmaNivelTresEnUnTick(
			float cantidadDeMovimientosFantasmaNivelTresEnUnTick) {
		this.cantidadDeMovimientosFantasmaNivelTresEnUnTick = cantidadDeMovimientosFantasmaNivelTresEnUnTick;
	}

	public void setCantidadDeMovimientosFantasmaPresaEnUnTick(
			float cantidadDeMovimientosFantasmaPresaEnUnTick) {
		this.cantidadDeMovimientosFantasmaPresaEnUnTick = cantidadDeMovimientosFantasmaPresaEnUnTick;
	}

	public void setCantidadDeMovimientosFantasmaMuertoEnUnTick(
			float cantidadDeMovimientosFantasmaMuertoEnUnTick) {
		this.cantidadDeMovimientosFantasmaMuertoEnUnTick = cantidadDeMovimientosFantasmaMuertoEnUnTick;
	}

	public float getCantidadDeMovimientosFrutaEnUnTick() {
		return cantidadDeMovimientosFrutaEnUnTick;
	}

	public void setCantidadDeMovimientosFrutaEnUnTick(
			float cantidadDeMovimientosFrutaEnUnTick) {
		this.cantidadDeMovimientosFrutaEnUnTick = cantidadDeMovimientosFrutaEnUnTick;
	}

	public ConfiguracionTicks()	{
		cantidadDeMovimientosPacManEnUnTick = 2f;
		cantidadDeMovimientosFantasmaNivelUnoEnUnTick = 1f;
		cantidadDeMovimientosFantasmaNivelDosEnUnTick = 1.5f;
		cantidadDeMovimientosFantasmaNivelTresEnUnTick = 2f;
		cantidadDeMovimientosFantasmaPresaEnUnTick = 1f;
		cantidadDeMovimientosFantasmaMuertoEnUnTick = 1f;
		cantidadDeMovimientosFrutaEnUnTick = 2f;
	}

}
