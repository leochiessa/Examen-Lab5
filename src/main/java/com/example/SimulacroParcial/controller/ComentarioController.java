package com.example.SimulacroParcial.controller;

import com.example.SimulacroParcial.models.Comentario;
import com.example.SimulacroParcial.repository.ComentarioRepository;
import jdk.nashorn.internal.objects.annotations.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Properties;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {

    @Autowired
    private ComentarioRepository cr;

    @PostMapping("")
    public void agregar(@RequestBody final @NotNull Comentario c) {
        cr.save(c);
    }

    @PostMapping("/{id}/borrar")
    public void borrarComentario(@PathVariable final @NotNull Integer id) {
        cr.deleteById(id);
    }

    @Scheduled(fixedDelay = 600000)
    public void borrarComentariosViejos() {
        Properties tiempo = new Properties();
        //tiempo.load("/SimulacroParcial/src/main/resources/application.properties");
        String t = tiempo.getProperty("tiempo");
        for (Comentario c : cr.findAll()) {
            if (LocalDate.now().compareTo(c.getFecha()) > Integer.valueOf(t)) {
                cr.delete(c);
            }
        }
    }
}