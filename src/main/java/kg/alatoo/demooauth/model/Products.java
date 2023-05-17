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
/*spring.datasource.name=postgres
        spring.datasource.url=jdbc:postgresql://${PROD_DB_HOST}:${PROD_DB_PORT}/${PROD_DB_NAME}
        spring.datasource.username=${PROD_DB_USERNAME}
        spring.datasource.password=${PROD_DB_PASSWORD}
        spring.jpa.hibernate.ddl-auto=update
        spring.sql.init.mode=always*/
