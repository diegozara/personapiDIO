package one.digital.innovation.personapi.utils;

import one.digital.innovation.personapi.dto.PersonDTO;
import one.digital.innovation.personapi.entity.Person;

import java.time.LocalDate;
import java.util.Collections;

public class PersonUtils {

    private static final String FIRST_NAME = "Diego";
    private static final String LAST_NAME = "Zaratini";
    private static final String CPF_NUMBER = "804.811.120-90";
    private static final Integer PERSON_ID = 1;
    public static final LocalDate BIRTH_DATE = LocalDate.of(1990, 04, 12);

    public static PersonDTO createFakeDTO() {
        return PersonDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .birthDate("12-04-1990")
                .phones(Collections.singletonList(PhoneUtils.createFakeDTO()))
                .build();
    }

    public static Person createFakeEntity() {
        return Person.builder()
                .id(PERSON_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .birthDate(BIRTH_DATE)
                .phones(Collections.singletonList(PhoneUtils.createFakeEntity()))
                .build();
    }

}
