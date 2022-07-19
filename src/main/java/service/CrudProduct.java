package service;

import dao.ProductDao;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class CrudProduct {
    @Autowired
    ProductDao productDao;

    public List<Product> getAll() {
        return productDao.getList();
    }

    public void save(Product product) {
        productDao.save(product);
    }

    public void edit(Product product) {
        productDao.edit(product);
    }

    public Product findById(int id) {
        return productDao.findById(id);

    }

    public void delete(int id) {
        productDao.delete(id);
    }
}
