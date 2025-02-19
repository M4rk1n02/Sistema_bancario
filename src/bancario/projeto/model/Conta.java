package bancario.projeto.model;

public interface Conta {
    void depositar(float quantia);
    void sacar(float quantia);
    boolean transferir(ContaBancaria c, float quantia);
    float consultarSaldo();
    float calcularTarifaTransferencia(float quantia);
}
