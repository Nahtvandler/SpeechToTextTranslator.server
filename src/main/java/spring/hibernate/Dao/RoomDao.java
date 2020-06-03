package spring.hibernate.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.DTOs.RoomHttpPojo;
import spring.hibernate.entity.Room;
import spring.hibernate.repository.RoomRepository;

import java.util.Optional;

@Component
@Transactional
public class RoomDao {

    @Autowired
    private RoomRepository roomRepository;

    public RoomDao(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public Room getByRoomId(Integer id) {
        Optional<Room> optional = roomRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }

        return null;
    }

    public Room getByRoomName(String name) {
        return roomRepository.findByName(name);
    }
}
