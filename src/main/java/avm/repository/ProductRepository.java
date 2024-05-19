package avm.repository;

import avm.products.BaseProduct;

import java.util.Collection;

public interface ProductRepository <T extends BaseProduct> {
    Collection<T> findAll();
    void save(T product);
    T findById(Integer id);
    boolean delete(int id);
}
