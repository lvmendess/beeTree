import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        LeitorTxt leitor = new LeitorTxt("input\\ordExt_teste.txt");
        BTree bt = new BTree(100);
        double linha = leitor.proximaLinha();
        int cont = 0;
        long start = System.nanoTime();
        while (linha > -1) {
            bt.insert(linha);
            linha = leitor.proximaLinha();
            cont++;
        }
        //System.out.println(cont);
        double key = 0.7518487311173618;
        System.out.println("busca pela chave "+key);
        System.out.println("chave "+key+" encontrada no nó "+(bt.search(key)));
        System.out.println("remoção da chave "+key);
        bt.remove(0.7518487311173618);
        Node teste = bt.search(0.7518487311173618);
        if (teste != null) {
            System.out.println("chave "+key+" encontrada no nó "+(bt.search(key)));
        } else {
            System.out.println("chave "+key+" não foi encontrada");
        }
        long finish = System.nanoTime();
        System.out.println((finish - start)/ 1000000+"ms");
    }
}
