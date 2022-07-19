package service;

import dao.CategoryDao;
import model.Category;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CrudCategory {
    @Autowired
    CategoryDao categoryDao;

    public List<Category> getAll() {
        return categoryDao.getList();
    }

    public void save(Category category) {
        categoryDao.save(category);
    }

    public void edit(Category category) {
        categoryDao.edit(category);
    }

    public Category findById(int id) {
        return categoryDao.findById(id);

    }

    public void delete(int id) {
        categoryDao.delete(id);
    }
}
