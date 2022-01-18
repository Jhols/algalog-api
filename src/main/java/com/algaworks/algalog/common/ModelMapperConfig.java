package com.algaworks.algalog.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Declara que esta classe é um componente Spring de configuração de Beans, ou seja, os métodos definem Beans Spring. 
public class ModelMapperConfig {

	@Bean // Declara e configura um Bean para o contexto do Spring, isto é, gerenciado por este. Desta forma, passa-se a se tornar possível injetar a depedência deste Bean.
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
