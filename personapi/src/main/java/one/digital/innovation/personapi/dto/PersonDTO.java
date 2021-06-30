package one.digital.innovation.personapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private Integer id;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String firstName;

    @Size(min = 2, max = 100)
    private String lastName;

    @NotEmpty
    @CPF
    private String cpf;

    @Size(min = 2, max = 8)
    private String zipCode;

    private String birthDate;

    private AddressDTO address;

    @Valid
    @NotEmpty
    private List<PhoneDTO> phones;
}
