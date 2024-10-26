package br.edu.fatecpg.AplicacaoJPA_CEP;

import br.edu.fatecpg.AplicacaoJPA_CEP.model.Endereco;
import br.edu.fatecpg.AplicacaoJPA_CEP.repository.EnderecoRepository;
import br.edu.fatecpg.AplicacaoJPA_CEP.service.ConsomeAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class ApiJpaCepApplication implements CommandLineRunner {

	@Autowired
	private EnderecoRepository repository;

	private static final Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(ApiJpaCepApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String menu = """
            ############################
           MENU OPÇÕES:
            1 - Adicionar CEP
            2 - Buscar por CEP
            3 - Sair
            ############################""";

		while (true) {
			System.out.println(menu);
			int escolha = scan.nextInt();

			switch (escolha) {
				case 1 -> adicionarCEP();
				case 2 -> buscarCEP();
				case 3 -> {
					System.out.println("Encerrando aplicação . . .");
					scan.close();
					return;
				}
				default -> System.out.println("Opção inválida. Tente novamente.");
			}
		}
	}

	public void adicionarCEP() throws InterruptedException, IOException {
		ConsomeAPI service = new ConsomeAPI();

		System.out.println("Digite um CEP: ");
		String cepDigitado = scan.next();

		Endereco endereco = service.retornaEnd(cepDigitado);

		// Verifica se o CEP já existe no banco
		Optional<Endereco> enderecoExistente = repository.findByCep(cepDigitado);
		if (enderecoExistente.isPresent()) {
			System.out.println("CEP já cadastrado no sistema: " + enderecoExistente.get());
		} else {
			endereco.setCep(cepDigitado);
			repository.save(endereco);
			System.out.println("Endereço salvo no banco de dados.");
		}
	}

	public void buscarCEP() {
		System.out.println("Digite o CEP que deseja buscar: ");
		String cep = scan.next();

		Optional<Endereco> endereco = repository.findByCep(cep);

		if (endereco.isPresent()) {
			System.out.println("Endereço encontrado: " + endereco.get());
		} else {
			System.out.println("CEP não encontrado no sistema.");
		}
	}
}
