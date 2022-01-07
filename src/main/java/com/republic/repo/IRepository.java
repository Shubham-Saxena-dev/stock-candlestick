package com.republic.repo;


import com.republic.event.Type;

public interface IRepository<T> {

    void storeInMemory(Type operation, T entity);

}
