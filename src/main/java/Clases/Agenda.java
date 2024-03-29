
package Clases;

import java.io.*;
import java.util.*;

/**
 *
 * @author Sebastian
 */
public class Agenda implements Operaciones{
    //Atributos
    public List<Contacto> contactos = new ArrayList();
    
    //Cosntructor
    public Agenda() {
    }
    
    //Métodos

    public List<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<Contacto> contactos) {
        this.contactos = contactos;
    }
    
    
    public void añadirContacto(String nombre, List<String> telefonos, 
                               String lugar, String email, String direccion, 
                               String alias)
                               throws CustomException{
        verificarTelefonos(telefonos, 0.0);
        verificarVacio(nombre, telefonos, lugar);
        
        Contacto nuevo = new Contacto(nombre,telefonos,lugar,email,direccion,alias);
        
        this.contactos.add(nuevo);
    }
    
    public void añadirContacto(Contacto contacto)
                               throws CustomException{
        int op = contactos.indexOf(contacto);
        if(op != -1){
            contactos.set(op, contacto);
        }else{
            verificarTelefonos(contacto.telefonos, contacto.id);
            verificarVacio(contacto.nombre, contacto.telefonos, contacto.lugar);
            this.contactos.add(contacto);
        }
    }
    
    public void añadirContactos(List<Contacto> contactos)
                               throws CustomException{
        for(Contacto contacto1: contactos){
            this.añadirContacto(contacto1);
        }
    }
    
    public void eliminarContacto(Contacto aEliminar) throws CustomException{
        
        if(!contactos.contains(aEliminar)){
            throw new CustomException("El contacto no pertenece a la agenda");
        }
        
        this.contactos.remove(aEliminar); 
    }
     
    public List<Contacto> consultar(String nombreCons, String telefonoCons, 
                          String lugarCons, String emailCons, 
                          String direccionCons, String aliasCons) 
                          throws CustomException {
        
        List<Contacto> aux = new ArrayList();
        List<Contacto> consulta = new ArrayList();
        
//Consulta por el nombre y guarda las coincidencias en una agenda auxiliar
        if(!"".equals(nombreCons)){
            contactos.stream()
                  .filter((agenda1) -> (agenda1.nombre.contains(nombreCons)))
                  .forEachOrdered((agenda1) -> {
                    aux.add(agenda1);
            });
            consulta.addAll(aux);
            verificarConsulta(consulta);
        }
//Luego consulta el teléfono en la agenda principal si la agenda auxiliar sigue
//vacía tras la consulta por el nombre
        if(aux.isEmpty() && !"".equals(telefonoCons)){
            contactos.stream()
                  .filter((agenda1)->(agenda1.telefonos.contains(telefonoCons)))
                  .forEachOrdered((agenda1) -> {
                      aux.add(agenda1);
            });
            consulta.addAll(aux);
            verificarConsulta(consulta);
        }
        else{
//Si la agenda auxiliar no está vacía tras la consulta por el nombre, la 
//consulta por el telefono se hace en esta agenda auxiliar removiendo los 
//contactos que NO cumplan con la busqueda
            if(!"".equals(telefonoCons)){
                aux.stream()
                   .filter((agenda1)->(!agenda1.telefonos.contains(telefonoCons)))
                   .forEachOrdered((agenda1) -> {
                       consulta.remove(agenda1);
                });
                aux.removeAll(aux);
                aux.addAll(consulta);
                verificarConsulta(consulta);
            }
        }

//La lógica de busqueda anterior, en agenda principal o en auxiliar, se repite
//con los demás datos a consultar
        
        if(aux.isEmpty() && !"".equals(lugarCons)){
            contactos.stream()
                  .filter((agenda1)->(agenda1.lugar.contains(lugarCons)))
                  .forEachOrdered((agenda1) -> {
                      aux.add(agenda1);
            });
            consulta.addAll(aux);
            verificarConsulta(consulta);
        }
        else{
            if(!"".equals(lugarCons)){
                aux.stream()
                   .filter((agenda1)->(!agenda1.lugar.contains(lugarCons)))
                   .forEachOrdered((agenda1) -> {
                       consulta.remove(agenda1);
                });
                aux.removeAll(aux);
                aux.addAll(consulta);
                verificarConsulta(consulta);
            }
        }

        if(aux.isEmpty() && !"".equals(emailCons)){
            contactos.stream()
                  .filter((agenda1)->(agenda1.email.contains(emailCons)))
                  .forEachOrdered((agenda1) -> {
                      aux.add(agenda1);
            });
            consulta.addAll(aux);
            verificarConsulta(consulta);
        }
        else{
            if(!"".equals(emailCons)){
                aux.stream()
                   .filter((agenda1)->(!agenda1.email.contains(emailCons)))
                   .forEachOrdered((agenda1) -> {
                       consulta.remove(agenda1);
                });
                aux.removeAll(aux);
                aux.addAll(consulta);
                verificarConsulta(consulta);
            }
        }
        
        if(aux.isEmpty() && !"".equals(direccionCons)){
            contactos.stream()
                  .filter((agenda1)->(agenda1.direccion
                                             .contains(direccionCons)))
                  .forEachOrdered((agenda1) -> {
                      aux.add(agenda1);
            });
            consulta.addAll(aux);
            verificarConsulta(consulta);
        }
        else{
            if(!"".equals(direccionCons)){
                aux.stream()
                   .filter((agenda1)->(!agenda1.direccion
                                               .contains(direccionCons)))
                   .forEachOrdered((agenda1) -> {
                       consulta.remove(agenda1);
                });
                aux.removeAll(aux);
                aux.addAll(consulta);
                verificarConsulta(consulta);
            }
        }
        
        if(aux.isEmpty() && !"".equals(aliasCons)){
            contactos.stream()
                  .filter((agenda1)->(agenda1.alias
                                             .contains(aliasCons)))
                  .forEachOrdered((agenda1) -> {
                      aux.add(agenda1);
            });
            consulta.addAll(aux);
            verificarConsulta(consulta);
        }
        else{
            if(!"".equals(aliasCons)){
                aux.stream()
                   .filter((agenda1)->(!agenda1.alias
                                               .contains(aliasCons)))
                   .forEachOrdered((agenda1) -> {
                       consulta.remove(agenda1);
                });
                aux.removeAll(aux);
                aux.addAll(consulta);
                verificarConsulta(aux);
            }
        }
        
        return consulta;
    }
    
    @Override
    public void exportar(File archivo) throws CustomException{
        
        try (java.io.PrintWriter salida = new PrintWriter(archivo)){
                salida.print(this.toString());
        } catch (FileNotFoundException ex) {
            throw new CustomException("No se encontró el archivo");
        }
    }
    
    @Override
    public void importar(File archivo) throws CustomException{
        
        List<Contacto> importados = new ArrayList<>();
        
        try {
            var lector = new Scanner(archivo);
            
            while(lector.hasNextLine()){
                List<String> telefonos = new ArrayList<>();
                var campos = lector.nextLine().split(";",6);

                var nombre = campos[0];
                var numeros = campos[1].split(",");
                
                telefonos.addAll(Arrays.asList(numeros));
                
                var lugar = campos[2];
                var email = campos[3];
                var direccion = campos[4];
                var alias = campos[5];
                
                Contacto c = new Contacto(nombre,telefonos,lugar,email,
                                          direccion,alias);
                importados.add(c);
            }
            
        } catch (FileNotFoundException ex) {
            throw new CustomException("No se encontró el archivo");
        }
        
        this.añadirContactos(importados);
        
    }
    
    @Override
    public void cargar(File archivo) throws CustomException{
        
        List<Contacto> importados = new ArrayList<>();
        
        try {
            var lector = new Scanner(archivo);
            
            while(lector.hasNextLine()){
                List<String> telefonos = new ArrayList<>();
                var campos = lector.nextLine().split(";",6);

                var nombre = campos[0];
                var numeros = campos[1].split(",");
                
                telefonos.addAll(Arrays.asList(numeros));
                
                var lugar = campos[2];
                var email = campos[3];
                var direccion = campos[4];
                var alias = campos[5];
                
                Contacto c = new Contacto(nombre,telefonos,lugar,email,
                                          direccion,alias);
                importados.add(c);
            }
            
        } catch (FileNotFoundException ex) {
            throw new CustomException("No se encontró el archivo precargado");
        }
        
        this.setContactos(importados);
        
    }

    @Override
    public String toString() {
        String impresion = "";
        
        for(Contacto contacto1: this.contactos){
            impresion += contacto1.nombre +";";
            for(String tel: contacto1.telefonos){
                if(tel.equals(contacto1.telefonos.get(contacto1.telefonos
                                                               .size()-1))){
                    impresion += tel;
                }else{
                    impresion += tel + ",";
                }
            }
            impresion += ";"+ contacto1.lugar;
            impresion += ";"+ contacto1.email;
            impresion += ";"+ contacto1.direccion;
            impresion += ";"+ contacto1.alias + "\n";
        }
        
        return impresion;
    }
    
    @Override
    public void verificarConsulta(List<Contacto> lista) throws CustomException{
        if(lista.isEmpty()){
            throw new CustomException("Ningún contacto cumple con la "
                                      +"consulta.");
        }
    }
    
    @Override
    public void verificarTelefonos(List<String> telefonos, Double id)
                                   throws CustomException{
        for(String telefono1: telefonos){
            for(Contacto contacto1: contactos){
                if(Objects.equals(contacto1.getId(), id)){
                }else{
                    if(contacto1.telefonos.contains(telefono1)){
                        throw new CustomException("El teléfono "+telefono1
                                              +" se repite en otro contacto.");
                    }
                }
            }
        }
    }
    
    @Override
    public void verificarVacio(String nombre, List<String> telefonos, 
                               String lugar) 
                                   throws CustomException{
        if("".equals(nombre) || telefonos.isEmpty() || "".equals(lugar)){
            throw new CustomException("Nombre, Telefonos y lugar son campos "
                                      + "obligatorios");
        }
    }
    
    
}
