package com.rko.nearbyplace.controller;

import com.rko.nearbyplace.aop.LogExecutionTime;
import com.rko.nearbyplace.dto.InputDto;
import com.rko.nearbyplace.service.DirectionService;
import com.rko.nearbyplace.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;

@Controller
@RequiredArgsConstructor
public class DirectionController {

    private final DirectionService directionService;
    private final ShortUrlService shortUrlService;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @PostMapping("/search")
    public ModelAndView postDirection(@ModelAttribute InputDto inputDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("output");
        modelAndView.addObject("outputFormList",
                directionService.recommendPlaceList(inputDto));

        return modelAndView;
    }

    @GetMapping("/{shortKey}")
    @LogExecutionTime
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortKey) {
        String originalUrl = shortUrlService.getOriginalUrl(shortKey);
        if (originalUrl != null) {
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
