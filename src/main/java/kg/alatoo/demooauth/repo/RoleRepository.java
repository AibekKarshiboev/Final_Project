package kg.alatoo.demooauth.repo;


import kg.alatoo.demooauth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
