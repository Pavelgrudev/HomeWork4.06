package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

@RestController
@RequestMapping("/info")
@Tag(name = "API для получении информации о приложении")
public class infoController {

    private final InfoService infoService;

    public infoController(InfoService infoService) {
        this.infoService = infoService;
    }
    @GetMapping("/getPort")
    public String getPort(){
        return infoService.getPort();
    }

    @GetMapping("stream-calculate")
    public ResponseEntity<Void> calculateWithStream(){
    infoService.calculateWithStream();
    return  ResponseEntity.ok().build();}
}
