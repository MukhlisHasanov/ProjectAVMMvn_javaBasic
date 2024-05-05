package avm.repository;

import avm.products.BaseProduct;

public interface ProductRepository <T extends BaseProduct>{
    void put(T product);
    T get(int id);
    void remove(int id);
}
