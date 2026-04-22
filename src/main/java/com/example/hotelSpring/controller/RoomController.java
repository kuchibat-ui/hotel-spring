package com.example.hotelSpring.controller;

import com.example.hotelSpring.entity.Room;
import com.example.hotelSpring.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RoomController {
    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/")
    public String mainPage(){
        return "index";
    }
    @GetMapping("/addRoom")
    public String showAddRoom(Model model){
        model.addAttribute("room", new Room());
        return "addRoom";

    }
    @PostMapping("/addRoom")
    public String addRoom(@ModelAttribute Room room){
        room.setStatus("Свободен");
        roomRepository.save(room);
        return "redirect:/rooms";
    }
    @GetMapping("/rooms")
    public String showRooms(Model model){                               // model -- это модель страницы
        List<Room> roomList = roomRepository.findAll();                 // получаем List всех комнат через repository-метод
        model.addAttribute("roomList", roomList);         // присваиваем имя модели и передаем ему roomList
        return "room-list";                                            // возвращаем модель и нужно создать room-list.html
    }
    @GetMapping("/delete-room/{id}")
    public String deleteRoom(@PathVariable Long id){                  // @PathVariable--получение значения в переменную
        roomRepository.deleteById(id);
        return "redirect:/rooms";                                    // возвращает на главную страницу
    }
    @GetMapping("/edit/{id}")
    public String updateRoom(@PathVariable Long id, Model model){    // model- создает новую страницу
        Room room = roomRepository.findById(id).orElseThrow();      // возвращается Optional, может не быть , потому исключение
        model.addAttribute("room", room);         // присваеваем имя модели и передаем туда room
        return "edit-room";                                          // edit-room страница
    }
    @PostMapping("/edit/{id}")
    public String updateRoom(@PathVariable Long id, @ModelAttribute Room updatedRoom){
        Room room = roomRepository.findById(id).orElseThrow();
        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setType(updatedRoom.getType());
        room.setStatus(updatedRoom.getStatus());
        room.setPricePerNight(updatedRoom.getPricePerNight());
        roomRepository.save(room);
        return "redirect:/rooms";
    }
}