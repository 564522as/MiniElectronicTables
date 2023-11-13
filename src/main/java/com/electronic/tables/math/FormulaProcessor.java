package com.electronic.tables.math;

import com.electronic.tables.table.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.*;
//Новый бин класса создается на каждый запрос, так как один объект может обработать только одну формулу
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class FormulaProcessor {
    private final Table table;
    private int position;                                         //Текущая позиция в формуле
    List<String> postfixArr = new ArrayList<>();                  //Хранит постфиксную запись формулы
    private final Stack<Character> operations = new Stack<>();    //Промежуточный стек для хранения опреций
    private final Stack<String> numbers = new Stack<>();          //Стек для хранения чисел формулы и промежуточных вычислений
    private final Map<Character, Integer> operationsPriority;     //Хранит приоритет оперций для их сравнения

    {
        operationsPriority = new HashMap<>();
        operationsPriority.put('(', 0);
        operationsPriority.put(')', 0);
        operationsPriority.put('-', 1);
        operationsPriority.put('+', 1);
        operationsPriority.put('/', 2);
        operationsPriority.put('*', 2);
    }
    @Autowired
    public FormulaProcessor(Table table) {
        this.table = table;
    }

    public String calculateFormula(String formula) throws Exception {
        List<String> list = getPostfixForm(formula);
        for (String s: list) {                                                                  //Обрабатываем посфиксную запись
            if (!operationsPriority.containsKey(s.charAt(0))) {                                 //Если это не опреция, то заносим в стек с числами
                numbers.push(s);
            } else {
                Double result = operationExecute(s.charAt(0),                                   //Выплняем соответствующую операцию между двумя последними числами в стеке
                        Double.parseDouble(numbers.pop()), Double.parseDouble(numbers.pop()));
                numbers.push(result.toString());                                                //Результат кладем вверх стека
            }
        }
        return numbers.pop();
    }

    public List<String> getPostfixForm(String formula) throws Exception {
        for (; position < formula.length(); position++) {
            Character current = formula.charAt(position);
            boolean isOperation = checkCharOnOperation(current);            //Проверям является ли текущее значение опрецией

            String trialNumber = "";
            if (!isOperation) {                                             //Если это не операция, проверям если это обычное число или ссылка на другую ячейку
                trialNumber = getNumber(formula);
            }

            if (!trialNumber.isEmpty()) postfixArr.add(trialNumber);       //Если getNumber() что то вернуло, то добавляем его в посфиксную запись

        }
        for (Character c: operations) {                                    //Все оставшиеся в промежуточном стеке опреции добавляем в запись
            postfixArr.add(c.toString());
        }
        return postfixArr;
    }

    public boolean checkCharOnOperation(Character current) {
        if (current == '(') {                                                  //Если это открывающая скобка просто добавляем в стек
            operations.push(current);
            return true;
        } else if (current == ')') {                                            //Если закрывающая скобка то выбрасываем все операции из стека
            while (operations.size() > 0 && operations.peek() != '(') {         //в запись, пока не дойдем до открывающей скобки
                postfixArr.add(operations.pop().toString());
            }
            operations.pop();                                                   //Удаляем из стека открывающую скобку
            return true;
        } else if (this.operationsPriority.containsKey(current)) {               //Если это обычная операция, то выталкиваем из стека в запись
            while (operations.size() > 0 &&                                      //операции, приоритет которых выше
                    operationsPriority.get(operations.peek()) >= operationsPriority.get(current)) {
                postfixArr.add(operations.pop().toString());
            }
            operations.push(current);                                            //Добавляем операцию в стек
            return true;
        }
        return false;
    }

    public String getNumber(String formula) throws Exception {
        StringBuilder number;
        number = new StringBuilder(checkOnCellRef(formula));        //Для начала проверям является ли следующие два символа ссылкой на другую ячейку

        for (; position < formula.length(); position++) {
            if (Character.isDigit(formula.charAt(position))) {      //Если это цифра, добавляем в number
                number.append(formula.charAt(position));
            } else if (formula.charAt(position) == '.' && formula.charAt(position-1) != '.') {  //Если неповторяющаяся точка, то тоже
                number.append('.');
            } else {
                position--;                                          //Иначе возварщаем позицию на пред. символ
                break;
            }
        }

        return number.toString();
    }

    public String checkOnCellRef(String formula) throws Exception {
        Character current = formula.charAt(position);
        StringBuilder number;
        if (position == formula.length()-1) {                   //Если это полседний символ, то он не может быть частью ссылки
            return "";
        }

        String trialRef = String.valueOf(current)  + formula.charAt(position + 1);      //предполагаемая ссылка

        if (table.findCell(trialRef).isPresent()) {                         //Пытаемся найти в таблице
            String value = table.findCell(trialRef).get().getValue();
            position += 2;
            if (value.isEmpty()) value = "0";
            number = new StringBuilder(value);                              //В случае нахождения возвращаем содержимое таблицы
        } else if(!Character.isDigit(current)){                             //Если данный символ не часть ссылки, не является цифрой, а также оперцией
            throw new Exception();                                          //то формула содержит ошибку
        } else {
            return "";
        }
        return number.toString();
    }

    public Double operationExecute(Character operation, Double left, Double right) {
        switch (operation) {            //В зависимости от опреции выполняем ее, между двумя числами
            case '+' -> {
                return right + left;
            }
            case '-' -> {
                return right - left;
            }
            case '/' -> {
                return right / left;
            }
            case '*' -> {
                return right * left;
            }
        }
        return null;
    }
}

