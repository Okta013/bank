package ru.anikeeva.bank.service;

import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private MappingUtils mappingUtils;

    @BeforeEach
    public void setUp() {
        when(mappingUtils.mapToClientEntity(any(ClientDTO.class))).thenReturn(new Client());
        when(mappingUtils.mapToClientDto(any(Client.class))).thenReturn(new ClientDTO());
    }

    @Test
    void createClient_withValidClient_savesClient() {

        ClientDTO clientDTO = new ClientDTO(1L, "Konstantin Frolov", "+79173456789", new BigDecimal("10345.67"));
        Client client = new Client();

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        ClientDTO result = clientService.createClient(clientDTO);

        verify(mappingUtils, times(1)).mapToClientEntity(clientDTO);
        verify(clientRepository, times(1)).save(client);
        verify(mappingUtils, times(1)).mapToClientDto(client);

        assertEquals(new ClientDTO(), result);
    }

}
