package com.algaworks.algalog.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable // Indica que esta classe pode ser usada como Embeddable em outra classe. Ou seja, as propriedades desta classe fazerem parte da mesma tabela de outra classe (entidade) no banco.
public class Destinatario {

	@Column(name = "destinatario_nome") // Define o nome da coluna que será criada na tabela que fez o Embedded. Se não usar esta anot, usa-se por padrão o próprio nome da propriedade.
	@NotBlank
	private String nome;
	
	@Column(name = "destinatario_logradouro")
	@NotBlank
	private String logradouro;
	
	@Column(name = "destinatario_numero")
	@NotBlank
	private String numero;
	
	@Column(name = "destinatario_complemento")
	@NotBlank
	private String complemento;
	
	@Column(name = "destinatario_bairro")
	@NotBlank
	private String bairro;
	
	
}
