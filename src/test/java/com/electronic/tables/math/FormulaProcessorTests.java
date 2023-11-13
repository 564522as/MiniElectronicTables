package com.electronic.tables.math;

import com.electronic.tables.table.Table;
import com.electronic.tables.table.Table4x4;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FormulaProcessorTests {
    private FormulaProcessor formulaProcessor;
    private Table table = new Table4x4();
    @BeforeEach
    public void init() {
        formulaProcessor = new FormulaProcessor(table);
    }
    @Test
    void operationExecuteTest() {
        Double answer1 = formulaProcessor.operationExecute('+', 4d, 5d);
        Double answer2 = formulaProcessor.operationExecute('-', 12d, 15d);
        Double answer3 = formulaProcessor.operationExecute('*', 2d, 5d);
        Double answer4 = formulaProcessor.operationExecute('/', 10d, 100d);

        Assertions.assertEquals(9d, answer1);
        Assertions.assertEquals(3d, answer2);
        Assertions.assertEquals(10d, answer3);
        Assertions.assertEquals(10d, answer4);
    }
    @Test
    void checkOnCellRefTest() throws Exception {
        this.table.findCell("A1").get().setValue("34");
        String answer = formulaProcessor.checkOnCellRef("A1");

        Assertions.assertEquals("34", answer);
    }

    @Test
    void getNumberTest() throws Exception {
        String answer = this.formulaProcessor.getNumber("3460A1");
        Assertions.assertEquals("3460", answer);

        String answer2 = this.formulaProcessor.getNumber("A1+35");
        Assertions.assertEquals("35", answer2);
    }

    @Test
    void getPostfixForm() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("12");
        list.add("15");
        list.add("+");
        List<String> trialList = this.formulaProcessor.getPostfixForm("12+15");
        for (int i = 0; i < 2; i++) {
            Assertions.assertEquals(list.get(i), trialList.get(i));
        }
    }

    @Test
    void calculateFormulaTest() throws Exception {
        String answer = this.formulaProcessor.calculateFormula("123*45/5+100");
        Assertions.assertEquals("1207.0", answer);
    }

    @Test
    void calculateFormulaWithRef() throws Exception {
        this.table.findCell("B3").get().setValue("10");
        this.table.findCell("C1").get().setValue("5");

        String answer = this.formulaProcessor.calculateFormula("(B3+B3)*B4");
        Assertions.assertEquals("0.0", answer);

        this.formulaProcessor = new FormulaProcessor(table);
        String answer2 = this.formulaProcessor.calculateFormula("(B3+B3)*C1");
        Assertions.assertEquals("100.0", answer2);
    }

    @Test()
    void whenErrorInFormula_thenThrowException() throws Exception {
       Assertions.assertThrows(Exception.class, () -> this.formulaProcessor.calculateFormula("as34+45"));
    }
}
