package com.electronic.tables.table;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class Table4x4Tests {
    private Table4x4 table = new Table4x4();
    @Test
    void findCellTest() {
        Optional<Cell> cell = table.findCell("A1");
        Assertions.assertTrue(cell.isPresent());
    }
}

