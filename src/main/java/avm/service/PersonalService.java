package avm.service;

import avm.products.Personal;
import avm.products.PersonalState;
import avm.repository.PersonalRepository;

public class PersonalService {
    private PersonalRepository repository;

    public PersonalService(PersonalRepository repository) {
        this.repository = repository;
    }

    public void add(String name, PersonalState department) {
        Personal personal = new Personal(null, name, department);
        repository.save(personal);
    }
    public boolean update(int id, String name, PersonalState department) {
        Personal updPersonal = repository.findById(id);
        if (updPersonal != null) {
            updPersonal.update(name, department);
            repository.save(updPersonal);
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        Personal delPersonal = repository.findById(id);
        if (delPersonal != null) {
            repository.delete(id);
            return true;
        }
        return false;
    }

    public Personal findById(int id) {
        return repository.findById(id);
    }

    public void print() {
        repository.findAll().forEach(System.out::println);
    }
}
