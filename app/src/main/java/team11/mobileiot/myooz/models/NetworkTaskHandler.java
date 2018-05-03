package team11.mobileiot.myooz.models;

import java.lang.reflect.ParameterizedType;

public abstract class NetworkTaskHandler<T> {
    public String className;
    public NetworkTaskHandler(){
        className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].toString().replace("class ", "");
    }
    public abstract void onReady(T result);
}
