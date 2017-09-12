package me.zeyuan.adapter_nonrestful;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static com.google.gson.internal.$Gson$Types.typeToString;


public class WrapperType implements ParameterizedType {
    private Type[] actualTypeArgs;
    private Class wrapper;

    public WrapperType(Type[] actualTypeArgs, Class wrapper) {
        this.actualTypeArgs = actualTypeArgs;
        this.wrapper = wrapper;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return actualTypeArgs;
    }

    @Override
    public Type getRawType() {
        return wrapper;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

    @Override
    public String toString() {
        Type[] typeArguments = getActualTypeArguments();
        Type rawType = getRawType();
        if (typeArguments.length == 0) return typeToString(rawType);
        StringBuilder result = new StringBuilder(30 * (typeArguments.length + 1));
        result.append(typeToString(rawType));
        result.append("<").append(typeToString(typeArguments[0]));
        for (int i = 1; i < typeArguments.length; i++) {
            result.append(", ").append(typeToString(typeArguments[i]));
        }
        return result.append(">").toString();
    }
}
