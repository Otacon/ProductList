package com.sainsbury.productlist.mappers;

import java.util.ArrayList;
import java.util.Collection;
/*
abstract class to extend the single object mapping to a collection of the same object. Useful if you
need to map collections of DTO/Domain/Models
 */
public abstract class Mapper<A, B> implements IMapper<A, B> {
    @Override
    public Collection<B> mapUp(Collection<A> collection) {
        if (collection == null) {
            return null;
        }
        Collection<B> mappedCollection = new ArrayList<>();
        for (A a : collection) {
            B mappedItem = mapUp(a);
            if (mappedItem != null) {
                mappedCollection.add(mappedItem);
            }
        }
        return mappedCollection;
    }

    @Override
    public Collection<A> mapDown(Collection<B> collection) {
        if (collection == null) {
            return null;
        }
        Collection<A> mappedCollection = new ArrayList<>();
        for (B b : collection) {
            A mappedItem = mapDown(b);
            if (mappedItem != null) {
                mappedCollection.add(mappedItem);
            }
        }
        return mappedCollection;
    }
}
