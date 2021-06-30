package one.digital.innovation.personapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Integer id;

    @NotEmpty
    @Size(min = 8, max = 8)
    private String cep;

    private String logradouro;

    private String bairro;

    private String localidade;

    private String uf;
}
