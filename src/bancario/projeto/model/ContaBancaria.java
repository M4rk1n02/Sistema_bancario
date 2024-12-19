package bancario.projeto.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ContaBancaria implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer numeroConta;
    private float saldo;
    private String dataAbertura; //Não tava salvando no .dat como datetime, tive que passar como string para converter com o formatter;
    private boolean status;

    public ContaBancaria(Integer numero) {
        this.numeroConta = numero;
        this.saldo = 0f;
        this.dataAbertura = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        this.status = true;
    }
    
//-------------------------------------------------------------------------------------------------------------------------------//
    
    public Integer getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Integer numeroConta) {
        this.numeroConta = numeroConta;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public LocalDateTime getDataAbertura() {
        return LocalDateTime.parse(dataAbertura, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

//-------------------------------------------------------------------------------------------------------------------------------//
    
    @Override
    public int hashCode() {
        return Objects.hash(numeroConta);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ContaBancaria other = (ContaBancaria) obj;
        return Objects.equals(numeroConta, other.numeroConta);
    }
    
    @Override
	public String toString() {
		return " Numero da conta=(" + numeroConta + ")\n   Saldo=(" + saldo + ")\n   Data de Abertura=(" + dataAbertura + ")\n   status=(" + status+ ") \n";
	}
    
//-------------------------------------------------------------------------------------------------------------------------------//
    
	public void depositar(float quantia) {
        if (status) {
            if (quantia > 0) {
                this.saldo += quantia;
                System.out.println("Depósito realizado com sucesso.");
            } else {
                System.err.println("Valor inválido para depósito.");
            }
        } else {
            System.err.println("Operação não permitida. Conta desativada.");
        }
    }

    public void sacar(float quantia) {
        if (status) {
            if (quantia > 0) {
                if (this.saldo >= quantia) {
                    this.saldo -= quantia;
                    System.out.println("Saque realizado com sucesso!");
                } else {
                    System.err.println("Saldo insuficiente.");
                }
            } else {
                System.err.println("Valor inválido para saque.");
            }
        } else {
            System.err.println("Operação não permitida. Conta desativada.");
        }
    }

    public void transferir(ContaBancaria c, float quantia) {
    	if (status && c.isStatus()) {
            if (quantia <= 0) {
                System.err.println("Valor inválido para transferência.");
            } else if (quantia <= saldo) {
                this.saldo -= quantia;
                c.saldo += quantia;
                System.out.println("Transferência realizada com sucesso.");
            } else {
                System.err.println("Saldo insuficiente para realizar a transferência.");
            }
        } else {
            System.err.println("Operação não pode ser realizada entre contas desativadas.");
        }
    }
    
    public void saldoTotal(ContaBancaria c, float quantia) {    	
    	 if (status && c.isStatus()) {
    		 saldo += quantia;
    	 } else {
    	        System.out.println("Operação não pode ser realizada entre contas desativadas.");
    	 }
    }
}
