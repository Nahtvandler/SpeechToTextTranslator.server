package spring.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.DTOs.LoginPair;
import spring.DTOs.UserHTTPPojo;
import spring.LoginStatusEnum;
import spring.hibernate.Dao.ClientDao;
import spring.hibernate.entity.Client;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ClientService {
    private final static Logger logger = LogManager.getLogger(ClientService.class);

    @Autowired
    public ClientDao clientDao;

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public boolean registerUser(UserHTTPPojo userRequest) {
        Client client;
        try {
            client = clientDao.save(userRequest);
            logger.info("Client with login {} registration is successful", userRequest.getLogin());
            return client != null ? true : false;
        } catch (Exception e) {
            logger.error("Client {} registration error", userRequest.getLogin(), e);
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

                LoginStatusEnum status = client != null
                        ? LoginStatusEnum.INCORRECT_PASSWORD
                        : LoginStatusEnum.CLIENT_NOT_FOUND;
                loginPair.setStatus(status.getStatus());

                logger.info("Client {} authorization failed: {}", login, status.name());
                return loginPair;
            } else {
                loginPair.setStatus(LoginStatusEnum.SUCCES.getStatus());

                UserHTTPPojo userHTTPPojo = new UserHTTPPojo();
                userHTTPPojo.setLogin(client.getLogin());
                userHTTPPojo.setFirstName(client.getFirstName());
                userHTTPPojo.setMiddleName(client.getMiddleName());
                userHTTPPojo.setLastName(client.getLastName());
                userHTTPPojo.setPassword(client.getPassword());

                loginPair.setClient(userHTTPPojo);

                logger.info("Client {} authorization success", login);
                return loginPair;
            }
        } catch (Exception e) {
            logger.error("Error authorizing client {}", login, e);
            return loginPair;
        }
    }


}
