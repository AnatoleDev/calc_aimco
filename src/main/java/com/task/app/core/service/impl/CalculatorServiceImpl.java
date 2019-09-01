package com.task.app.core.service.impl;

import com.task.app.core.entity.Calculator;
import com.task.app.core.repositories.CalculatorRepository;
import com.task.app.core.service.CalculatorService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;

import static java.util.Objects.nonNull;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    private CalculatorRepository repository;

    public CalculatorServiceImpl(CalculatorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Calculator calculate(Calculator calculator) {
        calculator.evaluate();
        return repository.save(calculator);
    }

    @Override
    public Calculator getExpression(final Long expressionId) {
        return repository.findById(expressionId);
    }

    @Override
    public List<Calculator> getAll() {
        return repository.findAll();
    }

    @Override
    public int countForData(final LocalDate date) {
        return repository.countAllByDate(date);
    }

    @Override
    public int operation(final String expression) {
        return repository.countAllByExpression(expression);
    }

    @Override
    public List<String> onDate(LocalDate date) {
        return repository.findAllByDate(date);
    }

    @Override
    public List<String> onOperation(String expression) {
        return repository.findAllByExpressionLike(expression);
    }

    @Override
    public String popular() {
        Map<Character, Integer> map = new HashMap<>();
        getAll().forEach(calculator -> {
                    String str = calculator.getExpression();
                    char[] chars = str.toCharArray();
                    for (Character c : chars) {
                        if (!isOperator(c)) {
                            int k = 1;
                            if (map.containsKey(c)) {
                                k = map.get(c) + 1;
                            }
                            map.put(c, k);
                        }
                    }
                }
        );
        Set<Entry<Character, Integer>> setvalue = map.entrySet();
        Iterator<Entry<Character, Integer>> i = setvalue.iterator();
        Entry<Character, Integer> max = null;
        while (i.hasNext()) {
            Entry<Character, Integer> me = i.next();
            if (max == null) {
                max = me;
            } else if (me.getValue() > max.getValue()) {
                max = me;
            }
        }
        return nonNull(max)
                ? max.getKey().toString()
                : "";
    }

    private boolean isOperator(final Character token) {
        return '+' == token || '-' == token || '(' == token || ')' == token
                || '*' == token || '.' == token || '/' == token || '^' == token;
    }
}