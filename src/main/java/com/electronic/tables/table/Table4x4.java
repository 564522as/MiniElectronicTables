package com.electronic.tables.table;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Table4x4 extends Table{
    @Override
    public void fillTable() {
        String[] columns = {"A", "B", "C", "D"};
        String[] rows = {"1", "2", "3", "4"};
        for (String column: columns) {
            for (String row: rows) {
                addCell(new Cell("", column+row));
            }
        }
    }
}
