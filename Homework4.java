/**
 * Java. Level 4* Lesson 4. Homework 4.
 *
 * @author Petrov Nikolay
 * @version dated May 06, 2019
 */
/*
1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС).
Используйте wait/notify/notifyAll.
2. На серверной стороне сетевого чата реализовать управление потоками через ExecutorService.*/

public class Homework4 {
    final Object monitor = new Object();
    volatile char currentLetter = 'A';

    public static void main(String[] args) {
        Homework4 hw4 = new Homework4();
        new Thread(() -> hw4.printLetter('A', 'B', 5)).start();
        new Thread(() -> hw4.printLetter('B', 'C', 5)).start();
        new Thread(() -> hw4.printLetter('C', 'A', 5)).start();
    }

    void printLetter(char mainLetter, char nextLetter, int times) {
        synchronized (monitor) {
            try {
                for (int i = 0; i < times; i++) {
                    while (currentLetter != mainLetter) {
                        monitor.wait();
                    }
                    System.out.print(mainLetter);
                    currentLetter = nextLetter;
                    monitor.notifyAll();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
