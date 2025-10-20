package com.springboot.jpa.services;

import com.springboot.jpa.entities.Client;
import com.springboot.jpa.entities.ClientDetails;
import com.springboot.jpa.repositories.ClientDetailRepository;
import com.springboot.jpa.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImp implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return  clientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findByName(String name) {
        return clientRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Optional<Client> findByCcr(String ccr) {
        return clientRepository.findByCcr(ccr);
    }

    @Override
    @Transactional
    public Client save(Client client) {
        client.getDetails().setClient(client);
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public Optional<Client> update(Long id, Client client) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {

            Client clientDB = clientOptional.orElseThrow();
            clientDB.setName(client.getName());
            clientDB.setTel(client.getTel());
            clientDB.setCcr(client.getCcr());

            if (client.getDetails() != null) {
                ClientDetails newDetails = client.getDetails();
                ClientDetails oldDetails = clientDB.getDetails();
                if (oldDetails == null) {
                    newDetails.setClient(clientDB);
                    clientDB.setDetails(newDetails);
                }
                else  {
                    oldDetails.setEmail(newDetails.getEmail());
                    oldDetails.setAddress(newDetails.getAddress());
                    oldDetails.setUpdated(new Date());
                    oldDetails.setClient(clientDB);
                }

            }
            return Optional.of(clientRepository.save(clientDB));

        }

       return clientOptional;
    }

    @Override
    @Transactional
    public Optional<Client> delete(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        clientOptional.ifPresent(client->{
            clientRepository.delete(client);
        });
        return clientOptional;
    }

}
