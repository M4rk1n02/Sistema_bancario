package bancario.projeto.model;

public class ContaPoupanca extends ContaBancaria {
	private static final long serialVersionUID = 1L;
	
	public ContaPoupanca(Integer numero) {
		super(numero);
	}

	@Override
	public void exibirTipo() {
		System.out.println("Tipo de Conta: Poupança");
	}

	@Override
	public String toString() {
		return super.toString() + "Tipo: Poupança"; 
	}
	
	public float calcularTarifaTransferencia(float quantia) {
        return quantia * 0.015f; 
    }
}
