package com.quess.geosync.beans.sensor;

import com.quess.geosync.beans.ponto.Ponto;
import exceptions.PontoNaoEncontradoException;
import com.quess.geosync.beans.ponto.PontoService;
import exceptions.SensorNaoEncontradoException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SensorController {
    private final SensorService service;
    private final PontoService pontoService;

    public SensorController(SensorService service, PontoService pontoService) {
        this.service = service;
        this.pontoService = pontoService;
    }

    @GetMapping("/sensores/ativar/{idponto}/{idsensor}/{incluir}")
    public String salvarSensor(@PathVariable("idponto") Integer idPonto, @PathVariable("idsensor") Integer idSensor, @PathVariable("incluir") boolean incluir, RedirectAttributes ra) {
        try {
            Ponto ponto = pontoService.get(idPonto);
            Sensor sensor = service.get(idSensor);

            String sensores = ponto.getSensores().replace(String.format("%d", idSensor), "").trim();
            String mensagem = "removido do";
            if (incluir) {
                sensores = (ponto.getSensores() + String.format(" %d", idSensor)).trim();
                mensagem = "adicionado ao";
            }
            ponto.setSensores(sensores);

            pontoService.salvar(ponto);
            ra.addFlashAttribute("messageSucess", String.format("Sensor de %s foi %s ponto %d", sensor.getNome(), mensagem, idPonto));
        }
        catch (PontoNaoEncontradoException | SensorNaoEncontradoException e) {
            ra.addFlashAttribute("messageError", e.getMessage());
        }
        return "redirect:/pontos/true";
    }
}
