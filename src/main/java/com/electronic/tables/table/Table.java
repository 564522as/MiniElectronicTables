package com.electronic.tables.controller;

import com.electronic.tables.service.TableService;
import com.electronic.tables.table.Cell;
import com.electronic.tables.table.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class TableController {
    private final Table table;
    private final TableService tableService;

    @Autowired
    public TableController(Table table, TableService tableService) {
        this.table = table;
        this.tableService = tableService;
    }

    @GetMapping("/table")
    public String getIndex(@ModelAttribute("cell") Cell cell) {
        return "index";
    }
    @PostMapping("/table")
    public String processTable(@ModelAttribute("cell") Cell cell) {
        Optional<Cell> optionalCell = table.findCell(cell.getAddress());
        optionalCell.ifPresent(value -> value
                .setValue(tableService.processValue(cell.getValue())));

        return "index";
    }

    @ModelAttribute("cells")
    public List<Cell> cells() {
        return this.table.getCells();
    }
}

