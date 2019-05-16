
/* Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
   Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
   идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку, иначе в методе
   необходимо выбросить RuntimeException. Написать набор тестов для этого метода (по 3-4 варианта входных данных).
   Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].*/
public class GetArray {
    public int[] getArray(int[] array) {
        int index = -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4) {
                index = i + 1 ;
            }
        }
        if (index == -1) throw new RuntimeException();
        int newArrayLength = array.length - index;
        int[] newArray = new int[newArrayLength];

        for (int i = 0; i <newArray.length ; i++) {
            newArray[i] = array[index];
            index++;
        }
        return newArray;
    }
}
