package com.marchini.historiacaotica.controller;

import com.marchini.historiacaotica.model.Contribuicao;
import com.marchini.historiacaotica.service.ContribuicaoService;
import com.marchini.historiacaotica.repository.ContributionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final ContributionRepository repository;
    private final ContribuicaoService contribuicaoService;

    public HomeController(ContributionRepository repository,
                          ContribuicaoService contribuicaoService) {
        this.repository = repository;
        this.contribuicaoService = contribuicaoService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Contribuicao> contributions = repository.findAllByOrderByDataAsc();
        String historia = contributions.stream()
                .map(c -> c.getTexto().replaceAll("[,\\s]+$", ""))
                .collect(Collectors.joining(", "));
        model.addAttribute("historia", historia);
        model.addAttribute("contributions", contributions);
        return "index";
    }

    @PostMapping("/contribuicao")
    public String contribuicao(@RequestParam("texto") String texto,
                               RedirectAttributes redirectAttributes) {
        try {
            contribuicaoService.salvar(texto);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/";
        }
        return "redirect:/";
    }
}