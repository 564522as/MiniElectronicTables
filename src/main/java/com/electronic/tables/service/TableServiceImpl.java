package com.electronic.tables.service;

import com.electronic.tables.math.FormulaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TableServiceImpl implements TableService{
    private FormulaProcessor processor;

    @Autowired
    public TableServiceImpl(FormulaProcessor processor) {
        this.processor = processor;
    }


    @Override
    public String processValue(String value) {
        if (!value.isEmpty() && value.charAt(0) == '=') {
            try {
                return processor.calculateFormula(value.substring(1));
            } catch (Exception e) {
                return "";
            }
        } else {
            try {
                return String.valueOf(Double.parseDouble(value));
            } catch (NumberFormatException e) {
                return "";
            }
        }
    }
}
