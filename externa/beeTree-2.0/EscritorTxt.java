import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

public class EscritorTxt {

    String pathname;

    public  EscritorTxt(String pathname) {
        this.pathname = pathname;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    public void write(ArrayList<Double> result, String filename/*int n*/){
        try {
            filename = filename.replace(".txt", "");
            File file = new File(pathname + filename + ".txt");
            
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            StringBuilder str = new StringBuilder();
            for (double d : result) {
                str.append(d+"\n");
            }
            String w = str.toString();
            bufferedWriter.write(w, 0, w.length() - 1);
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
