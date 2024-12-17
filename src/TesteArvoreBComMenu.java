import java.util.*;
import java.io.*;

public class TesteArvoreBComMenu {
    public static void main(String[] args) {
        try {
            String arquivoRaiz = "raiz.txt";
            ArvoreB arvore = new ArvoreB(arquivoRaiz, 6); // Grau
            Scanner scanner = new Scanner(System.in);
            String chave;
            String nome;

            while (true) {
                System.out.println("\nMenu de Opções:");
                System.out.println("1. Inserir um novo registro");
                System.out.println("2. Pesquisar um registro");
                System.out.println("3. Remover um registro"); // Nova opção
                System.out.println("4. Carregar Banco de arquivo TXT"); // Nova opção
                System.out.println("5. Imprimir");
                System.out.println("6. Sair");
                System.out.print("Escolha uma opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha

                switch (opcao) {
                    case 1:
                        System.out.print("Digite a chave: ");
                        chave = scanner.nextLine();
                        System.out.print("Digite o nome: ");
                        nome = scanner.nextLine();
                        System.out.print("Digite os outros campos: ");
                        String outrosCampos = scanner.nextLine();

                        Registro novoRegistro = new Registro(chave, nome, outrosCampos, "null", "null");
                        arvore.inserir(novoRegistro);
                        System.out.println("Registro inserido com sucesso!");
                        break;

                    case 2:
                        // Realizar uma busca
                        System.out.print("Digite a chave para buscar: ");
                        String chaveBusca = scanner.nextLine();
                        Registro resultado = arvore.buscar(chaveBusca);
                        if (resultado != null) {
                            System.out.println("Registro encontrado: " + resultado);
                        } else {
                            System.out.println("Registro não encontrado.");
                        }
                        break;

                    case 3:
                        System.out.print("Digite a chave para remover: ");
                        String chaveRemover = scanner.nextLine();
                        if (arvore.remover(chaveRemover)) {
                            System.out.println("Registro removido com sucesso.");
                        } else {
                            System.out.println("Erro: Registro não encontrado.");
                        }
                        break;
                    case 4:
                        System.out.println("Carregar arvore de arquivo ");
                        String arquivoEntrada = "dados_50.csv";

                        // Ler registros do arquivo e inserir na árvore
                        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoEntrada))) {
                            String linha;
                            boolean isFirstLine = true;
                            while ((linha = reader.readLine()) != null) {
                                if (isFirstLine) {
                                    isFirstLine = false; // Ignorar a primeira linha (cabeçalho)
                                    continue;
                                }
                                String[] partes = linha.split(";");
                                if (partes.length == 3) {
                                    chave = partes[0];
                                    nome = partes[1];
                                    String outrosDados = partes[2];
                                    Registro registro = new Registro(chave, nome, outrosDados, "null", "null");
                                    arvore.inserir(registro);
                                }
                            }
                        }

                        System.out.println("Todos os registros foram inseridos na Árvore B com sucesso.");
                        break;
                    case 5:
                        System.out.println("Impressao ArvoreB ");
                        arvore.imprimirArvorePersonalizado();

                        break;
                    case 6:
                        System.out.println("Encerrando o programa.");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
