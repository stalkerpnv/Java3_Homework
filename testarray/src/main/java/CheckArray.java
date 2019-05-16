
/*Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки или единицы,
то метод вернет false; Написать набор тестов для этого метода (по 3-4 варианта входных данных).
*/

public class CheckArray {
    public boolean checkArray(int[] array) {
        boolean containsOne = false;
        boolean containsFour = false;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) containsOne = true;
            if (array[i] == 4) containsFour = true;
        }
        return (containsOne || containsFour);
    }

}
