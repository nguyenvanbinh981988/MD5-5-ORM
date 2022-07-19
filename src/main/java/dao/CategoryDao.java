package dao;

import model.Category;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoryDao {
    @Autowired
    EntityManager entityManager;

    public List<Category> getList(){
        String queryStr = "SELECT c FROM Category c";
        TypedQuery<Category> query = entityManager.createQuery(queryStr, Category.class);
        return (List<Category>) query.getResultList();
    }

    public void save(Category category){
        EntityTransaction txn = entityManager.getTransaction();
        txn.begin();
        entityManager.persist(category);
        txn.commit();
    }

    public void edit(Category category) {
        EntityTransaction txn = entityManager.getTransaction();
        txn.begin();
        entityManager.merge(category);
        txn.commit();
    }

    public void delete(int id){
        EntityTransaction txn = entityManager.getTransaction();
        txn.begin();
        entityManager.remove(findById(id));
        txn.commit();
    }


    public Category findById(int id){
        String query = "SELECT c FROM Category c where c.id=:id";
        Category category = entityManager.createQuery(query,Category.class).setParameter("id",id).getSingleResult();
        return category;
    }

}
