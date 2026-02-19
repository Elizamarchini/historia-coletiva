package com.marchini.historiacaotica.controller;
import com.marchini.historiacaotica.model.Contribuicao;
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

        String textoLimpo = texto == null ? "" : texto.trim();
        String[] palavras = textoLimpo.split("\\s+");

        if (textoLimpo.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "O texto deve ter entre 3 a 6 palavras");
            return "redirect:/";
        }

        if (palavras.length < 3 || palavras.length > 6) {
            redirectAttributes.addFlashAttribute("erro", "O texto deve ter entre 3 a 6 palavras");
            return "redirect:/";
        }

        if (textoLimpo.matches(".*(.)\\1{3,}.*")) {
            redirectAttributes.addFlashAttribute("erro",
                    "Evite repetir o mesmo caractere várias vezes seguidas");
            return "redirect:/";
        }

        Contribuicao contribution = new Contribuicao(texto);
        repository.save(contribution);

        return "redirect:/";
    }
}
