package com.example.hotelSpring.repository;

import com.example.hotelSpring.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    List<Room> findByRoomNumber(String roomNumber);
}
