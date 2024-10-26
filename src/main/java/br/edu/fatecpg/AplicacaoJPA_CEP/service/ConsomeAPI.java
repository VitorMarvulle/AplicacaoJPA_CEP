package br.edu.fatecpg.AplicacaoJPA_CEP.service;

import br.edu.fatecpg.AplicacaoJPA_CEP.model.Endereco;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsomeAPI {

  public Endereco retornaEnd(String cep) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://viacep.com.br/ws/" + cep + "/json/"))
            .build();
    HttpResponse<String> response = client
            .send(request, HttpResponse.BodyHandlers.ofString());

    // Desserializando o JSON para o objeto Endereco usando Jackson
    ObjectMapper mapper = new ObjectMapper();
    Endereco endereco = mapper.readValue(response.body(), Endereco.class);

    return endereco;
  }
}
