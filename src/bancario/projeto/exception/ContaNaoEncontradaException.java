package bancario.projeto.exception;

public class ContaNaoEncontradaException extends Exception {
	private static final long serialVersionUID = 1L;
	
    public ContaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
