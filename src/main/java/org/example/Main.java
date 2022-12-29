package org.example;

import org.example.models.Item;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

class ReflectionExplorer {

    /**
     * Gets class metadata instance using obj.getClass() method
     * @param obj: Instance of a class
     */
    public void getClassMetaFromObject(Object obj) {
        Class<?> clazz = obj.getClass();
        displayMetadataForClass(clazz);
    }

    /**
     * Getting properties of Item clas using reflection
     * Using Class.forName() method
     * @param fullyQualifiedClassName:- Fully qualified class name (e.g. com.tcs.models.Human)
     * @throws ClassNotFoundException:- Throws ClassNotFoundException if the class is not found.
     */
    public void getClassMetaFromClassName(String fullyQualifiedClassName) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(fullyQualifiedClassName);
        displayMetadataForClass(clazz);
    }

    public void getClassMetaFromClass(Class<?> clazz) {
        displayMetadataForClass(clazz);
    }

    /**
     * Invokes newInstance method of 'Class' class, as it can invoke default constructor
     * @param clazz :- Class Instance
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Object getDefaultClassInstance(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return clazz.getDeclaredConstructor().newInstance();
    }

    /**
     * Invokes newInstance method of 'Constructor' class, as it can invoke parameterized constructor
     */
    public Object getParametrizedClassInstance(Class<?> clazz,
                                             List<?> initArgs,
                                             Class<?> ...parameterTypes) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return clazz.getConstructor(parameterTypes).newInstance(initArgs.toArray());
    }

    public void invokeMethodOfClass(String fullyQualifiedClassName, String methodName,
                                    List<Class<?>> methodTypeArgs,
                                    List<?> methodArgs,
                                    List<Class<?>> constructorTypeArgs,
                                    List<?> constructorInitArgs
                                    ) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // Get instance of java.lang.Class
        Class<?> clazz = Class.forName(fullyQualifiedClassName);

        // Get a new instance of the above class
        Object obj = clazz.getConstructor(constructorTypeArgs.toArray(new Class[0])).
                newInstance(constructorInitArgs.toArray());
        Method method = clazz.getDeclaredMethod(methodName,
                methodTypeArgs.toArray(new Class[0])
                );
        method.setAccessible(true);
        method.invoke(obj, methodArgs.toArray());
    }

    private void displayMetadataForClass(Class<?> clazz) {
        System.out.println("================================================");
        System.out.println("Class Name is: " + clazz.getName());
        System.out.println("Fields are: ");
        Arrays.asList(clazz.getDeclaredFields()).forEach(
                field -> {
                    System.out.println(field.getName() + " " + field.getType());
                }
        );
        System.out.println("Is Interface " + clazz.isInterface());
        System.out.println("================================================");
    }

}

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("Hello world!");
        ReflectionExplorer refExp = new ReflectionExplorer();

        // 1. Using Object
        Item item = new Item();
        refExp.getClassMetaFromObject(item);

        // 2. Using fully qualified class name
        refExp.getClassMetaFromClassName("org.example.models.Item");

        // 3. Using an instance of Class directly. (eg. .class)
        refExp.getClassMetaFromClass(Item.class);

        System.out.println("Creating instances of class");
        // Get instance of class and invoke the display method
        try {
            System.out.println("Using default constructor");
            Item item1 = (Item) refExp.getDefaultClassInstance(Item.class);
            item1.displayItem();
        } catch(Exception ex) {
            System.out.println("Exception Details: ");
            ex.printStackTrace();
        }

        // Get instance of class using parameterized constructor
        try {
            System.out.println("Using parameterized constructor");
            Item item2 = (Item) refExp.getParametrizedClassInstance(
                    Item.class,
                    Arrays.asList(1, "Aritra", 20.4F),
                    Integer.class, String.class, Float.class
                    );
            item2.displayItem();
        } catch(Exception ex) {
            System.out.println("Exception Detials: ");
            ex.printStackTrace();
        }

        // Create an instance of Item class by invoking its parameterized constructor
        // and invoke its private method
        try {
            refExp.invokeMethodOfClass("org.example.models.Item",
                        "displayPrivateInfo",
                                    List.of(String.class),
                                    List.of("Main"),
                                    List.of(Integer.class, String.class, Float.class),
                                    List.of(12, "Aag", 24.5F)
                    );
        } catch(Exception ex) {
            System.out.println("Exception occurred while invoking private method");
            ex.printStackTrace();
        }

    }
}