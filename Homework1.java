package homework1;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Java. Level 3* Lesson 1. Homework 1.
 *
 * @author Petrov Nikolay
 * @version dated April 20, 2019
 */

public class Homework1 {

    private <T> void swapElemArr(int firstInd, int secondInd, T[] array) {
        T temp = array[firstInd];
        array[firstInd] = array[secondInd];
        array[secondInd] = temp;
    }

    private <T> ArrayList<T> convertArrayToArrayList(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
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
