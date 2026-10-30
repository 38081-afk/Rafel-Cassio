package escola.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O código é obrigatório.")
    @Column(nullable = false, unique = true)
    private String codigo;

    @NotBlank(message = "O nome é obrigatório.")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "A capacidade é obrigatória.")
    @Min(value = 1, message = "A capacidade deve ser no mínimo 1.")
    @Column(nullable = false)
    private Integer capacidade;

    @Column(nullable = false)
    private boolean reservada = false;

    public Sala() {
    }

    public Sala(Long id, String codigo, String nome, Integer capacidade, boolean reservada) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.capacidade = capacidade;
        this.reservada = reservada;
    }

    public Sala(String codigo, String nome, Integer capacidade) {
        this.codigo = codigo;
        this.nome = nome;
        this.capacidade = capacidade;
        this.reservada = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public boolean isReservada() {
        return reservada;
    }

    public void setReservada(boolean reservada) {
        this.reservada = reservada;
    }
}
