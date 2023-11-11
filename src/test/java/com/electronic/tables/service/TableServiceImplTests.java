package com.electronic.tables.service;

import com.electronic.tables.math.FormulaProcessor;
import com.electronic.tables.table.Table;
import com.electronic.tables.table.Table4x4;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TableServiceImplTests {
    private final Table table = new Table4x4();
    private TableServiceImpl tableService;
    @BeforeEach
    void init() {
        FormulaProcessor formulaProcessor = new FormulaProcessor(table);
        this.tableService = new TableServiceImpl(formulaProcessor);
    }

    @Test
    void processValueTest() {
        String answer = this.tableService.processValue("=34+36");
        Assertions.assertEquals("70.0", answer);
    }

    @Test
    void whenGiveNumber_thenReturnIt() {
        String answer = this.tableService.processValue("500");
        Assertions.assertEquals("500.0", answer);
    }

    @Test
    void whenGiveWrongValue_thenReturnEmptyString() {
        String answer = this.tableService.processValue("=ty");
        Assertions.assertEquals("", answer);
    }
}
