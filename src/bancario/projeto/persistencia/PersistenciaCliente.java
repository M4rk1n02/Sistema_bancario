package bancario.projeto.persistencia;

import bancario.projeto.model.Cliente;
import bancario.projeto.model.ContaBancaria;

import java.io.*;
import java.util.ArrayList;

public class PersistenciaCliente {
	
    private static final String arquivo_cliente = "clientes.dat";
    private ArrayList<Cliente> clientes;
    
    
//-------------------------------------------------------------------------------------------------------------------------------//
    
    public PersistenciaCliente() {
        clientes = carregarClientes();
    }

    public void adicionarCliente(Cliente c) {
        if (clientes.contains(c)) {
            System.out.println("Cliente já cadastrado!");
        } else {
            clientes.add(c);
            salvarClientes();
            System.out.println("Cliente cadastrado com sucesso!");
        }
    }

    public void removerCliente(Cliente c) {
        if (clientes.contains(c)) {
            clientes.remove(c);
            salvarClientes();
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public Cliente localizarClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public void atualizarCliente(Cliente c) {
        if (clientes.contains(c)) {
            int index = clientes.indexOf(c);
            clientes.set(index, c);
            salvarClientes();
            System.out.println("Cliente atualizado com sucesso!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
        	System.out.println(" ____________________________________ ");
        	System.out.println("|                                    |");
            System.out.println("|         Lista dos clientes         |");
            System.out.println("|____________________________________|");
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
    }
    
//-------------------------------------------------------------------------------------------------------------------------------//
    
    public void adicionarContaAoCliente(String cpf, ContaBancaria conta) {
        Cliente cliente = localizarClientePorCpf(cpf);
        if (cliente != null) {
            cliente.adicionarConta(conta);
            atualizarCliente(cliente); 
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    
    public void removerContaDoCliente(String cpf, int numeroConta) {
        Cliente cliente = localizarClientePorCpf(cpf);
        if (cliente != null) {
            ContaBancaria conta = cliente.localizarContaPorNumero(numeroConta);
            if (conta != null) {
                cliente.removerConta(conta);
                atualizarCliente(cliente); 
            } else {
                System.out.println("Conta não encontrada.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public void atualizarContaDoCliente(String cpf, ContaBancaria contaAtualizada) {
        Cliente cliente = localizarClientePorCpf(cpf);
        if (cliente != null) {
            cliente.atualizarConta(contaAtualizada);
            atualizarCliente(cliente); 
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    
    private void salvarClientes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo_cliente))) {
            oos.writeObject(clientes);
        } catch (IOException e) {
            System.err.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Cliente> carregarClientes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo_cliente))) {
            return (ArrayList<Cliente>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de clientes não encontrado. Criando uma nova lista.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar clientes: " + e.getMessage());
        }
        return new ArrayList<>();
    }

}
