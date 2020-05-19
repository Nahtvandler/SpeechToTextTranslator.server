package spring.hibernate.repository;

import org.springframework.data.repository.CrudRepository;
import spring.hibernate.entity.Client;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Integer> {

    public Client save(Client user);

    public List<Client> findAll();

    Client findByLogin(@Param("login") String login);

    Client findByLoginAndPassword(@Param("login") String login, @Param("password") String password);
}
