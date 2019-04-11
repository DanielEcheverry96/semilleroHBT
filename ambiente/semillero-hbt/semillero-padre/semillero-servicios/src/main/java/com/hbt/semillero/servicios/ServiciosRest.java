package com.hbt.semillero.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.hbt.semillero.dto.LineaDTO;
import com.hbt.semillero.dto.MarcaDTO;
import com.hbt.semillero.dto.PersonaDTO;
import com.hbt.semillero.dto.ResultadoDTO;
import com.hbt.semillero.entidades.Linea;
import com.hbt.semillero.entidades.Marca;
import com.hbt.semillero.entidades.Persona;
import com.hbt.semillero.servicios.interfaces.IConsultasBeanLocal;

@Path("/ServiciosRest")
public class ServiciosRest {

	@EJB
	private IConsultasBeanLocal consultasBean; 
	
	@GET
	@Path("/consultarMarcas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<MarcaDTO> consultarMarcas() {
		List<Marca> marcas = consultasBean.consultarMarcas();
		List<MarcaDTO> retorno = new ArrayList<>();
		for (Marca marca : marcas) {
			MarcaDTO marcaDTO = construirMarcaDTO(marca);
			retorno.add(marcaDTO);
		}
		return retorno;
	}
	
	@GET
	@Path("/consultarLineas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<LineaDTO> consultarLineas(@QueryParam(value = "idMarca")Long idMarca) {
		List<Linea> lineas = consultasBean.consultarLineas(idMarca);
		List<LineaDTO> retorno = new ArrayList<>();
		for (Linea linea : lineas) {
			LineaDTO lineaDTO = new LineaDTO();
			MarcaDTO marcaDTO = construirMarcaDTO(linea.getMarca());
			
			lineaDTO.setIdLinea(linea.getIdLinea());
			lineaDTO.setNombre(linea.getNombre());
			lineaDTO.setCilindraje(linea.getCilindraje());
			lineaDTO.setMarca(marcaDTO);
			retorno.add(lineaDTO);
		}
		return retorno;
	}
	
	@POST
	@Path("/crearPersona")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultadoDTO crearPersona(PersonaDTO personaDTO) {
		ResultadoDTO retorno = new ResultadoDTO();
		retorno.setExitoso(true);
		try {
			consultasBean.crearPersona(personaDTO);			
		}
		catch (Exception ex) {
			retorno.setExitoso(false);
			retorno.setMensajeError("No se pudo ingresar la persona");
		}
		return retorno;
	}
	
	@GET
	@Path("/consultarPersonas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PersonaDTO> consultarPersonas(@QueryParam(value = "tipoIdentificacion")String tipoIdentificacion, @QueryParam(value = "numeroIdentificacion") String numeroIdentificacion ) {
		List<Persona> personas = consultasBean.consultarPersonas(tipoIdentificacion, numeroIdentificacion);
		List<PersonaDTO> retorno = new ArrayList<>();
		for (Persona persona : personas) {
			PersonaDTO personaDTO = new PersonaDTO();
			
			personaDTO.setIdPersona(persona.getIdPersona());
			personaDTO.setNombres(persona.getNombres());
			personaDTO.setApellidos(persona.getApellidos());
			personaDTO.setTipoIdentificacion(persona.getTipoIdentificacion());
			personaDTO.setNumeroIdentificacion(persona.getNumeroIdentificacion());
			personaDTO.setNumeroTelefonico(persona.getNumeroTelefonico());
			personaDTO.setEdad(persona.getEdad());
			
			retorno.add(personaDTO);
		}
		return retorno;
	}
	
	@PUT
	@Path("/actualizarPersona")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultadoDTO actualizarPersona(PersonaDTO personaDTO) {
		ResultadoDTO retorno = new ResultadoDTO();
		retorno.setExitoso(true);
		try {
			consultasBean.actualizarPersona(personaDTO);			
		}
		catch (Exception ex) {
			retorno.setExitoso(false);
			retorno.setMensajeError("No se pudo actualizar la persona");
		}
		return retorno;
	}
	
	
	/**
	 * Asignar a un objeto MarcaDTO los valores de las propiedades
	 * de un objeto Marca
	 * @param marca
	 * @return
	 */
	private MarcaDTO construirMarcaDTO(Marca marca) {
		MarcaDTO marcaDTO = new MarcaDTO();
		marcaDTO.setIdMarca(marca.getIdMarca());
		marcaDTO.setNombre(marca.getNombre());
		return marcaDTO;
	}
	
}