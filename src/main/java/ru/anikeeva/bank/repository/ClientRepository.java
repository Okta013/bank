package ru.anikeeva.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anikeeva.bank.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
