package com.id.misc.hacks;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FinalStaticFieldChange {
    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);//remove "final" modifier!

        field.set(null, newValue);
    }

    public static void main(String args[]) throws Exception {
        setFinalStatic(Boolean.class.getField("FALSE"), true);
        System.err.print(Boolean.FALSE);//true.
    }
}
