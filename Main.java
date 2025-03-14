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
        RandomAccessFile rafi = new RandomAccessFile("input\\ordExt_teste.txt", null);
        BTree bt = new BTree(100);
    }
}
