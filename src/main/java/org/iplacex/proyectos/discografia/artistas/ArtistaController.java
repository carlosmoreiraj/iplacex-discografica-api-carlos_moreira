package org.iplacex.proyectos.discografia.artistas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepository;

    // POST /artista
    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista nuevoArtista = artistaRepository.save(artista);
        return new ResponseEntity<>(nuevoArtista, HttpStatus.CREATED);
    }

    // GET /artistas
    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {
        List<Artista> artistas = artistaRepository.findAll();
        return new ResponseEntity<>(artistas, HttpStatus.OK);
    }

    // GET /artista/{id}
    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetArtistaRequest(@PathVariable String id) {
        Optional<Artista> artistaOpt = artistaRepository.findById(id);
        if (artistaOpt.isPresent()) {
            return new ResponseEntity<>(artistaOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Artista no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // PUT /artista/{id}
    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artista artista) {
        if (artistaRepository.existsById(id)) {
            artista._id = id;
            Artista artistaActualizado = artistaRepository.save(artista);
            return new ResponseEntity<>(artistaActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se puede actualizar: El Id del Artista no fue encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /artista/{id}
    @DeleteMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable String id) {
        if (artistaRepository.existsById(id)) {
            artistaRepository.deleteById(id);
            return new ResponseEntity<>("Artista eliminado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se puede eliminar: El Id del Artista no fue encontrado", HttpStatus.NOT_FOUND);
        }
    }
}