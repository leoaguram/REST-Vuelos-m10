package mx.unam.dgtic.m10_Vuelo.controller;

import mx.unam.dgtic.m10_Vuelo.model.Vuelo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;

@RestController
@RequestMapping("/api/vuelos")
public class VueloRestController {
    public static final String NOMBRE = "Leonardo Aguirre Ramírez";

    HashMap<Integer, Vuelo> vuelos;

    public VueloRestController() {
        vuelos = new HashMap<>();
        vuelos.put(0, new Vuelo(0, "CDMX", "Los Ángeles CA", LocalDateTime.of(2024, 4, 13, 15, 30),"15HGM5"));
        vuelos.put(1, new Vuelo(1, "Cancún", "Puerto Vallarta", LocalDateTime.of(2024, 4, 13, 20, 55),"16SC45"));
        vuelos.put(2, new Vuelo(2, "Los Cabos", "Monterrey", LocalDateTime.of(2024, 6, 12, 10, 0), "12E4ED"));
        vuelos.put(3, new Vuelo(3, "Guadalajara", "Miami", LocalDateTime.of(2024, 6, 12, 19, 30), "19LOS5"));
    }

    @GetMapping(path = "/saludar", produces = MediaType.TEXT_HTML_VALUE)
    public String saludar() {
        return "<div style='text-align: center;'>" +
                "<h1 style='color: red;'>Integrantes:</h1><br>" +
                "<h2>" + NOMBRE + "</h2>" +
                "</div>";
    }

    @GetMapping(path = "/", headers = {"Accept=application/json"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Integer, Vuelo>> getAll(){
        return new ResponseEntity<>(vuelos, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Vuelo> getVuelo(@PathVariable int id){
        Vuelo vuelo = vuelos.get(id);
        if(vuelo != null){
            return ResponseEntity.ok(vuelo);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vuelo> addVuelo(@RequestBody Vuelo vuelo){
        int id = 1;
        while(vuelos.containsKey(id)){
            id++;
        }
        vuelo.setId(id);
        vuelos.put(id, vuelo);
        return new ResponseEntity<>(vuelo, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vuelo> reemplazarVuelo(@PathVariable int id, @RequestBody Vuelo vuelo){
        if(vuelos.containsKey(id)){
            vuelos.replace(id, vuelo);
            return ResponseEntity.ok(vuelos.get(id));
        }else{
            vuelos.put(id, vuelo);
            return new ResponseEntity<>(vuelos.get(id), HttpStatus.CREATED);
        }
    }

    @PutMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putNoPermitido(){
        return new ResponseEntity<>("{'msg':'Acción no permitida'}", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vuelo> actualizarVuelo(@PathVariable int id, @RequestBody Vuelo vuelo){
        Vuelo vueloDB = vuelos.get(id);
        if(vuelo== null){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        if(vueloDB == null){
            return ResponseEntity.notFound().build();
        }
        if(vueloDB.getCodigoVuelo() != null){
            vueloDB.setCodigoVuelo(vuelo.getCodigoVuelo());
        }
        if(vueloDB.getOrigen() != null){
            vueloDB.setOrigen(vuelo.getOrigen());
        }
        if(vueloDB.getDestino() != null){
            vueloDB.setDestino(vuelo.getDestino());
        }
        if(vueloDB.getFechaHora() != null){
            vueloDB.setFechaHora(vuelo.getFechaHora());
        }

        vuelos.replace(id, vueloDB);
        return ResponseEntity.ok(vuelos.get(id));
    }

    @PatchMapping("/")
    public ResponseEntity<String> patchNoPermitido() {
        return new ResponseEntity<>("{'msg':'Accion no permitida'}", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vuelo> deleteVuelo(@PathVariable int id){
        if(vuelos.containsKey(id)){
            return ResponseEntity.ok(vuelos.remove(id));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
