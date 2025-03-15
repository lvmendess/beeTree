import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class Main {
    /*public static void main(String[] args) throws FileNotFoundException{
        LeitorTxt leitor = new LeitorTxt("input\\ordExt_teste.txt");
        BTree bt = new BTree(100);
        double linha = leitor.proximaLinha();
        int cont = 0;
        while (linha > -1) {
            bt.insert(linha);
            linha = leitor.proximaLinha();
            cont++;
        }
        System.out.println(cont);
        System.out.println(Arrays.toString(bt.search(0.7518487311173618).keys));
        bt.remove(0.7518487311173618);
        Node teste = bt.search(0.7518487311173618);
        if (teste != null) {
            System.out.println(Arrays.toString(teste.keys));
        } else {
            System.out.println("null");
        }
        
        //bt.printBTree();
    }*/

    public static void main(String[] args) throws FileNotFoundException {
        LeitorTxt leitor = new LeitorTxt("input\\ordExt_teste.txt");
        btree2 bt = new btree2(100);
        double[] linha;
        int contador = 0;
        int contadorInsercoes = 0;
        while((linha = leitor.proximaLinha())[0] > -1){
            if (contador % 1000 == 0) {
                System.out.println("Inserindo na arvore a tupla " + Arrays.toString(linha));
                bt.insert(linha);
                contadorInsercoes++;
            }
            contador++;
        }
        System.out.println("Total: " + contador);
        System.out.println("Inserções: " + contadorInsercoes);
        bt.printBTree();
        System.out.println(bt.search(0.3666983707019249));
        System.out.println(bt.search(0.3666983707019250));
    }
}
