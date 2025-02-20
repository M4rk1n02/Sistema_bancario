package bancario.projeto.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContaBancaria implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer numeroConta;
    private float saldo;
    private String dataAbertura; //Não tava salvando no .dat como datetime, tive que passar como string para converter com o formatter;
    private boolean status;
    private List<RegistroTransacao> transacoes;

    public ContaBancaria(Integer numero) {
        this.numeroConta = numero;
        this.saldo = 0f;
        this.dataAbertura = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        this.status = true;
        this.transacoes = new ArrayList<>();
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
		return "\n Numero da conta=(" + numeroConta + ")\n   Saldo=(" + saldo + ")\n   Data de Abertura=(" + dataAbertura + ")\n   status=(" + status+ ") \n";
	}

    public void exibirTipo() {
   	} 
    
//-------------------------------------------------------------------------------------------------------------------------------//
    
	public void depositar(float quantia) {
        if (status) {
            if (quantia > 0) {
                this.saldo += quantia;
                System.out.println("Depósito realizado com sucesso.");
                registrarTransacao("Depósito", quantia);
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
                    System.out.println("Saque realizado com sucesso.");
                    registrarTransacao("Saque", quantia);
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

    public boolean transferir(ContaBancaria contaDestino, float valor) {
        if (this.saldo >= valor) {
            float tarifa = calcularTarifaTransferencia(valor);
            float valorComTarifa = valor + tarifa; 

            if (this.saldo >= valorComTarifa) {
                this.saldo -= valorComTarifa;
                contaDestino.saldo += valor;
                System.out.println("Tarifa aplicada: R$" + tarifa);
                return true;
            } else {
                System.out.println("Saldo insuficiente para cobrir a tarifa.");
                return false;
            }
        } else {
            System.out.println("Saldo insuficiente para a transferência.");
            return false;
        }
    }
    
    public void registrarTransacao(String tipo, float valor) {
        if (transacoes == null) {
            transacoes = new ArrayList<>();
        }
        RegistroTransacao transacao = new RegistroTransacao(tipo, valor, LocalDateTime.now());
        transacoes.add(transacao);
    }

    
    public void imprimirExtrato() {
    	
        System.out.println("Extrato da conta " + numeroConta);
        System.out.println("______________________________________");

        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma movimentação encontrada.");
        } else {
            transacoes.forEach(System.out::println);
        }
        
        System.out.println("______________________________________");
    }
    
    public float calcularTarifaTransferencia(float quantia) {
        float tarifa = 0.0f;
        if (this instanceof ContaCorrente) {
            tarifa = quantia * 0.01f;
        } else if (this instanceof ContaPoupanca) {
            tarifa = quantia * 0.005f;
        }
        System.out.println("Tarifa de transferência: " + tarifa);
        return tarifa;
    }
    
    
    public void saldoTotal(ContaBancaria c, float quantia) {    	
    	 if (status && c.isStatus()) {
    		 saldo += quantia;
    	 } else {
    	        System.out.println("Operação não pode ser realizada entre contas desativadas.");
    	 }
    }
    
}
