# MiniElectronicTables
Для запуска программы необходимо архивировать его в jar файл с момощью maven(~> mvn package), 
далее запустить его с помощью команды: ~>java -jar jarFileName

После запуска по адресу http://localhost:8080/table будет находиться таблица 4 на 4, с заполняемыми ячейками
В ячейку можно ввести число или формулу, при вводе обыного числа он в любом случае будет певеводиться в тип double.
Формулу можно ввести после знака "=", она может содержать как обычные числа, так и ссылки на другие ячейки по его адресу

Между числами можно производить 4 операции +,-,/,* и брать выражения в скобки. Чтобы зафиксировать значение ячейки необходимо 
нажать enter на ней, если этого не сделать и перейти к другой ячейке, то изменения в первой ячейке не сохранятся.
Если в формуле содержится ошибка, то содержимое ячейки обнулится. В случае указания в качестве содержимого недопустимых 
значений, значение ячейки также будет обнуляться

