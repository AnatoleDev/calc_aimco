package com.task.app.core.controller;


import com.task.app.core.entity.Calculator;
import com.task.app.core.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculator")
public class CalcController {

    private CalculatorService service;

    @Autowired
    public CalcController(CalculatorService service) {
        this.service = service;
    }

    @GetMapping(path = "/{expression}")
    public Calculator setExpression(@PathVariable(name = "expression") final String expression) {
        return service.calculate(expression);
    }

    @GetMapping(path = "get/{expression}")
    public Calculator getExpression(@PathVariable(name = "expression") final Long expression) {
        return service.getExpression(expression);
    }
}