package com.quess.geosync.ponto;

import com.quess.geosync.usuario.Usuario;
import com.quess.geosync.usuario.UsuarioNaoEncontradoException;
import com.quess.geosync.usuario.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PontoController {
    private final PontoService service;
    private final UsuarioService usuarioService;

    public PontoController(PontoService service, UsuarioService usuarioService) {
        this.service = service;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/pontos/{ativo}")
    public String showPontosList(@PathVariable("ativo") boolean ativo, Model model) {
        List<Ponto> listPontos = service.listAll(ativo);
        model.addAttribute("listPontos", listPontos);
        model.addAttribute("ativos", ativo);

        try {
            Integer idCentral = usuarioService.getCentral(usuarioService.getUsuarioLogado().getId());
            model.addAttribute("central", idCentral);
        }
        catch (UsuarioNaoEncontradoException | PontoNaoEncontradoException ignored) {}

        return "pontos";
    }

    @GetMapping("/pontos/ativotoggle/{id}")
    public String ativoTogglePonto(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            boolean bloqueado = service.ativoToggle(id);
            String mensagem = "O ponto %d foi ativado";
            if (!bloqueado) {
                mensagem = "O ponto %d foi inativado";
            }
            ra.addFlashAttribute("messageSucess", String.format(mensagem, id));
        }
        catch (PontoNaoEncontradoException e) {
            ra.addFlashAttribute("messageError", e.getMessage());
        }
        return "redirect:/pontos/true";
    }

    @GetMapping("/pontos/renomear/{id}/{nome}")
    public String editNomePonto(@PathVariable("id") Integer id, @PathVariable("nome") String nome, RedirectAttributes ra) {
        try {
            service.renomear(id, nome);
            ra.addFlashAttribute("messageSucess", String.format("O ponto %d foi renomeado", id));
        }
        catch (Exception e) {
            if (e.getClass().equals(PontoNaoEncontradoException.class)) {
                ra.addFlashAttribute("messageError", e.getMessage());
            }
            else {
                ra.addFlashAttribute("messageError", String.format("O nome %s já está sendo usado", nome));
            }
        }
        return "redirect:/pontos/true";
    }

    @GetMapping("/pontos/adotarcentral/{idcentral}")
    public String adotarCentral(@PathVariable("idcentral") Integer idCentral, RedirectAttributes ra) {
        try {
            Usuario usuarioLogado = usuarioService.getUsuarioLogado();
            if (usuarioLogado == null) {
                throw new UsuarioNaoEncontradoException();
            }

            service.adotarCentral(usuarioLogado.getId(), idCentral);
            ra.addFlashAttribute("messageSucess", String.format("Central %d adotada para o seu usuário", idCentral));
        }
        catch (UsuarioNaoEncontradoException | PontoNaoEncontradoException e) {
            ra.addFlashAttribute("messageError", e.getMessage());
        }

        return "redirect:/pontos/true";
    }
}
