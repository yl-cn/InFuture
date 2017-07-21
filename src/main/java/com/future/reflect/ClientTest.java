package com.future.reflect;

import java.lang.reflect.Array;

public class ClientTest {
    public static void main(String args[]) throws Exception {
        Tester test = new Tester();
        Tester[] tests = new Tester[0];
        Class<?> c1 = tests.getClass().getComponentType();
        Class<?> c2 = Tester.class;
        Class<?> c3 = test.getClass();

        System.out.println(c1.getName());
        System.out.println(c2.getName());
        System.out.println(c3.getName());

        Tester[] newTesters = (Tester[]) Array.newInstance(c1, 10);
        Tester newTester = (Tester) c1.newInstance();
        System.out.println(newTesters.length);
    }
}

class Tester {
    private String name;
    private String mem;
}
