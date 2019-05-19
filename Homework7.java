/**
 * Java. Level 3. Lesson 7. Homework 7
 *
 * @author Petrov Nikolay
 * @version May 20, 2019
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*1. Создать класс, который может выполнять «тесты».
В качестве тестов выступают классы с наборами методов, снабженных аннотациями ​@Test​.
Класс, запускающий тесты, должен иметь статический метод ​start(Class testClass)​, которому
в качестве аргумента передается объект типа ​Class​. Из «класса-теста» вначале должен быть
запущен метод с аннотацией ​@BeforeSuite​, если он присутствует. Далее запускаются методы с аннотациями ​@Test​, а
по завершении всех тестов – метод с аннотацией ​@AfterSuite​.
К каждому тесту необходимо добавить приоритеты (​int​-числа от 1 до 10), в соответствии с которыми
будет выбираться порядок их выполнения. Если приоритет одинаковый, то порядок не имеет
значения. Методы с аннотациями ​@BeforeSuite и ​@AfterSuite должны присутствовать в
единственном экземпляре. Если это не так – необходимо бросить ​RuntimeException при запуске«тестирования».
P.S. Это практическое задание – проект, который пишется «с нуля». Данная задача не связана
напрямую с темой тестирования через JUnit*/

public class Homework7 {
    public static void main(String[] args) {
        ClassForTesting.start(ClassForTesting.class);
    }
}

class ClassForTesting {

    public static void start(Class cls) {
        performTest(cls);
    }

    public static void performTest(Class cls) throws RuntimeException {
        ClassForTesting testobj;
        try {
            testobj = (ClassForTesting) cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Method beforeMethod = null;
        Method arferMethod = null;
        Method[] methods = cls.getMethods();
        List<MethodWithPriority> testingMethods = new ArrayList<>();

        for (Method method : methods) {
            if (method.getAnnotation(BeforeSuite.class) != null) {
                if (beforeMethod != null)
                    throw new RuntimeException("The method with the @BeforeSuite annotation should be one");
                beforeMethod = method;
            } else if (method.getAnnotation(AfterSuite.class) != null) {
                if (arferMethod != null)
                    throw new RuntimeException("The method with the @AfterSuite annotation should be one");
                arferMethod = method;
            } else if (method.getAnnotation(Test.class) != null) {
                Test annotationTst = method.getAnnotation(Test.class);
                testingMethods.add(new MethodWithPriority(method, annotationTst.value()));
            }

        }
        testingMethods.sort(
                Comparator.comparing(MethodWithPriority::getPriority));

        try {
            if (beforeMethod != null)
                beforeMethod.invoke(testobj);

            for (MethodWithPriority methodWP : testingMethods)
                methodWP.method.invoke(testobj);

            if (arferMethod != null)
                arferMethod.invoke(testobj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(value = 5)
    public void Test5() {
        System.out.println("Performed Test №5");
    }

    @Test(value = 3)
    public void Test2() {
        System.out.println("Performed Test №2");
    }

    @Test(value = 1)
    public void Test1() {
        System.out.println("Performed Test №1");
    }

    @BeforeSuite
    public void BeforeAll() {
        System.out.println("This method is performed before all");
    }

    @AfterSuite
    public void AfterAll() {
        System.out.println("This method is performed after all");
    }
}

class MethodWithPriority {
    public Method method;
    public Integer priority;

    public MethodWithPriority(Method method, int priority) {
        this.method = method;
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface BeforeSuite {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface AfterSuite {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Test {
    int value();
}
