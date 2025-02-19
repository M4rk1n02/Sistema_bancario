package bancario.projeto.model;

public class ContaCorrente extends ContaBancaria {
	private static final long serialVersionUID = 1L;
	
	public ContaCorrente(Integer numero) {
        super(numero);
    }

    @Override
    public void exibirTipo() {
        System.out.println("Tipo de Conta: Corrente");
    }

    @Override
    public String toString() {
        return super.toString() + "Tipo: Corrente"; 
    }
    
    public float calcularTarifaTransferencia(float quantia) {
        return quantia * 0.01f; 
    }
}
