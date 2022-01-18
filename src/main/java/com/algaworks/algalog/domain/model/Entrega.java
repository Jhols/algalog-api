package com.algaworks.algalog.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.algaworks.algalog.domain.ValidationGroups;
import com.algaworks.algalog.domain.exception.NegocioException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Entrega {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@ManyToOne // Mapeamento objeto-relacional Muitos para Um, referenciando a chave estrangeira de Cliente na tabela Entrega, no banco
	@JoinColumn(name = "cliente_id") // Por padrão, o ManyToOne buscará a nomenclatura de coluna (objeto_id) não sendo necessário esta anot. Caso o nome do id seja diferente (ex: idcliente), é necessário colocar esta anot.
	@NotNull
	@Valid // Permite a validação cascateada do objeto "cliente" no momento em que o objeto "entrega" está sendo validado. Caso contrário, apenas o objeto "entrega" seria validado.
	@ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class) // Apenas faz a validação das propriedades que possuem o grupo "ValidationGroup.ClienteId". Quaisquer outros, inclusive o default, são ignorados.
	private Cliente cliente;
	
	@Embedded // Abstrai-se os dados do destinatário e os mapeia pra mesma tabela Entrega no banco.
	@NotNull
	@Valid
	private Destinatario destinatario;
	
	@NotNull
	private BigDecimal taxa;
	
	@OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL) // Anotação que está no lado inverso de uma relação Muitos para Um. No parâmetro "mappedBy", utiliza-se o nome da propriedade da classe que está "do outro lado" da relação. 
	private List<Ocorrencia> ocorrencias = new ArrayList<>(); // Neste caso, quem está "do outro lado" é a classe Ocorrencia.		// Parâmetro "cascade" indica que deve ser feito um cascateamento na hora de persistir os dados de uma ocorrêcia para dentro de uma entrega.
		
	@Enumerated(EnumType.STRING) // Captura o valor da string (EnumType.STRING) do enum e armazena na coluna de Status, na tabela Entrega do banco, ao invés do valor numérico (índice, EnumType.ORDINAL).
	@JsonProperty(access = Access.READ_ONLY) // Não permite que o consumidor da API consiga realizar escrita sobre esse dado.
	private StatusEntrega status;
	
	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime dataPedido;
	
	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime dataFinalizacao;

	public Ocorrencia adicionarOcorrencia(String descricao) {
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setDescricao(descricao);
		ocorrencia.setDataRegistro(OffsetDateTime.now());
		ocorrencia.setEntrega(this);
		
		this.getOcorrencias().add(ocorrencia);
		return ocorrencia;
	}

	public void finalizar() {
		if (!getStatus().equals(StatusEntrega.PENDENTE)) {
			throw new NegocioException("Entrega não pode ser finalizada");
		}
		
		setStatus(StatusEntrega.FINALIZADA);
		setDataFinalizacao(OffsetDateTime.now());
	}
	
	/*
	 * Uma vez que as classes foram separadas em classes de domínio e de representação, as anotações de Bean Validation
	 * passam a ser opcionais na classes de entidade, caso elas já estejam nas classes de representação.
	 * Se as operações do sistema serão feitas SEMPRE via API, pode-se retirar as anotações das entidades, pois o modelo
	 * de representação já está sendo validado ao receber dados do consumidor.
	 * Porém, se não há certeza se SEMPRE será pela API ou há um outro tipo de interface que utilizará os dados da entidade,
	 * é recomendável manter as anotações de Bean Validation.
	 * 
	 * Anotações Bean Validation:
	 * - @NotNull, @NotBlank, @Valid, @ConvertGroup, @JsonProperty, etc.
	 */
	
}
