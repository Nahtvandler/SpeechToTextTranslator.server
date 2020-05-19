package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import spring.DTOs.LoginPair;
import spring.DTOs.UserHTTPPojo;
import spring.hibernate.Dao.ClientDao;
import spring.hibernate.entity.Client;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ClientService {

    @Autowired
    public ClientDao clientDao;

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public boolean registerUser(UserHTTPPojo userRequest) {
        Client client;
        try {
            client = clientDao.save(userRequest);
            return client != null ? true : false;
        } catch (Exception e) {
            // TODO Logging
            e.printStackTrace();
            return false;
        }
    }

    public String getAllClients() {
        List<Client> users = clientDao.getAll();
        return users.toString();
    }

    public LoginPair doLogin(String login, String password) {
        LoginPair loginPair = new LoginPair();

        Client client = null;
        try {
            client = clientDao.getClientByLoginAndPassword(login, password);

            if (client == null) {
                client = clientDao.getClientByLogin(login);

                loginPair.setStatus(client != null ? "3" : "2");
                return loginPair;
            } else {
                loginPair.setStatus("1");

                UserHTTPPojo userHTTPPojo = new UserHTTPPojo();
                userHTTPPojo.setLogin(client.getLogin());
                userHTTPPojo.setFirstName(client.getFirstName());
                userHTTPPojo.setMiddleName(client.getMiddleName());
                userHTTPPojo.setLastName(client.getLastName());
                userHTTPPojo.setPassword(client.getPassword());

                loginPair.setClient(userHTTPPojo);

                return loginPair;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return loginPair;
        }
    }


}
