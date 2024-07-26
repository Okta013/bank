package ru.anikeeva.bank.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.anikeeva.bank.dto.ClientDTO;
import ru.anikeeva.bank.dto.PaymentDTO;
import ru.anikeeva.bank.entity.Client;
import ru.anikeeva.bank.repository.ClientRepository;
import ru.anikeeva.bank.utils.MappingUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;
    private final MappingUtils mappingUtils;

    public ClientService(ClientRepository clientRepository, MappingUtils mappingUtils) {
        this.clientRepository = clientRepository;
        this.mappingUtils = mappingUtils;
    }

    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = mappingUtils.mapToClientEntity(clientDTO);
        client = clientRepository.save(client);
        return mappingUtils.mapToClientDto(client);
    }

    public ClientDTO getClientById(Long id) {
        return mappingUtils.mapToClientDto(clientRepository.findById(id).orElse(new Client()));
    }

    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream().map(mappingUtils::mapToClientDto).collect(Collectors.toList());
    }

    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        client.setName(clientDTO.getName());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setBalance(clientDTO.getBalance());
        clientRepository.save(client);
        return mappingUtils.mapToClientDto(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

//    public List<PaymentDTO> getOutgoingPayments(Long id) {
//
//    }
}