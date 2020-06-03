package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.DTOs.RoomHttpPojo;
import spring.DTOs.UserHTTPPojo;
import spring.hibernate.Dao.ClientDao;
import spring.hibernate.Dao.RoomDao;
import spring.hibernate.entity.Client;
import spring.hibernate.entity.Room;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoomService {

    @Autowired
    private RoomDao roomDao;
    @Autowired
    private ClientDao clientDao;

    public RoomService(RoomDao roomDao, ClientDao clientDao) {
        this.roomDao = roomDao;
        this.clientDao = clientDao;
    }

    public RoomHttpPojo createRoom(RoomHttpPojo roomHttpPojo) throws Exception {
        Room room = new Room();
        room.setName(roomHttpPojo.getName());
        room.setDescription(roomHttpPojo.getDescription());

        Client client = clientDao.getClientByLogin(roomHttpPojo.getOwner().getLogin());
        room.setOwner(client);

        room = roomDao.save(room);

        RoomHttpPojo savedRoom = roomHttpPojo;
        savedRoom.setId(room.getId().toString());

        return savedRoom;
    }

    public RoomHttpPojo findRoomById(Integer id) {
        Room room = roomDao.getByRoomId(id);
        if (room == null ) {
            // TODO logging
            return null;
        }

        RoomHttpPojo roomHttpPojo = new RoomHttpPojo();
        roomHttpPojo.setId(room.getId().toString());
        roomHttpPojo.setName(room.getName());
        roomHttpPojo.setDescription(room.getDescription());

//        Client client = room.getOwner();
//        UserHTTPPojo userHTTPPojo = new UserHTTPPojo();
//        userHTTPPojo.setLogin(client.getLogin());
//        userHTTPPojo.setFirstName(client.getFirstName());
//        userHTTPPojo.setMiddleName(client.getMiddleName());
//        userHTTPPojo.setLastName(client.getLastName());
//        roomHttpPojo.setOwner(userHTTPPojo);

        return roomHttpPojo;
    }
}
