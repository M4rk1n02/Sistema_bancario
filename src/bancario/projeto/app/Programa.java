package bancario.projeto.app;

import bancario.projeto.exception.ClienteNaoEncontradoException;
import bancario.projeto.exception.ContaNaoEncontradaException;
import bancario.projeto.model.Cliente;
import bancario.projeto.model.ContaBancaria;
import bancario.projeto.model.ContaCorrente;
import bancario.projeto.model.ContaPoupanca;
import bancario.projeto.persistencia.PersistenciaCliente;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Programa {

    public static void main(String[] args) {
        PersistenciaCliente persistencia = new PersistenciaCliente();
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        System.out.println("Bem-vindo ao sistema bancario!");

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
                    default -> System.out.println("Opcao invalida. Por favor, escolha uma opcao valida: \n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. \n");
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
        Cliente cliente = null;
		try {
			cliente = persistencia.localizarClientePorCpf(cpf);
		} catch (ClienteNaoEncontradoException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
        
        if (cliente != null) {
            persistencia.removerCliente(cliente);
        } else {
            System.out.println("Cliente nao encontrado. \n");
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
        Cliente cliente = null;
		try {
			cliente = persistencia.localizarClientePorCpf(cpf);
			System.out.println(" ____________________________________"+ "\n\n"+     					   	
					   " Nome: " + cliente.getNome()         + "\n"  +
					   " CPF: " + cliente.getCpf()           + "\n"  +
					   " ____________________________________");
			
		} catch (ClienteNaoEncontradoException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
        
    }

//-------------------------------------------------------------------------------------------------------------------------------//
    private static void opcoesDeCliente(PersistenciaCliente persistencia, Scanner scanner) {
        scanner.nextLine();
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();
        Cliente cliente = null;
        
		try {
			cliente = persistencia.localizarClientePorCpf(cpf);
		} catch (ClienteNaoEncontradoException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());			
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
                System.out.println("|8 -> Imprimir Extrato               |");
                System.out.println("|9 -> Remover Conta                  |");
                System.out.println("|10 -> Voltar ao Menu Principal      |");
                System.out.println("|____________________________________|");
                       
                int opcao = scanner.nextInt();

                switch (opcao) {
                	case 1 -> novaConta(cliente, persistencia, scanner);               
	                case 2 -> depositarSaldo(cliente, scanner);
                    case 3 -> saqueSaldo(cliente, scanner);
                    case 4 -> transferirSaldo(persistencia, cliente, scanner);
                    case 5 -> saldoConta(cliente, scanner);
                    case 6 -> saldoContaTotal(cliente, scanner);
                    case 7 -> System.out.println(cliente.getContas());
                    case 8 -> imprimirExtrato(cliente,scanner);
                    case 9 -> removerConta(cliente, scanner);
                    case 10 -> voltar = true;
                    default -> System.out.println("Operacao invalida. \n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. \n");
                scanner.nextLine();
            }
        }
    }
    
//-------------------------------------------------------------------------------------------------------------------------------//
    private static void novaConta(Cliente cliente, PersistenciaCliente persistencia, Scanner scanner) {
    	System.out.println("Digite o tipo de conta \n(1 para Corrente, 2 para Poupanca): ");
        int tipo = scanner.nextInt();
        
        ContaBancaria novaConta;
        if (tipo == 1) {
            novaConta = new ContaCorrente(cliente.getContas().size() + 1); 
        } else {
            novaConta = new ContaPoupanca(cliente.getContas().size() + 1);
        }
        
        try {
			persistencia.adicionarContaAoCliente(cliente.getCpf(), novaConta);
		} catch (ClienteNaoEncontradoException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
        novaConta.exibirTipo(); 
    }
    
//-------------------------------------------------------------------------------------------------------------------------------//
    private static void depositarSaldo(Cliente cliente, Scanner scanner) {
        System.out.print("Digite o numero da conta: ");
       	int numeroConta = scanner.nextInt();       	
       	ContaBancaria conta = null;
		try {
			conta = cliente.localizarContaPorNumero(numeroConta);
		} catch (ContaNaoEncontradaException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
       	
       	if (conta != null) {
       		System.out.print("Digite o valor do deposito: \n");
       		float valor = scanner.nextFloat();
       		conta.depositar(valor);
       	} else {
       		System.out.println("Conta nao encontrada. \n");
       	}
    }
    
  //-------------------------------------------------------------------------------------------------------------------------------//    
    private static void saqueSaldo(Cliente cliente, Scanner scanner) {
        System.out.print("Digite o numero da conta: "); 
        int numeroConta = scanner.nextInt();
        ContaBancaria conta = null;
        
		try {
			conta = cliente.localizarContaPorNumero(numeroConta);
		} catch (ContaNaoEncontradaException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
        
        if (conta != null) {
        	System.out.print("Digite o valor do saque: ");
        	float valor = scanner.nextFloat();
        	conta.sacar(valor);
        } else {
        	System.out.println("Conta nao encontrada. \n");
        }
    }

//-------------------------------------------------------------------------------------------------------------------------------//
    private static void transferirSaldo(PersistenciaCliente persistencia, Cliente cliente, Scanner scanner) {
    	   	    
    	System.out.print("Digite o numero da conta de origem: \n");
    	int numeroContaOrigem = scanner.nextInt();
    	ContaBancaria contaOrigem = null;
    	
		try {
			contaOrigem = cliente.localizarContaPorNumero(numeroContaOrigem);
		} catch (ContaNaoEncontradaException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
   	    
    	if(contaOrigem == null) {
    		System.out.println("Conta nao encontrada. \n");
    		return;
    	}
    	    
    	scanner.nextLine();
    	    
    	System.out.print("Digite o CPF do cliente que recebera o dinheiro: \n");
    	String cpfDestino = scanner.nextLine();
    	Cliente clienteDestinoTransferencia = null;
		try {
			clienteDestinoTransferencia = persistencia.localizarClientePorCpf(cpfDestino);
		} catch (ClienteNaoEncontradoException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
    	    
    	if(clienteDestinoTransferencia == null) {
    		System.out.println("Cliente nao encontrado.");
    		return;
    	}
    	    
    	System.out.print("Digite o numero da conta de destino: \n");
    	int numeroContaDestino = scanner.nextInt();
    	ContaBancaria contaDestino = null;
		try {
			contaDestino = clienteDestinoTransferencia.localizarContaPorNumero(numeroContaDestino);
		} catch (ContaNaoEncontradaException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
    	    
    	if(contaDestino == null) {
    		System.out.println("Conta nao encontrada. \n");
    		return;
    	}
    	    
    	System.out.print("Digite o valor da transferancia: \n");
    	float valor = scanner.nextFloat();
    	    
    	if (contaOrigem.transferir(contaDestino, valor)) {
    		System.out.println("Transferancia realizada com sucesso. \n");
    	} else {
    		System.out.println("Saldo insuficiente. \n");
    	}
	}
    
//-------------------------------------------------------------------------------------------------------------------------------//    
    private static void saldoConta(Cliente cliente, Scanner scanner) {
        System.out.print("Digite o numero da conta: "); 
        int numeroConta = scanner.nextInt();
        ContaBancaria conta = null;
        
		try {
			conta = cliente.localizarContaPorNumero(numeroConta);
		} catch (ContaNaoEncontradaException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
        
        if(conta != null) {
        	System.out.println("O saldo da conta " + conta.getNumeroConta() + " e de :" +  conta.getSaldo());
        }else {
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
    private static void imprimirExtrato(Cliente cliente, Scanner scanner) {
        System.out.println("Digite o numero da conta para ver o extrato:");
        int numeroConta = scanner.nextInt();

        ContaBancaria conta = cliente.getContas().stream()
                .filter(c -> c.getNumeroConta().equals(numeroConta))
                .findFirst()
                .orElse(null);

        if (conta == null) {
            System.out.println("Conta nao encontrada.");
            return;
        }

        conta.imprimirExtrato();
    }

//-------------------------------------------------------------------------------------------------------------------------------//    
    private static void removerConta(Cliente cliente, Scanner scanner) {
    	 System.out.print("Digite o numero da conta: "); 
         int numeroConta = scanner.nextInt();
         ContaBancaria conta = null;
         
		try {
			conta = cliente.localizarContaPorNumero(numeroConta);
		} catch (ContaNaoEncontradaException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
         cliente.removerConta(conta);
    }    
}
