package com.electronic.tables.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Table {
    private List<Cell> cells;
    public Table() {
        cells = new ArrayList<>();
        fillTable();
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void addCell(Cell cell) {
        this.cells.add(cell);
    }

    public Optional<Cell> findCell(String address) {
        for (Cell cell: cells) {
            if (cell.getAddress().equals(address)) {
                return Optional.of(cell);
            }
        }
        return Optional.empty();
    }

    public String getValueByAddress(String address) throws Exception {
        for (Cell cell: cells) {
            if (cell.getAddress().equals(address)) {
                return cell.getValue();
            }
        }
        throw new Exception();
    }

    public abstract void fillTable();
}


