package com.marchini.historiacaotica.service;

import com.marchini.historiacaotica.model.Contribuicao;
import com.marchini.historiacaotica.repository.ContributionRepository;
import org.springframework.stereotype.Service;

@Service
public class ContribuicaoService {

    private final ContributionRepository repository;

    public ContribuicaoService(ContributionRepository repository) {
        this.repository = repository;
    }

    public void salvar(String texto) {
        String textoLimpo = texto == null ? "" : texto.trim();
        String[] palavras = textoLimpo.split("\\s+");

        if (textoLimpo.isEmpty() || palavras.length < 3 || palavras.length > 6)
            throw new IllegalArgumentException("O texto deve ter entre 3 e 6 palavras");

        if (textoLimpo.matches(".*(.)\\1{3,}.*"))
            throw new IllegalArgumentException("Evite repetir o mesmo caractere várias vezes seguidas");

        repository.save(new Contribuicao(textoLimpo));
    }
}