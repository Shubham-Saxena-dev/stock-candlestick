package com.republic.parsers;

import com.republic.event.Type;

public interface IEvent<T> {

    Type getType();

    T getData();

    void type(Type operationType);

    void entity(T entity);
}
