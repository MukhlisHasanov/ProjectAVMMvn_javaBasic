package avm.repository;

import avm.products.BaseProduct;
import avm.products.MarketProduct;

import java.util.Collection;

public interface ProductRepository <T extends BaseProduct> {

    Collection<T> findAll();
    void save(T product);
    T findById(Integer id);

//    MarketProduct findById(Integer id);

//    MarketProduct findById(Integer id);

    void remove(int id);
}
