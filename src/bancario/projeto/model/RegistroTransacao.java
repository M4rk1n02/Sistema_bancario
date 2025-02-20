package bancario.projeto.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroTransacao implements Serializable{
	private static final long serialVersionUID = 1L;
	
    private String tipo;        
    private float valor;        
    private LocalDateTime data; 

    public RegistroTransacao(String tipo, float valor, LocalDateTime data) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public float getValor() {
        return valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    @Override
    public String toString() {
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) 
               + " - " + tipo + ": R$" + valor;
    }
}
