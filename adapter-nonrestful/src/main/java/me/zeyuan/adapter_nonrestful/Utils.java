package me.zeyuan.adapter_nonrestful;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import rx.Observable;

class Utils {
    /**
     * Returns true if {@code annotations} contains an instance of {@code cls}.
     */
    public static boolean isAnnotationPresent(Annotation[] annotations,
                                              Class<? extends Annotation> cls) {
        for (Annotation annotation : annotations) {
            if (cls.isInstance(annotation)) {
                return true;
            }
        }
        return false;
    }

    static Class getAnnotatedWrapper(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (NonRESTful.class.isInstance(annotation)) {
                return ((NonRESTful) annotation).value();
            }
        }
        return null;
    }

    static boolean isImplementedInterface(Class clazz, Class reference) {
        if (!reference.isInterface()) {
            throw new IllegalArgumentException("Reference must be a interface");
        }
        for (Class clz : clazz.getInterfaces()) {
            if (reference.equals(clz)) {
                return true;
            }
        }
        return false;
    }

    static ParameterizedType buildWrapperType(final Type type, final Class wrapper) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                Type[] args = ((ParameterizedType) (type)).getActualTypeArguments();
                ResponseWrapperType baseType = new ResponseWrapperType(args, wrapper);
                return new Type[]{baseType};
            }

            @Override
            public Type getRawType() {
                return Observable.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }
}
