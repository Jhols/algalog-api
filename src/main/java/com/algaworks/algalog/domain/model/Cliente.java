package com.algaworks.algalog.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.algaworks.algalog.domain.ValidationGroups;

import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.Setter;

@Getter //Lombok: cria getters para todos os atributos
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //Lombok: cria os métodos equals e hashcode apenas nos atributos que possuem o Include. Se não houver params., cria pra todos os atr.
@Entity //Define que a classe é uma entidade correspondente a uma tabela no banco de dados de mesmo nome ou com nome atribuido a um parametro.
public class Cliente {

	@Id
	@EqualsAndHashCode.Include //Permite a criação dos métodos equals e hashcode.
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Utiliza o valor gerado e a forma como é gerado pelo SGBD
	@NotNull(groups = ValidationGroups.ClienteId.class) // Esta propriedade só é validada caso uma classe possua o ConvertGroup com a propriedade "to" referenciando ValidationGroups.ClienteId.class. Uma validação default não valida esta propriedade.
	private Long id;
	
	@NotBlank //Valor não pode ser nulo (null) ou vazio (Ex: "").
	@Size(max=60) //Tamanho máximo de caracteres do valor. Precisa estar condizente com o tam. da coluna da tabela no banco.
	private String nome;
	
	@NotBlank
	@Size(max=255)
	@Email //Valida se no ato de inclusão ou alteração, a sintaxe da string está correta pra email.
	private String email;
	
	//Se eu quiser especificar o nome da coluna ou usar um nome diferente na coluna
	@Column(name = "telefone")
	@NotBlank
	@Size(max=20)
	private String telefone;
		
}
