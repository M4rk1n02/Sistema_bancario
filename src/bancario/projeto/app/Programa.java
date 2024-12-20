package bancario.projeto.app;

import bancario.projeto.model.Cliente;
import bancario.projeto.model.ContaBancaria;
import bancario.projeto.persistencia.PersistenciaCliente;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Programa {

    public static void main(String[] args) {
        PersistenciaCliente persistencia = new PersistenciaCliente();
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        System.out.println("Bem-vindo ao sistema bancário!");

        while (continuar) {
    
        	try {
            	System.out.println(" ____________________________________ ");
            	System.out.println("|                                    |");
                System.out.println("|           Menu Principal           |");
                System.out.println("|____________________________________|");
                System.out.println("|        O que deseja fazer?         |");
                System.out.println("|1 -> Cadastrar novo cliente         |");
                System.out.println("|2 -> Login                          |");
                System.out.println("|3 -> Listar todos os clientes       |");
                System.out.println("|4 -> Buscar cliente via cpf         |");
                System.out.println("|5 -> Remover cliente                |");
                System.out.println("|6 -> Sair                           |");
                System.out.println("|____________________________________|");

                
                int opcao = scanner.nextInt();

                switch (opcao) {
                    case 1 -> cadastrarCliente(persistencia, scanner);
                    case 2 -> opcoesDeCliente(persistencia, scanner);
                    case 3 -> listarClientes(persistencia);
                    case 4 -> consultarClientePorCpf(persistencia, scanner); 
                    case 5 -> removerCliente(persistencia, scanner);
                    case 6 -> {System.out.println("saindo...");
                    	continuar = false;}
                    default -> System.out.println("Opção inválida. Por favor, escolha uma opção valida: \n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. \n");
                scanner.nextLine();
            }
        }
    }
    
    
//-------------------------------------------------------------------------------------------------------------------------------// 
    private static void cadastrarCliente(PersistenciaCliente persistencia, Scanner scanner) {
        scanner.nextLine(); 
        System.out.print("Insira o CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Insira o nome: ");
        String nome = scanner.nextLine();
        
        if(Cliente.validarCPF(cpf) && Cliente.validarNome(nome) ) {
            persistencia.adicionarCliente(new Cliente(cpf, nome));
        }
    }
    
    
//-------------------------------------------------------------------------------------------------------------------------------//    
    private static void removerCliente(PersistenciaCliente persistencia, Scanner scanner) {
        scanner.nextLine(); 
        System.out.print("Insira o CPF do cliente a ser removido: ");
        String cpf = scanner.nextLine();
        Cliente cliente = persistencia.localizarClientePorCpf(cpf);
        
        if (cliente != null) {
            persistencia.removerCliente(cliente);
        } else {
            System.out.println("Cliente não encontrado. \n");
        }
    }
    
    
//-------------------------------------------------------------------------------------------------------------------------------//   
    private static void listarClientes(PersistenciaCliente persistencia) {
        persistencia.listarClientes();   
    }
    
    
//-------------------------------------------------------------------------------------------------------------------------------//    
    private static void consultarClientePorCpf(PersistenciaCliente persistencia, Scanner scanner) {
    	scanner.nextLine();
        System.out.print( "Digite o CPF do cliente que deseja consultar: " );        
        String cpf = scanner.nextLine();
        Cliente cliente = persistencia.localizarClientePorCpf(cpf);
        
        if (cliente != null) {
        	System.out.println(" ____________________________________"+ "\n\n"+     					   	
        					   " Nome: " + cliente.getNome()         + "\n"  +
        					   " CPF: " + cliente.getCpf()           + "\n"  +
        					   " ____________________________________");
        } else {
        	System.out.println("Cliente não encontrado. \n");
        }
        
    }


//-------------------------------------------------------------------------------------------------------------------------------//
    private static void opcoesDeCliente(PersistenciaCliente persistencia, Scanner scanner) {
        scanner.nextLine();
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();
        Cliente cliente = persistencia.localizarClientePorCpf(cpf);
        
        if (cliente == null) {
            System.out.println("Cliente não encontrado. \n");
            return;
        }

        boolean voltar = false;
        while (!voltar) {
            try {
            	System.out.println(" ____________________________________ ");
            	System.out.println("|                                    |");
                System.out.println("|            Menu Cliente            |");
                System.out.println("|____________________________________|");
                System.out.println("|             Bem vindo!             |");
                System.out.println("|        O que deseja fazer?         |");
                System.out.println("|1 -> Criar Conta                    |");
                System.out.println("|2 -> Depositar                      |");
                System.out.println("|3 -> Sacar                          |");
                System.out.println("|4 -> Transferir                     |");
                System.out.println("|5 -> Saldo de uma conta             |");
                System.out.println("|6 -> Saldo total das Contas         |");
                System.out.println("|7 -> Listar Contas                  |");
                System.out.println("|8 -> Remover Conta                  |");
                System.out.println("|9 -> Voltar ao Menu Principal       |");
                System.out.println("|____________________________________|");
                       
                int opcao = scanner.nextInt();

                switch (opcao) {
	                case 1 -> {
	                    ContaBancaria novaConta = new ContaBancaria(cliente.getContas().size() + 1);
	                    persistencia.adicionarContaAoCliente(cpf, novaConta);
	                }                    
	                case 2 -> depositarSaldo(cliente, scanner);
                    case 3 -> saqueSaldo(cliente, scanner);
                    case 4 -> transferirSaldo(cliente, scanner);
                    case 5 -> saldoConta(cliente, scanner);
                    case 6 -> saldoContaTotal(cliente, scanner);
                    case 7 -> System.out.println(cliente.getContas());
                    case 8 -> removerConta(cliente, scanner);
                    case 9 -> voltar = true;
                    default -> System.out.println("Opção inválida. \n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. \n");
                scanner.nextLine();
            }
        }
    }

//-------------------------------------------------------------------------------------------------------------------------------//
    private static void depositarSaldo(Cliente cliente, Scanner scanner) {
        System.out.print("Digite o número da conta: ");
       	int numeroConta = scanner.nextInt();       	
       	ContaBancaria conta = cliente.localizarContaPorNumero(numeroConta);
       	
       	if (conta != null) {
       		System.out.print("Digite o valor do depósito: \n");
       		float valor = scanner.nextFloat();
       		conta.depositar(valor);
       	} else {
       		System.out.println("Conta não encontrada. \n");
       	}
    }
    
  //-------------------------------------------------------------------------------------------------------------------------------//    
    private static void saqueSaldo(Cliente cliente, Scanner scanner) {
        System.out.print("Digite o número da conta: "); 
        int numeroConta = scanner.nextInt();
        ContaBancaria conta = cliente.localizarContaPorNumero(numeroConta);
        
        if (conta != null) {
        	System.out.print("Digite o valor do saque: ");
        	float valor = scanner.nextFloat();
        	conta.sacar(valor);
        } else {
        	System.out.println("Conta não encontrada. \n");
        }
    }

//-------------------------------------------------------------------------------------------------------------------------------//
    private static void transferirSaldo(Cliente cliente, Scanner scanner) {
        System.out.print("Digite o número da sua conta: ");
        int numeroContaOrigem = scanner.nextInt();
        System.out.print("Digite o número da conta de destino: ");
        int numeroContaDestino = scanner.nextInt();
        
        
        ContaBancaria contaOrigem = cliente.localizarContaPorNumero(numeroContaOrigem);
        ContaBancaria contaDestino = cliente.localizarContaPorNumero(numeroContaDestino);
        
        if (contaOrigem != null && contaDestino != null) {
        	System.out.print("Digite o valor da transferência: ");
        	float valor = scanner.nextFloat();
        	contaOrigem.transferir(contaDestino, valor);
        } else {
        	System.out.println("Conta(s) não encontrada(s).");
        }
    }
    
//-------------------------------------------------------------------------------------------------------------------------------//    
    private static void saldoConta(Cliente cliente, Scanner scanner) {
        System.out.print("Digite o número da conta: "); 
        int numeroConta = scanner.nextInt();
        ContaBancaria conta = cliente.localizarContaPorNumero(numeroConta);
        
        if(conta != null) {
        	System.out.println("O saldo da conta " + conta.getNumeroConta() + " é de :" +  conta.getSaldo());
        }else {
        	System.out.println("conta nao encontrada");
        	return;
        }
        
   }
    
//-------------------------------------------------------------------------------------------------------------------------------//    
    private static void saldoContaTotal(Cliente cliente, Scanner scanner) {
    	 float saldoTotal = 0;
    	    for (ContaBancaria conta : cliente.getContas()) {
    	        saldoTotal += conta.getSaldo(); 
    	    }
    	    System.out.println("Saldo total de todas as contas de: " + saldoTotal);
    }
    
//-------------------------------------------------------------------------------------------------------------------------------//    
    private static void removerConta(Cliente cliente, Scanner scanner) {
    	 System.out.print("Digite o número da conta: "); 
         int numeroConta = scanner.nextInt();
         ContaBancaria conta = cliente.localizarContaPorNumero(numeroConta);
         cliente.removerConta(conta);
    }
   
    
// Função para apagar um cliente individualmente
//    private static boolean removerClienteIndividual(Cliente cliente, PersistenciaCliente persistencia) {
//        if (cliente != null) {
//            persistencia.removerCliente(cliente);
//            return true;
//        } else {
//            return false; 
//        }
//    }
    
}
