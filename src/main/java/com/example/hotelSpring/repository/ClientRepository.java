package com.example.hotelSpring.repository;

import com.example.hotelSpring.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
List<Client>findBySurname(String surname);

}
