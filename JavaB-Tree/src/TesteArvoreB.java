public class TesteArvoreB {
    public static void main(String[] args) {
        String arquivo = "arvoreB.dat";
        ArvoreB arvore;

        // Tentar carregar a árvore do arquivo
        arvore = ArvoreB.carregarDeArquivo(arquivo);
        if (arvore == null) {
            System.out.println("Nenhum arquivo encontrado. Criando uma nova árvore.");
            arvore = new ArvoreB(4);  // Ordem 4
        }

        // Inserir alguns dados de exemplo
        arvore.inserir(12345678, "João Silva");
        arvore.inserir(23456789, "Maria Oliveira");
        arvore.inserir(34567890, "Carlos Souza");
        arvore.inserir(45678901, "Ana Costa");
        arvore.inserir(56789012, "Paula Santos");
        arvore.inserir(67890123, "Roberto Lima");
        arvore.inserir(78901234, "Lucia Mendes");

        // Imprimir a estrutura da árvore
        System.out.println("\nEstrutura da Árvore B:");
        arvore.imprimirArvore();

        // Salvar a árvore em um arquivo
        arvore.salvarEmArquivo(arquivo);
    }
}
