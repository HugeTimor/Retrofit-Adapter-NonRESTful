package me.zeyuan.adapter_nonrestful;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;


/**
 * A Retrofit call adapter to adapt returnType from Observable<T> to Observable<ResponseWrapper<T>>>.
 * <p>
 * note: This must be applied after RxJavaCallAdapter.
 */
public class NonRESTfulAdapterFactory extends CallAdapter.Factory {
    private Class wrapper;

    public NonRESTfulAdapterFactory() {
        this.wrapper = DefaultResponseWrapper.class;
    }

    public NonRESTfulAdapterFactory(Class wrapper) {
        if (!Utils.isImplementedInterface(wrapper, ResponseWrapper.class)) {
            throw new IllegalArgumentException("wrapper should implemented ResponseWrapper.class");
        }
        this.wrapper = wrapper;
    }

    @Override
    public CallAdapter<?, ?> get(final Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != Observable.class) {
            return null; // Ignore non-Observable types.
        }
        Class annotatedWrapper = Utils.getAnnotatedWrapper(annotations);
        if (annotatedWrapper == null) {
            return null; // Ignore no non-RESTful method.
        }
        if (!Utils.isImplementedInterface(annotatedWrapper, ResponseWrapper.class)) {
            throw new IllegalArgumentException("@NonRESTful markup class should implemented ResponseWrapper.class");
        }
        if (!DefaultResponseWrapper.class.equals(annotatedWrapper)) {
            wrapper = annotatedWrapper;
        }

        final Type actualReturnType = Utils.buildWrapperType(returnType, wrapper);
        //noinspection unchecked returnType checked above to be Observable.
        final CallAdapter<Object, Observable<ResponseWrapper<?>>> delegate =
                (CallAdapter<Object, Observable<ResponseWrapper<?>>>) retrofit.nextCallAdapter(this,
                        actualReturnType, annotations);

        return new CallAdapter<Object, Object>() {
            @Override
            public Object adapt(Call<Object> call) {
                Observable<ResponseWrapper<?>> observable = delegate.adapt(call);
                return observable.map(new Func1<ResponseWrapper<?>, Object>() {
                    @Override
                    public Object call(ResponseWrapper<?> response) {
                        return pick(response);
                    }
                });
            }

            @Override
            public Type responseType() {
                return delegate.responseType();
            }
        };
    }

    private Object pick(ResponseWrapper<?> response) {
        if (response.isOk()) {
            return response.getData();
        } else {
            throw new BusinessException(response.getCode(), response.getMessage());
        }
    }
}
