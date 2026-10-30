package escola.service;

import escola.model.Sala;
import escola.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public Sala cadastrar(Sala sala) {
        if (salaRepository.existsByCodigo(sala.getCodigo())) {
            throw new ConflitoException("Já existe uma sala cadastrada com o código: " + sala.getCodigo());
        }
        sala.setId(null);
        sala.setReservada(false);
        return salaRepository.save(sala);
    }

    public List<Sala> listarTodas() {
        return salaRepository.findAll();
    }

    public Sala buscarPorId(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sala não encontrada com o ID: " + id));
    }

    public Sala reservar(Long id) {
        Sala sala = buscarPorId(id);
        if (sala.isReservada()) {
            throw new ConflitoException("A sala já se encontra reservada.");
        }
        sala.setReservada(true);
        return salaRepository.save(sala);
    }

    public Sala liberar(Long id) {
        Sala sala = buscarPorId(id);
        if (!sala.isReservada()) {
            throw new ConflitoException("A sala já se encontra livre.");
        }
        sala.setReservada(false);
        return salaRepository.save(sala);
    }

    public void excluir(Long id) {
        Sala sala = buscarPorId(id);
        if (sala.isReservada()) {
            throw new ConflitoException("Não é possível excluir uma sala que está reservada.");
        }
        salaRepository.delete(sala);
    }
}
