import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.StringTokenizer;

public class LeitorTxt {
    private String diretorioArquivo;
    BufferedReader entradaDeDados;
    private StringTokenizer strK;
    
    public LeitorTxt(String diretorioArquivo) throws FileNotFoundException {

        this.diretorioArquivo = diretorioArquivo;
        this.entradaDeDados = new BufferedReader(new FileReader(diretorioArquivo));

    }

    public double proximaLinha() {
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
    }
}
