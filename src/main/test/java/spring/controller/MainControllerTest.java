package spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import spring.DTOs.LoginPair;
import spring.DTOs.RoomHttpPojo;
import spring.DTOs.UserHTTPPojo;
import spring.LoginStatusEnum;
import spring.hibernate.Dao.ClientDao;
import spring.hibernate.Dao.RoomDao;
import spring.hibernate.entity.Client;
import spring.hibernate.entity.Room;
import spring.service.ClientService;
import spring.service.RoomService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MainControllerTest {

    @InjectMocks
    MainController subject;

    @Mock
    ClientDao clientDao;

    @Mock
    RoomDao roomDao;

    @BeforeEach
    public void setUp() throws Exception {
        subject = new MainController(new ClientService(clientDao), new RoomService(roomDao, clientDao));
    }

    @Test
    void hello() {
        assertThat(subject.index(), is("HI THERE"));
    }

    @Test
    public void registerUserCorrect() throws Exception {
        UserHTTPPojo userHTTPPojo = new UserHTTPPojo();
        userHTTPPojo.setLogin("user1");
        given(clientDao.save(userHTTPPojo)).willReturn(new Client());

        String result = subject.registerUser(userHTTPPojo);
        assertThat(result, is(HttpStatus.OK.toString()));
    }

    @Test
    void registerUserIncorrect() throws  Exception {
        UserHTTPPojo userHTTPPojo = new UserHTTPPojo();
        userHTTPPojo.setLogin("user2");
        given(clientDao.save(userHTTPPojo)).willThrow(new Exception("get out of here smerd"));

        String result = subject.registerUser(userHTTPPojo);
        assertThat(result, is(HttpStatus.INTERNAL_SERVER_ERROR.toString()));
    }

    @Test
    void loginByNameAndPassword() throws Exception {
        String login = "admin";
        String password = "1234";

        Client client = new Client();
        client.setLogin(login);
        client.setPassword(password);

        given(clientDao.getClientByLoginAndPassword(login, password)).willReturn(client);

        LoginPair result = subject.doLogin(login, password);

        UserHTTPPojo expectedPojo = new UserHTTPPojo();
        expectedPojo.setLogin(login);
        expectedPojo.setPassword(password);

        LoginPair expectedPair = new LoginPair();
        expectedPair.setStatus(LoginStatusEnum.SUCCES.getStatus());
        expectedPair.setClient(expectedPojo);

        assertEquals(expectedPair, result);
    }

    @Test
    void badLogin() throws Exception {
        LoginPair result = subject.doLogin("user1", "password");

        LoginPair expectedPair = new LoginPair();
        expectedPair.setStatus(LoginStatusEnum.CLIENT_NOT_FOUND.getStatus());

        assertEquals(expectedPair, result);
    }

    @Test
    void badPassword() throws Exception {
        String login = "admin";
        String password = "1234";

        Client client = new Client();
        client.setLogin(login);
        client.setPassword(password);

        given(clientDao.getClientByLogin(login)).willReturn(client);

        LoginPair result = subject.doLogin(login, "otherPassword");

        LoginPair expectedPair = new LoginPair();
        expectedPair.setStatus(LoginStatusEnum.INCORRECT_PASSWORD.getStatus());

        assertEquals(expectedPair, result);
    }

    @Test
    void createRoom() throws Exception {
        Room room = new Room();
        room.setName("room");
        room.setId(1);

        given(roomDao.save(any())).willReturn(room);

        RoomHttpPojo result = subject.createRoom(createRoomHttpPojo());

        RoomHttpPojo expectedRoomPojo = createRoomHttpPojo();
        expectedRoomPojo.setId("1");

        assertEquals(expectedRoomPojo, result);
    }

    private RoomHttpPojo createRoomHttpPojo() {
        RoomHttpPojo roomHttpPojo = new RoomHttpPojo();
        roomHttpPojo.setName("room");
        roomHttpPojo.setOwner(new UserHTTPPojo());

        return roomHttpPojo;
    }

    @Test
    void findRoom() {
        Room room = new Room();
        room.setName("room");
        room.setId(1);

        given(roomDao.getByRoomId(any())).willReturn(room);

        RoomHttpPojo result = subject.connectToRoom(1);
        RoomHttpPojo expectedRoom = createRoomHttpPojo();
        expectedRoom.setId("1");
        expectedRoom.setOwner(null);

        assertEquals(expectedRoom, result);
    }

    @Test
    void сouldntFindroom() {
        RoomHttpPojo result = subject.connectToRoom(1);

        assertEquals(null, result);
    }
}
