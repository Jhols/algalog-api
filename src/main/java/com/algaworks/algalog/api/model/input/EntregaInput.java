package com.algaworks.algalog.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaInput { // Classes de Input são responsáveis por receber os dados vindos do consumidor da API e serem tratados antes de serem levados à classe de domínio.

	// Lembrete importante: é recomendado que as classes da API não tenham conhecimento das classes de domínio.
	
	@Valid
	@NotNull
	private ClienteIdInput cliente;
	
	@Valid
	@NotNull
	private DestinatarioInput destinatario;
	
	@NotNull
	private BigDecimal taxa;
	
}
