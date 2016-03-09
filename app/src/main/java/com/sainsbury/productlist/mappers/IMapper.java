package com.sainsbury.productlist.mappers;

import java.util.Collection;
/*
Interface for mapping from different data formats (DTO<->Domain<->Model)
 */
public interface IMapper<A,B> {
    Collection<B> mapUp(Collection<A> collection);
    B mapUp(A a);
    Collection<A> mapDown(Collection<B> collection);
    A mapDown(B a);
}
