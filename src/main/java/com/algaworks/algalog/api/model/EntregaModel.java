package com.algaworks.algalog.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.algaworks.algalog.domain.model.StatusEntrega;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaModel { // Esta classe é um modelo de representação. Ela replica os dados da classe de modelo de domínio (negócio) de forma que possam ser apresentados a usuários, por exemplo.
	 
	/*
  	O modelo de representação é importante para proteger a API, de modo que dados sensíveis não sejam expostos sem querer, 
 	 
 	ou a API quebre caso novas propriedades sejam inseridas na classe de domínio.
	
	Outra vantagem, é que o programador não precisa se preocupar tanto nos impactos que uma alteração na classe
	poderia trazer. É possível estruturar a classe de representação de uma forma totalmente diferente da de domínio
	que isso não traria grandes consequências para a API, principalmente no que tange a banco de dados.
	 */
	
	private Long id;
	private ClienteResumoModel cliente;
	private DestinatarioModel destinatario;
	private BigDecimal taxa;
	private StatusEntrega status;
	private OffsetDateTime dataPedido;
	private OffsetDateTime dataFinalizacao;
	
}
