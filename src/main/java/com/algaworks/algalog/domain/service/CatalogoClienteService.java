package com.algaworks.algalog.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algalog.domain.exception.NegocioException;
import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@Service // Classe de serviço, responsável por executar operações de regras de negócio que utilizam mais de uma entidade ou para casos de uso específicos
@AllArgsConstructor
public class CatalogoClienteService {

	private ClienteRepository clienteRepository;
	
	
	public Cliente buscar(Long clienteId) {
		return clienteRepository.findById(clienteId) 	 // FindById retorna um option. Pode haver um cliente ou não com o id.
				.orElseThrow(() -> new NegocioException("Cliente não encontrado")); // Caso não haja um cliente com o id mencionado, é lançado uma exceção.
	}
	
					// Anotação que declara que o método será executado dentro de uma transação.
	@Transactional	// Se houver algum problema na execução do método, todas as operações que estão sendo feitas no banco são descartadas.
	public Cliente salvar(Cliente cliente) {
		
		boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
				.stream() // Retorna um uma sequência Stream caso haja algum elemento dentro do Optional.
				.anyMatch(clienteExistente -> !clienteExistente.equals(cliente)); // Caso haja um elemento dentro do Optional e este seja diferente (! not) do elemento sendo comparado, dá match e retorna true.
		
		if (emailEmUso) {
			throw new NegocioException("Já existe um cliente cadasrado com este e-mail");
		}
		
		return clienteRepository.save(cliente);
	}
	
	@Transactional
	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
	
}
