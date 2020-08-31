package cl.acorral.store.product.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Esta clase se utiliza para devolver los mensajes de error que se lanzan cuando el request esta mal
 */

@Getter @Setter @Builder
public class ErrorMessage {
    private String code;
    private List<Map<String,String>>messages;
}
