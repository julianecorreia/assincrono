package br.unipar.desktop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cliente {

    private Long id;

    private String nome;

    private String email;

    private String telefone;

    public Cliente(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public static List<Cliente> unmarshalFromJson(String json)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Cliente> clienteList = mapper.readValue(json,
                new TypeReference<List<Cliente>>(){});
        return clienteList;
    }

    public static String marshalToJson(Cliente cliente)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(cliente);
    }


}
