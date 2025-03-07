import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        LeitorTxt leitor = new LeitorTxt("input\\ordExt_teste.txt");
        BTree bt = new BTree(1000);
        double linha = leitor.proximaLinha();
        int cont = 0;
        while (linha > -1) {
            bt.insert(linha);
            linha = leitor.proximaLinha();
            cont++;
        }
        //System.out.println(cont);
        //bt.printBTree();
    }
}
