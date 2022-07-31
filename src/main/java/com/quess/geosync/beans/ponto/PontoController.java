package com.quess.geosync.beans.ponto;

import com.quess.geosync.beans.registro.RegistroService;
import com.quess.geosync.beans.sensor.SensorService;
import com.quess.geosync.beans.usuario.Usuario;
import exceptions.UsuarioNaoEncontradoException;
import com.quess.geosync.beans.usuario.UsuarioService;
import exceptions.PontoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class PontoController {

    @Value("${server.link}")
    private String servidorLink;

    @Value("${server.servlet.context-path}")
    private String servidorContextPath;

    private final PontoService pontoService;
    private final UsuarioService usuarioService;
    private final SensorService sensorService;
    private final RegistroService registroService;

    public PontoController(PontoService pontoService, UsuarioService usuarioService, SensorService sensorService, RegistroService registroService) {
        this.pontoService = pontoService;
        this.usuarioService = usuarioService;
        this.sensorService = sensorService;
        this.registroService = registroService;
    }

    @GetMapping("/pontos/{ativo}")
    public String showPontosList(@PathVariable("ativo") boolean ativo, Model model) {
        model.addAttribute("listPontos", pontoService.listAll(ativo));
        model.addAttribute("ativos", ativo);
        model.addAttribute("listSensores", sensorService.listAll());

        try {
            Integer idCentral = usuarioService.getCentral(usuarioService.getUsuarioLogado().getId());
            model.addAttribute("central", idCentral);
        }
        catch (UsuarioNaoEncontradoException | PontoNaoEncontradoException ignored) {}

        model.addAttribute("link", servidorLink);
        model.addAttribute("contextPath", servidorContextPath);

        return "pontos";
    }

    @GetMapping("/pontos/ativotoggle/{id}")
    public String ativoTogglePonto(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            boolean bloqueado = pontoService.ativoToggle(id);
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
            pontoService.renomear(id, nome);
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

            pontoService.adotarCentral(usuarioLogado.getId(), idCentral);
            ra.addFlashAttribute("messageSucess", String.format("Central %d adotada para o seu usuário", idCentral));
        }
        catch (UsuarioNaoEncontradoException | PontoNaoEncontradoException e) {
            ra.addFlashAttribute("messageError", e.getMessage());
        }

        return "redirect:/pontos/true";
    }

    @GetMapping("/pontos/consultar/{id}")
    public String consultarPonto(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Ponto ponto = pontoService.get(id);
            List<String> sensoresId = Arrays.asList(ponto.getSensores().split(" "));

            Map<String, Object> infos = registroService.getInfos(ponto.getId(), sensoresId);

            @SuppressWarnings("unchecked")
            List<String> colunas = (List<String>) infos.get("colunas");

            @SuppressWarnings("unchecked")
            List<Map<String, String>> registros = (List<Map<String, String>>) infos.get("registros");

            model.addAttribute("pageTitle", String.format("Consultar ponto %s", ponto.getNome()));
            model.addAttribute("listColunas", colunas);
            model.addAttribute("listRegistros", registros);
            return "ponto";
        }
        catch (PontoNaoEncontradoException e) {
            ra.addFlashAttribute("messageError", e.getMessage());
            return "redirect:/pontos/true";
        }
    }
}
