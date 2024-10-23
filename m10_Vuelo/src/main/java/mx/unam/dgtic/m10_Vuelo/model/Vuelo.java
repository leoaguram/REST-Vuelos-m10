package mx.unam.dgtic.m10_Vuelo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vuelo {
    private Integer id;
    private String origen;
    private String destino;
    private LocalDateTime fechaHora;
    private String codigoVuelo;
}
