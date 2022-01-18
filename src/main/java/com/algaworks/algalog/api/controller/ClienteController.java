package com.algaworks.algalog.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CatalogoClienteService;

@RestController // Define que esta classe é um controlador MVC, que utiiza os princípios REST.
@RequestMapping("/clientes") // Acessa os métodos desta classe ao acessar a URL no parâmetro
public class ClienteController {

	//@PersistenceContext
	//private EntityManager manager;
	
	@Autowired // Injeção de dependência. Instancia um objeto
	private ClienteRepository clienteRepository;
	private CatalogoClienteService catalogoClienteService;
	
	
	//@GetMapping("/clientes") // Posso colocar a anot acima de um método ou da classe para todos os métodos
							   // Caso esteja acima da classe, não é necessário especificar o parâmetro. 
	@GetMapping // Parametro já especificado na classe.
	public List<Cliente> listar() {
		return clienteRepository.findAll();
		
//		return manager.createQuery("from Cliente", Cliente.class)
		//			.getResultList();
		
		// Tambem pode ser feito assim:
		/*
		var cliente1 = new Cliente();
		var cliente2 = new Cliente();
		
		cliente1.setId(1L);
		cliente1.setNome("João");
		cliente1.setTelefone("71 9999-1111");
		cliente1.setEmail("joao@email.com");
		
		cliente2.setId(2L);
		cliente2.setNome("Maria");
		cliente2.setTelefone("71 9999-2222");
		cliente2.setEmail("maria@email.com");
		
		return Arrays.asList(cliente1, cliente2);
		*/
		
	}
	
	@GetMapping(params = "obter") // URL: .../clientes?obter=texto
	public List<Cliente> listar(@RequestParam("obter") String texto) {
		return clienteRepository.findByNomeContaining(texto);
		
	}	
	
	
	
	//@GetMapping("/clientes/{clienteId}") // Caso o GetMapping não esteja acima da classe
	@GetMapping("{clienteId}") //URL: .../clientes/1
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		// ResponseEntity é utilizado para enviar um código de reposta HTTP do servidor para o cliente (usuário). Ex: 200, 201, 404, etc
		// Poderia não utiliza-lo, porém, é interessante, para que seja enviado os códigos corretos para o cliente.
		// Exemplo: caso encontre o cliente solicitado, é enviado como resposta o cliente e o codigo 200 (Ok). Senão, é enviado o cod 404 (não encontrado)
		return clienteRepository.findById(clienteId)
//				.map(cliente -> ResponseEntity.ok(cliente))
				.map(ResponseEntity::ok) //O mesmo do código acima
				.orElse(ResponseEntity.notFound().build());
		/*
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		
		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		else	
			return ResponseEntity.notFound().build(); //Retorna um erro 404 caso não encontre a entidade
			//return cliente.orElse(null); //Se nao houver clientes, ele retorna vazio.
		 */
	}
	
	@PostMapping // Quando é feito uma chamada Post para o /clientes na URL, adiciona um cliente
	@ResponseStatus(HttpStatus.CREATED) // Retorna o codigo 201 (created) na resposta à requisição. Caso contrario, retornaria 200 (Ok)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) { //RequestBody desserializa e vincula os dados do corpo da requisição (ex: um JSON) ao parâmetro objeto cliente
		// Valid realiza a validação das regras do objeto ainda na chamada do método. Caso contrário, seria feita a validação, pela própria classe, após o retorno do método. 
//		return clienteRepository.save(cliente); // Armazena o cliente no banco
		return catalogoClienteService.salvar(cliente);
	}
	
	@PutMapping("{clienteId}") // Quando utilizado o verbo HTTP Put
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, 
											 @RequestBody Cliente cliente) {
		if (!clienteRepository.existsById(clienteId)) { // Caso não encontre o ID, retorna um erro 404
			return ResponseEntity.notFound().build();
		}
		
		// Caso o encontre, atualiza os dados do cliente
		cliente.setId(clienteId); // Usado pra forçar uma atualização ao cliente (O Spring já sabe que se trata de update). Caso contrário, será criado um novo cliente com outro ID.
//		cliente = clienteRepository.save(cliente);
		cliente = catalogoClienteService.salvar(cliente);
		
		
		// Retorna o codigo 200
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("{clienteId}")
	public ResponseEntity<Void> deletar(@Valid @PathVariable Long clienteId) {
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
//		clienteRepository.deleteById(clienteId);
		catalogoClienteService.excluir(clienteId);		
		
		return ResponseEntity.noContent().build();
	}
}
