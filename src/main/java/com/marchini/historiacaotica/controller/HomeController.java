package com.marchini.historiacaotica.controller;
import com.marchini.historiacaotica.model.Contribution;
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

    public HomeController(ContributionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home(Model model) {

        List<Contribution> contributions = repository.findAllByOrderByDataDesc();

        String historia = contributions.stream()
                .map(c -> c.getTexto().replaceAll("[,\\s]+$", ""))
                .collect(Collectors.joining(", "));

        model.addAttribute("historia", historia);
        model.addAttribute("contributions", contributions);

        return "index";
    }

    @PostMapping("/contribute")
    public String contribute(@RequestParam("texto") String texto,
                             RedirectAttributes redirectAttributes) {

        String[] palavras = texto.trim().split("\\s+");

        if (palavras.length > 6) {
            redirectAttributes.addFlashAttribute("erro", "Máximo de 6 palavras permitido");
            return "redirect:/";
        }

        Contribution contribution = new Contribution(texto);
        repository.save(contribution);

        return "redirect:/";
    }

    @PostMapping("/clear")
    public String clearHistory() {
        repository.deleteAll();

        return "redirect:/";
    }

}
