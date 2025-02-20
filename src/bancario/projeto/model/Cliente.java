package bancario.projeto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import bancario.projeto.exception.ContaNaoEncontradaException;

public class Cliente implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String cpf;
	private String nome;
	private ArrayList<ContaBancaria> contas;
	
	
	public Cliente(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
		this.contas = new ArrayList<>();
	}
	
	public static boolean validarCPF(String cpf) {
        if (cpf.length() != 11) {
            System.out.println("CPF deve ter exatamente 11 caracteres.");
            return false;  
        }
        return true;
	}
	
	public static boolean validarNome(String nome) {
        if (nome.length() <= 3 || nome == null) {
            System.out.println("Nome nao pode ser nulo e deve conter mais de 3 caracteres");
            return false;  
        }
        return true;
	}
	 	
	public void adicionarConta(ContaBancaria c) {
		if(contas.contains(c)) {
			System.out.println("Conta ja cadastrada!");
		} else {
			contas.add(c);
			System.out.println("Conta cadastrada com sucesso!");
		}
	}
	
	public void removerConta(ContaBancaria c) {
		if(contas.contains(c)) {
			contas.remove(c);
			System.out.println("Conta removida com sucesso!");
		} else {
			System.out.println("Conta nÃ£o localizada!");				
		}
	}
	
	public ContaBancaria localizarContaPorNumero(int numeroConta) throws ContaNaoEncontradaException {
	    for (ContaBancaria conta : contas) {
	        if (conta.getNumeroConta() == numeroConta) {
	            return conta;
	        }
	    }
	    throw new ContaNaoEncontradaException("Conta com número " + numeroConta + " não foi encontrada.");
	}

	
	public void atualizarConta(ContaBancaria c) {
		if(contas.contains(c)) {
			int index = contas.indexOf(c);
			contas.set(index, c);
			System.out.println("Conta atualizada com sucesso!");
		} else { 
			System.out.println("Conta nÃ£o localizada!");			
		}
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<ContaBancaria> getContas() {
		return contas;
	}

	public void setContas(ArrayList<ContaBancaria> contas) {
		this.contas = contas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
	}

	@Override
    public String toString() {
        return    " ____________________________________" + "\n\n"
        		+ " Cliente: " + nome + "\n"
        		+ " CPF: " + cpf + "\n"
        		+ " ____________________________________";
    }
	
}
