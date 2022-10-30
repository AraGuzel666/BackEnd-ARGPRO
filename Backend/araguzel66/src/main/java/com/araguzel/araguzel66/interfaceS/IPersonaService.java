
package com.araguzel.araguzel66.interfaceS;

import com.araguzel.araguzel66.entity.Persona;
import java.util.List;

public interface IPersonaService {
    //Lista de Personas
    public List<Persona> getPersona();
    
    //Guardar objeto Persona
    public void savePersona(Persona persona);
    
    //Eliminar objeto por ID
    public void deletePersona(Long id);
    
    //Buscar persona por ID
    public Persona findPersona(Long id);
    
    
}
