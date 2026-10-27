package edu.tor.quarkusswing.service;

import edu.tor.quarkusswing.entity.Person;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import java.util.List;
@Singleton
public class PersonService implements PanacheRepository<Person> {
    @Transactional
    public void create(String name, String email) {
        Person person = new Person();
        person.name = name;
        person.email = email;
        persist(person);
    }

    @Transactional
    public void initTestData() {
        if (count() == 0) {
            create("Иван Иванов", "ivan@example.com");
            create("Пётр Петров", "petr@example.com");
            create("Мария Сидорова", "maria@example.com");
        }
    }

    public boolean isExistByName(String name) {
        return count("name", name) > 0;
    }
}
