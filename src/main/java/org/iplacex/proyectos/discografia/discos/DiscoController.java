package org.iplacex.proyectos.discografia.discos;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
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
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepository;

    @Autowired
    private IArtistaRepository artistaRepository;

    // POST /disco (Valida que idArtista exista)
    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {
        if (disco.idArtista != null && artistaRepository.existsById(disco.idArtista)) {
            Disco nuevoDisco = discoRepository.save(disco);
            return new ResponseEntity<>(nuevoDisco, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("No se puede crear el disco: El Id de artista no existe", HttpStatus.BAD_REQUEST);
        }
    }

    // GET /discos
    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        List<Disco> discos = discoRepository.findAll();
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }

    // GET /disco/{id}
    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable String id) {
        Optional<Disco> discoOpt = discoRepository.findById(id);
        if (discoOpt.isPresent()) {
            return new ResponseEntity<>(discoOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Disco no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // GET /artista/{id}/discos
    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) {
        List<Disco> discos = discoRepository.findDiscosByIdArtista(id);
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }
}