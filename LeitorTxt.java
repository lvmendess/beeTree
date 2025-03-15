import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

public class LeitorTxt {
    private String diretorioArquivo;
    BufferedReader entradaDeDados;
    private StringTokenizer strK;
    RandomAccessFile rafi;
    
    /*public LeitorTxt(String diretorioArquivo) throws FileNotFoundException {
        this.diretorioArquivo = diretorioArquivo;
        this.entradaDeDados = new BufferedReader(new FileReader(diretorioArquivo));
    }*/

    public LeitorTxt(String diretorioArquivo){
        this.diretorioArquivo = diretorioArquivo;
        try {
            this.rafi = new RandomAccessFile(diretorioArquivo, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public double[] proximaLinha(){
        double linhaDouble = -1;
        double offset = -1;
        try {
            offset = (double) rafi.getFilePointer();
            String linha = rafi.readLine(); //guardar linha original na instrução para facilitar escrita
            if (linha != null) {
                linhaDouble = Double.parseDouble(linha);
            } else {
                rafi.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new double[] {linhaDouble, offset};
    }

    /*public double proximaLinha() {
        double linhaDouble = -1;
        try {
            String linha = entradaDeDados.readLine(); //guardar linha original na instrução para facilitar escrita
            if (linha != null) {
                linhaDouble = Double.parseDouble(linha);
            } else {
                entradaDeDados.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linhaDouble;
    }*/
}
