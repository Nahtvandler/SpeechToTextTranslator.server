package spring.hibernate.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.DTOs.UserHTTPPojo;
import spring.hibernate.entity.Client;
import spring.hibernate.repository.ClientRepository;

import java.util.List;

@Component
@Transactional
public class ClientDao {

    @Autowired
    private ClientRepository clientRepository;

    public ClientDao() {
    }

    public ClientDao(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client save(UserHTTPPojo userDto) throws Exception {
        Client client = new Client();
        client.setFirstName(userDto.getFirstName());
        client.setMiddleName(userDto.getMiddleName());
        client.setLastName(userDto.getLastName());
        client.setLogin(userDto.getLogin());
        client.setPassword(userDto.getPassword());

        return clientRepository.save(client);
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Client getClientByLogin(String login) throws Exception {
        return clientRepository.findByLogin(login);
    }

    public Client getClientByLoginAndPassword(String login, String password) throws Exception {
        return clientRepository.findByLoginAndPassword(login, password);
    }
}
