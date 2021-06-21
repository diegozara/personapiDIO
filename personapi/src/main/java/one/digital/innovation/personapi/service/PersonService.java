package one.digital.innovation.personapi.service;

import one.digital.innovation.personapi.dto.MessageResponseDTO;
import one.digital.innovation.personapi.dto.PersonDTO;
import one.digital.innovation.personapi.entity.Person;
import one.digital.innovation.personapi.exception.PersonNotFoundException;
import one.digital.innovation.personapi.mapper.PersonMapper;
import one.digital.innovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;
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

    public MessageResponseDTO createPerson(PersonDTO personDTO){

        Person personToSave = personMapper.toModel(personDTO);

        Person savedPerson = personRepository.save(personToSave);
        return MessageResponseDTO
                .builder()
                .message("Created Person with id: " + savedPerson.getId())
                .build();
    }

    public List<PersonDTO> listAll (){
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
    private Person verifyIfExists (Integer id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(()-> new PersonNotFoundException(id));
    }


}
