package com.example.hotelSpring.controller;

import com.example.hotelSpring.entity.Booking;
import com.example.hotelSpring.entity.Client;
import com.example.hotelSpring.entity.Room;
import com.example.hotelSpring.repository.BookingRepository;
import com.example.hotelSpring.repository.ClientRepository;
import com.example.hotelSpring.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BookingController {
    private BookingRepository bookingRepository;
    private ClientRepository clientRepository;
    private RoomRepository roomRepository;

    public BookingController(BookingRepository bookingRepository, ClientRepository clientRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.clientRepository = clientRepository;
        this.roomRepository = roomRepository;
    }

    @GetMapping("/bookings")
    public String showBookings(Model model) {
        List<Booking> bookingList = bookingRepository.findAll();
        model.addAttribute("bookingList", bookingList);
        return "booking-list";
    }

    @GetMapping("/add-booking")
    public String showAddBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
        return "add-booking";
    }

    @PostMapping("add-booking")
    public String addBooking(@ModelAttribute Booking booking, @RequestParam Long clientId, @RequestParam Long roomId) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        Room room = roomRepository.findById(roomId).orElseThrow();
        booking.setClient(client);
        booking.setRoom(room);
        booking.setBookingDate(LocalDate.now());
        booking.calculateTotalPrice();
        room.setStatus("Забронировано");
        roomRepository.save(room);
        bookingRepository.save(booking);
        return "redirect:/bookings";
    }

    @GetMapping("/edit-booking/{id}")
    public String updateBooking(@PathVariable Long id, Model model) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        model.addAttribute("booking", booking);
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
        return "edit-booking";
    }

    @PostMapping("/edit-booking/{id}")
    public String updateBooking(@PathVariable Long id, @ModelAttribute Booking updatedBooking, @RequestParam Long clientId, @RequestParam Long roomId) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        Room room = roomRepository.findById(roomId).orElseThrow();
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setClient(client);
        booking.setRoom(room);
        booking.setCheckInDate(updatedBooking.getCheckInDate());
        booking.setCheckOutDate(updatedBooking.getCheckOutDate());
        booking.calculateTotalPrice();
        booking.setGuestsCount(updatedBooking.getGuestsCount());
        booking.setStatus(updatedBooking.getStatus());
        bookingRepository.save(booking);
        return  "redirect;/bookings";
    }

    @GetMapping("/delete-booking/{id}")
    public String deleteBooking(@PathVariable Long id){
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setStatus("Отменено");
        Room room = booking.getRoom();
        room.setStatus("Свободна");
        roomRepository.save(room);
        bookingRepository.save(booking);
        return  "redirect;/bookings";
    }
}
