package com.skarp.prio.base;

// Rest Controller
import org.springframework.web.bind.annotation.RestController;

// Mappings
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class BaseController {

    @GetMapping("/")
    public Base base(@RequestParam(value = "name", defaultValue = "Team Skarp API") String name) {
        return new Base(name);
    }

}