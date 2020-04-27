package com.Desert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Monster monster = new Monster();
        Method method = monster.getClass().getMethod("eat", String.class);
        method.invoke(monster, "F");
    }
}

class Monster {

    public void eat(String food) {
        System.out.println("Eating " + food);
    }
}
