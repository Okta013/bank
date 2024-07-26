package ru.anikeeva.bank.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.anikeeva.bank.entity.Client;
import ru.anikeeva.bank.repository.ClientRepository;
import ru.anikeeva.bank.utils.MappingUtils;

import java.util.List;

@Service
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;
    private final MappingUtils mappingUtils;

    public ClientService(ClientRepository clientRepository, MappingUtils mappingUtils) {
        this.clientRepository = clientRepository;
        this.mappingUtils = mappingUtils;
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client updateClient(Long id, Client updatedClient) {
        Client client = getClientById(id);
        client.setName(updatedClient.getName());
        client.setPhoneNumber(updatedClient.getPhoneNumber());
        client.setBalance(updatedClient.getBalance());
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}