package com.algaworks.algalog.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algalog.domain.model.Cliente;

@Repository // Indica que a classe é um repositório para criação de métodos de acesso ao banco de dados.
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	// Ao extender a interface JpaRepository, são inclusos todos os métodos CRUD básicos.
	
	List<Cliente> findByNome(String nome);
	List<Cliente> findByNomeContaining(String nome); // Usa o 'like' para fazer a consulta de uma parte do nome
	Optional<Cliente> findByEmail(String email);
	
}
