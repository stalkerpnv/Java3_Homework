package homework1;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Java. Level 3* Lesson 1. Homework 1.
 *
 * @author Petrov Nikolay
 * @version dated April 20, 2019
 */

/* 1. Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);
   2. Написать метод, который преобразует массив в ArrayList;
   3. Задача про Fruit и Box*/

public class Homework1 {

    private <T> void swapElemArr(int firstInd, int secondInd, T[] array) {
        T temp = array[firstInd];
        array[firstInd] = array[secondInd];
        array[secondInd] = temp;
    }

    private <T> ArrayList<T> convertArrayToArrayList(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    public static void main(String[] args) {
        Homework1 hw1 = new Homework1();
        String[] str = {"First", "Second", "Third", "Fourth", "Fifth"};
        System.out.println("Array str before swap " + Arrays.toString(str));

        hw1.swapElemArr(0, 4, str);
        System.out.println("Array str after swap " + Arrays.toString(str));

        Double[] dArr = {1.0, 2.0, 3.0, 4.0, 5.0};
        System.out.println("Array dArr before swap " + Arrays.toString(dArr));

        hw1.swapElemArr(2, 4, dArr);
        System.out.println("Array dArr after swap " + Arrays.toString(dArr));

        System.out.println(hw1.convertArrayToArrayList(dArr));

        Box<Apple> appleBox = new Box<>();
        Box<Orange> orangeBox = new Box<>();
        for (int i = 0; i < 10; i++) {
            appleBox.addFruit(new Apple());
            orangeBox.addFruit(new Orange());
        }
        System.out.println(appleBox.getFruits());
        System.out.println(orangeBox.getFruits());

        Box<Apple>appleBox1 = new Box<>();
        appleBox1.addFruit(new Apple());
        appleBox.moveToAnotherBox(appleBox1);
        System.out.println(appleBox.getFruits());
        System.out.println(appleBox1.getFruits());
    }
}

abstract class Fruit {
    private float weight;

    Fruit(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " " + weight;
    }
}

class Apple extends Fruit {
    Apple() {
        super(1.0f);
    }
}

class Orange extends Fruit {
    Orange() {
        super(1.5f);
    }
}
class Box<T extends Fruit>{
    private ArrayList<T> fruits;

    public Box() {
        this.fruits = new ArrayList<>();
    }

    public void addFruit(T fruit){
        fruits.add(fruit);
    }

    public float getWeight(){
        float weight = 0.0f;
        for(T fruit:fruits){
            weight = weight + fruit.getWeight();
        }
        return weight;
    }

    public boolean Compare(Box<?> box){
        return (Float.compare(this.getWeight(), box.getWeight())) == 0;
    }

    public void moveToAnotherBox(Box<T> box){
        box.addFruits(getFruits());
        fruits.clear();
    }

    public void addFruits(ArrayList<T> fruits){
        this.fruits.addAll(fruits);
    }
    public ArrayList<T> getFruits(){
        return fruits;
    }
}


