package kg.alatoo.demooauth.repo;


import kg.alatoo.demooauth.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

    void deleteById(Long id);

    Optional<Products> findById(Long id);
    List<Products> findByOrderByNameAsc();
    List<Products> findByOrderByNameDesc();
    List<Products> findByOrderByPrizeAsc();
    List<Products> findByOrderByPrizeDesc();
    List<Products> findByOrderByIdAsc();
    List<Products> findByOrderByIdDesc();
}
