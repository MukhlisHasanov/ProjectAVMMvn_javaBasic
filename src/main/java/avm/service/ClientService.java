package avm.service;

import avm.products.Client;
import avm.repository.ClientRepository;

public class ClientService {
    private ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public void add(String name, int age, float wallet) {
        Client client = new Client(null, name, age, wallet);
        repository.save(client);
    }
    public boolean update(int id, String name, int age) {
        Client updClient = repository.findById(id);
        if (updClient != null) {
            updClient.update(name, age);
            repository.save(updClient);
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        Client delClient = repository.findById(id);
        if (delClient != null) {
            repository.delete(id);
            return true;
        }
        return false;
    }

    public Client findById(int id) {
        return repository.findById(id);
    }

    public void print() {
        repository.findAll().forEach(System.out::println);
    }
}
