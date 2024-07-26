package ru.anikeeva.bank.controller;

import org.springframework.web.bind.annotation.*;
import ru.anikeeva.bank.dto.ClientDTO;
import ru.anikeeva.bank.entity.Client;
import ru.anikeeva.bank.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ClientDTO createClient(@RequestBody ClientDTO clientDTO) {
        return clientService.createClient(clientDTO);
    }

    @GetMapping("/{id}")
    public ClientDTO getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @GetMapping
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClients();
    }

    @PutMapping("/{id}")
    public ClientDTO updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        return clientService.updateClient(id, clientDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }
}