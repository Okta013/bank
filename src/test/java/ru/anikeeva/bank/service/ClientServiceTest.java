package ru.anikeeva.bank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anikeeva.bank.dto.ClientDTO;
import ru.anikeeva.bank.entity.Client;
import ru.anikeeva.bank.repository.ClientRepository;
import ru.anikeeva.bank.utils.MappingUtils;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private MappingUtils mappingUtils;

    @Test
    void createClient_withValidClient_savesClient() {

        ClientDTO clientDTO = new ClientDTO(1L, "Konstantin Frolov",
                "+79173456789", new BigDecimal("10345.67"));
        Client client = new Client(1L, "Konstantin Frolov",
                "+79173456789", new BigDecimal("10345.67"));

        when(mappingUtils.mapToClientEntity(clientDTO)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);
        when(mappingUtils.mapToClientDto(client)).thenReturn(clientDTO);

        ClientDTO createdClientDTO = clientService.createClient(clientDTO);

        Assertions.assertNotNull(createdClientDTO);
        Assertions.assertEquals(clientDTO.getId(), createdClientDTO.getId());
        Assertions.assertEquals(clientDTO.getName(), createdClientDTO.getName());
        Assertions.assertEquals(clientDTO.getPhoneNumber(), createdClientDTO.getPhoneNumber());
        Assertions.assertEquals(clientDTO.getBalance(), createdClientDTO.getBalance());

        verify(mappingUtils, times(1)).mapToClientEntity(clientDTO);
        verify(clientRepository, times(1)).save(client);
        verify(mappingUtils, times(1)).mapToClientDto(client);
    }

//    @Test
//    void getAllClients_withValidClients_GetClientsList(){
//        ClientDTO clientDTOOne = new ClientDTO(1L, "Konstantin Frolov",
//                "+79173456789", new BigDecimal("10345.67"));
//        ClientDTO clientDTOTwo = new ClientDTO(2L, "Alexey Golovin",
//                "+79876345678", new BigDecimal("23476.03"));
//        ClientDTO clientDTOThree = new ClientDTO(3L, "Anna Popova",
//                "+79378564321", new BigDecimal("74536.21"));
//        List<ClientDTO> clientsDTO = new ArrayList<>();
//        clientsDTO.add(clientDTOOne);
//        clientsDTO.add(clientDTOTwo);
//        clientsDTO.add(clientDTOThree);
//        List<Client> clients = new ArrayList<>();
//
//        when(clientRepository.findAll()).thenReturn(clients);
//
//        List<ClientDTO> result = clientService.getAllClients();
//
//        verify(clientRepository, times(1)).findAll();
//        assertEquals(3,result.size());
//        assertEquals(clientsDTO, result);
//    }

}
