package com.example.hotelSpring.controller;

import com.example.hotelSpring.entity.Client;
import com.example.hotelSpring.repository.ClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ClientController {

    private ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/addClient")
    public String showAddClient(Model model) {
        model.addAttribute("client", new Client());
        return "addClient";
    }

    @PostMapping("/addClient")
    public String addClient(@ModelAttribute Client client) {
        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/clients")
    public String showClients(Model model) {
        List<Client> clientList = clientRepository.findAll();
        model.addAttribute("clientList", clientList);
        return "client-list";
    }

    @GetMapping("/delete-client/{id}")
    public String deleteClients(@PathVariable Long id) {
        clientRepository.deleteById(id);
        return "redirect:/clients";
    }

}
