package kg.alatoo.demooauth.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name, description,weight, prize, time;


}
