package kg.alatoo.demooauth.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Role {
    @Id
    private String name;
    public Role(String name){
        this.name = name;
    }

    public Role() {

    }
}
