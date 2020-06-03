package spring.hibernate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import spring.hibernate.entity.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Integer> {

    Room save(Room user);

    List<Room> findAll();

    Optional<Room> findById(@Param("id") Integer id);

    Room findByName(@Param("name") String roomName);
}
