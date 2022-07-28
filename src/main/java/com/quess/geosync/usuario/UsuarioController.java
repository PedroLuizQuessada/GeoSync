package com.quess.geosync.usuario;

import com.quess.geosync.util.SenhaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/usuarios")
    public String showUsuariosList(Model model) {
        List<Usuario> listUsuarios = service.listAll();
        model.addAttribute("listUsuarios", listUsuarios);
        return "usuarios";
    }

    @GetMapping("/usuarios/new")
    public String showUsuarioForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("pageTitle", "Adicionar usuário");
        return "usuario_form";
    }

    @PostMapping("/usuarios/save")
    public String saveUsuario(Usuario usuario, RedirectAttributes ra) {
        usuario.setSenha(SenhaUtil.criptografar(usuario.getSenha()));

        try {
            usuario.setTentativasAcesso(0);
            service.save(usuario);
            ra.addFlashAttribute("messageSucess", "O usuário foi salvo com sucesso");
        }
        catch (Exception e) {
            ra.addFlashAttribute("messageError", String.format("O e-mail %s já está sendo usado", usuario.getEmail()));
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Usuario usuario = service.get(id);
            model.addAttribute("usuario", usuario);
            model.addAttribute("pageTitle", String.format("Editar usuário %d", usuario.getId()));
            return "usuario_form";
        }
        catch (UsuarioNaoEncontradoException e) {
            ra.addFlashAttribute("messageError", e.getMessage());
            return "redirect:/usuarios";
        }
    }

    @GetMapping("/usuarios/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("messageSucess", String.format("O usuário %d foi deletado", id));
        }
        catch (UsuarioNaoEncontradoException e) {
            ra.addFlashAttribute("messageError", e.getMessage());
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/blocktoggle/{id}")
    public String blockToggleUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            boolean bloqueado = service.blockToggle(id);
            String mensagem = "O usuário %d foi bloqueado";
            if (!bloqueado) {
                mensagem = "O usuário %d foi desbloqueado";
            }
            ra.addFlashAttribute("messageSucess", String.format(mensagem, id));
        }
        catch (UsuarioNaoEncontradoException e) {
            ra.addFlashAttribute("messageError", e.getMessage());
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/admtoggle/{id}")
    public String admToggleUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            boolean adm = service.admToggle(id);
            String mensagem = "O usuário %d se tornou ADM";
            if (!adm) {
                mensagem = "O usuário %d não é mais ADM";
            }
            ra.addFlashAttribute("messageSucess", String.format(mensagem, id));
        }
        catch (UsuarioNaoEncontradoException e) {
            ra.addFlashAttribute("messageError", e.getMessage());
        }
        return "redirect:/usuarios";
    }
 }
