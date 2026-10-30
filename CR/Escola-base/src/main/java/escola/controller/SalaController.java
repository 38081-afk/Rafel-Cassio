package escola.controller;

import escola.model.Sala;
import escola.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping
    public ResponseEntity<Sala> cadastrar(@Valid @RequestBody Sala sala) {
        Sala novaSala = salaService.cadastrar(sala);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaSala);
    }

    @GetMapping
    public ResponseEntity<List<Sala>> listarTodas() {
        List<Sala> salas = salaService.listarTodas();
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sala> buscarPorId(@PathVariable Long id) {
        Sala sala = salaService.buscarPorId(id);
        return ResponseEntity.ok(sala);
    }

    @PostMapping("/{id}/reservar")
    public ResponseEntity<Sala> reservar(@PathVariable Long id) {
        Sala sala = salaService.reservar(id);
        return ResponseEntity.ok(sala);
    }

    @PostMapping("/{id}/liberar")
    public ResponseEntity<Sala> liberar(@PathVariable Long id) {
        Sala sala = salaService.liberar(id);
        return ResponseEntity.ok(sala);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        salaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
