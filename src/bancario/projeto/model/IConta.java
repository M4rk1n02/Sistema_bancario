package bancario.projeto.model;

public interface IConta {
    void depositar(float quantia);
    void sacar(float quantia);
    boolean transferir(IConta contaDestino, float quantia);
    float consultarSaldo();
    float calcularTarifaTransferencia(float quantia);
    void exibirTipo();  
}
