package dao;

import model.Product;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProductDao {
    @Autowired
    EntityManager entityManager;

    public List<Product> getList(){
        String queryStr = "SELECT p FROM Product p";
        TypedQuery<Product> query = entityManager.createQuery(queryStr, Product.class);
        return (List<Product>) query.getResultList();
    }

    public void save(Product product){
        EntityTransaction txn = entityManager.getTransaction();
        txn.begin();
        entityManager.persist(product);
        txn.commit();
    }

    public void edit(Product product) {
        EntityTransaction txn = entityManager.getTransaction();
        txn.begin();
        entityManager.merge(product);
        txn.commit();
    }

    public void delete(int id){
        EntityTransaction txn = entityManager.getTransaction();
        txn.begin();
        entityManager.remove(findById(id));
        txn.commit();
    }


    public Product findById(int id){
        String query = "SELECT p FROM Product p where p.id=:id";
        Product product = entityManager.createQuery(query,Product.class).setParameter("id",id).getSingleResult();
        return product;
    }

}
