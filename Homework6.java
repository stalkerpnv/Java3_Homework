/**
 * Java. Level 3* Lesson 6 Homework 6.
 *
 * @author Petrov Nikolay
 * @version dated May 16, 2019
 */
/* 1. Добавить на серверную сторону чата логирование, с выводом информации о действиях на сервере
(запущен, произошла ошибка, клиент подключился, клиент прислал сообщение/команду).
2. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку, иначе в методе
 необходимо выбросить RuntimeException. Написать набор тестов для этого метода (по 3-4 варианта входных данных).
Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
3. Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки или единицы,
то метод вернет false; Написать набор тестов для этого метода (по 3-4 варианта входных данных).
4. *Добавить на серверную сторону сетевого чата логирование событий.*/

public class Homework6 {

    public static void main(String[] args) {
        new ChatForm();
    }
}
