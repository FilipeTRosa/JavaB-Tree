import java.io.*;

// Classe para representar a Árvore B
class ArvoreB implements Serializable {
    private static final long serialVersionUID = 1L;  // serialVersionUID para ArvoreB

    private NoArvoreB raiz;
    private int ordem;

    // Construtor para inicializar a árvore
    public ArvoreB(int ordem) {
        this.ordem = ordem;
        raiz = new NoArvoreB(ordem);
    }

    public NoArvoreB getRaiz() {
        return raiz;
    }

    // Função de busca
    public String buscar(int matricula) {
        return buscarNo(raiz, matricula);
    }

    private String buscarNo(NoArvoreB no, int matricula) {
        int i = 0;
        while (i < no.numChaves && matricula > no.matriculas[i]) {
            i++;
        }

        if (i < no.numChaves && matricula == no.matriculas[i]) {
            return no.nomes[i];
        }

        if (no.folha) {
            return null;
        }

        return buscarNo(no.filhos[i], matricula);
    }

    // Função de inserção
    public void inserir(int matricula, String nome) {
        NoArvoreB raizAntiga = raiz;

        if (raiz.numChaves == ordem - 1) {
            NoArvoreB novaRaiz = new NoArvoreB(ordem);
            novaRaiz.folha = false;
            novaRaiz.filhos[0] = raiz;
            dividir(novaRaiz, 0);
            raiz = novaRaiz;
        }

        inserirNo(raiz, matricula, nome);
    }

    private void dividir(NoArvoreB no, int i) {
        NoArvoreB filho = no.filhos[i];
        NoArvoreB novoNo = new NoArvoreB(ordem);
        novoNo.folha = filho.folha;

        int meio = ordem / 2;

        no.matriculas[i] = filho.matriculas[meio];
        no.nomes[i] = filho.nomes[meio];
        no.numChaves++;

        for (int j = meio + 1; j < ordem - 1; j++) {
            novoNo.matriculas[j - meio - 1] = filho.matriculas[j];
            novoNo.nomes[j - meio - 1] = filho.nomes[j];
        }

        filho.numChaves = meio;
        novoNo.numChaves = ordem - meio - 1;

        if (!filho.folha) {
            for (int j = meio + 1; j < ordem; j++) {
                novoNo.filhos[j - meio - 1] = filho.filhos[j];
            }
        }

        for (int j = no.numChaves; j > i + 1; j--) {
            no.filhos[j] = no.filhos[j - 1];
        }
        no.filhos[i + 1] = novoNo;
    }

    private void inserirNo(NoArvoreB no, int matricula, String nome) {
        int i = no.numChaves - 1;

        if (no.folha) {
            // Verifica se a chave já existe no nó
            for (int j = 0; j < no.numChaves; j++) {
                if (no.matriculas[j] == matricula) {
                    System.out.println("Erro: A matrícula " + matricula + " já existe na árvore.");
                    return;
                }
            }

            // Encontra o local adequado para inserir a chave
            while (i >= 0 && matricula < no.matriculas[i]) {
                no.matriculas[i + 1] = no.matriculas[i];
                no.nomes[i + 1] = no.nomes[i];
                i--;
            }

            no.matriculas[i + 1] = matricula;
            no.nomes[i + 1] = nome;
            no.numChaves++;
        } else {
            // Encontrar o filho correto para continuar a inserção
            while (i >= 0 && matricula < no.matriculas[i]) {
                i--;
            }
            i++;

            // Verifica se a chave já existe no nó antes de continuar
            for (int j = 0; j < no.numChaves; j++) {
                if (no.matriculas[j] == matricula) {
                    System.out.println("Erro: A matrícula " + matricula + " já existe na árvore.");
                    return;
                }
            }

            // Inicializa o filho se ele for null
            if (no.filhos[i] == null) {
                no.filhos[i] = new NoArvoreB(ordem);
            }

            // Se o filho estiver cheio, realiza a divisão
            if (no.filhos[i].numChaves == ordem - 1) {
                dividir(no, i);
                if (matricula > no.matriculas[i]) {
                    i++;
                }
            }

            inserirNo(no.filhos[i], matricula, nome);
        }
    }


    // Função para salvar a árvore em um arquivo
    public void salvarEmArquivo(String nomeArquivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            out.writeObject(this);
            System.out.println("Árvore B salva em " + nomeArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Função para imprimir a árvore B
    public void imprimirArvore() {
        imprimirNo(raiz, 0);
    }

    // Função auxiliar para imprimir um nó da árvore B
    private void imprimirNo(NoArvoreB no, int nivel) {
        if (no != null) {
            // Imprime o nível atual e as chaves do nó
            System.out.print("Nível " + nivel + ": ");
            for (int i = 0; i < no.numChaves; i++) {
                System.out.print(no.matriculas[i] + " (" + no.nomes[i] + ") ");
            }
            System.out.println();

            // Se o nó não é uma folha, imprime os filhos recursivamente
            if (!no.folha) {
                for (int i = 0; i <= no.numChaves; i++) {
                    imprimirNo(no.filhos[i], nivel + 1);
                }
            }
        }
    }
    // Função para carregar a árvore de um arquivo
    public static ArvoreB carregarDeArquivo(String nomeArquivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            return (ArvoreB) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
