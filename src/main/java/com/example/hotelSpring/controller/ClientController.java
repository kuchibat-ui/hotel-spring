package com.example.hotelSpring.controller;

import com.example.hotelSpring.entity.Client;
import com.example.hotelSpring.repository.ClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientController {

    private ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/add-client")                          // юзер заходит на add-client (GET) и видит форму
    public String showAddClient(Model model) {
        model.addAttribute("client", new Client());   // передает пустой Client в модель
        return "add-client";
    }

    @PostMapping("/add-client")
    public String addClient(@ModelAttribute Client client) {   //@MA--аннотация связывает данные из HTTP-запроса с объектом
                                                               //автоматически сам вызывает Сеттеры и устанавливает значения
        clientRepository.save(client);                       //сохранить в БД
        return "redirect:/clients";                          // ПОКАЗАТЬ список клиентов
    }

    @GetMapping("/clients")
    public String showClients(Model model) {           //Model -контейнер для данных которые нужно передать из контейнера в html
        List<Client> clientList = clientRepository.findAll();   //получили список всех клиентов
        model.addAttribute("clientList", clientList);  //положили в модель(имя, значение)
        return "client-list";                                   //вернули имя шаблона. client-list.html получает доступ
    }                                                           // по имени clientList

    @GetMapping("/delete-client/{id}")
    public String deleteClients(@PathVariable Long id) {
        clientRepository.deleteById(id);
        return "redirect:/clients";
    }

    @GetMapping("/edit-client/{id}")
    public String updateRoom(@PathVariable Long id, Model model){    // model- создает новую страницу
        Client client = clientRepository.findById(id).orElseThrow();      // возвращается Optional, может не быть , потому исключение
        model.addAttribute("client", client);         // присваеваем имя модели и передаем туда room
        return "edit-client";                                          // edit-room страница
    }
    @PostMapping("/edit-client/{id}")
    public String updateRoom(@PathVariable Long id, @ModelAttribute Client updatedClient){
        Client client = clientRepository.findById(id).orElseThrow();
        client.setSurname(updatedClient.getSurname());
        client.setFirstname(updatedClient.getFirstname());
        client.setPhone(updatedClient.getPhone());
        client.setEmail(updatedClient.getEmail());
        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/search-surname")
    public String listFindedClients(@RequestParam(name = "surname",required = false) String surname,Model model){

        if (surname != null && !surname.trim().isEmpty()){
           List<Client> findedClients = clientRepository.findBySurname(surname);
            model.addAttribute("surname",surname);
            model.addAttribute("findedClients",findedClients);
        } else {
            model.addAttribute("findedClients",new ArrayList<>());
        }
       return "search-surname";
    }

}
