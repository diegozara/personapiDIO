package one.digital.innovation.personapi.service;

import one.digital.innovation.personapi.dto.MessageResponseDTO;
import one.digital.innovation.personapi.dto.PersonDTO;
import one.digital.innovation.personapi.entity.Address;
import one.digital.innovation.personapi.entity.Person;
import one.digital.innovation.personapi.exception.PersonNotFoundException;
import one.digital.innovation.personapi.mapper.PersonMapper;
import one.digital.innovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    private PersonService personService;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO) {

        Address address = createAddress(personDTO.getZipCode());

        Person personToSave = personMapper.toModel(personDTO);

        personToSave.setAddress(address);

        Person savedPerson = personRepository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "Created Person with id: ");
    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findById(Integer id) throws PersonNotFoundException {
        Person person = verifyIfExists(id);
        return personMapper.toDTO(person);
    }

    public void delete(Integer id) throws PersonNotFoundException {

        verifyIfExists(id);

        personRepository.deleteById(id);
    }

    public MessageResponseDTO updateById(Integer id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);

        Address address = createAddress(personDTO.getZipCode());

        Person personToUpdate = personMapper.toModel(personDTO);

        personToUpdate.setAddress(address);

        Person updatedPerson = personRepository.save(personToUpdate);
        return createMessageResponse(updatedPerson.getId(), "Updated Person with id: ");
    }

    private Person verifyIfExists(Integer id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createMessageResponse(Integer id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }

    private Address createAddress(String zipCode) {

        String url = "https://viacep.com.br/ws/"+zipCode+"/json/";
        RestTemplate restTemplate = new RestTemplate();
        Address address = restTemplate.getForObject(url, Address.class);
        return address;
    }
}
