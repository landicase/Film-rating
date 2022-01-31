package com.epam.rating.context;

import com.epam.rating.entity.Identifiable;
import com.epam.rating.exception.UnknownEntityException;

import java.util.Collection;

public interface ApplicationContext {
    <T extends Identifiable> Collection<T> retrieveBaseEntityList(Class<T> tClass);

    void init() throws UnknownEntityException;
}
