import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Data {
    private String pathTsv;

    public Data(String pathTsv) {
        this.pathTsv = pathTsv;
    }


    private List<String> getTsvData() throws IOException {
        BufferedReader tsvReader = new BufferedReader(new InputStreamReader(new FileInputStream(pathTsv), "UTF-16"));
        List<String> tsvList = new ArrayList<>();
        String line;
        while ((line = tsvReader.readLine()) != null) tsvList.add(line);

        return tsvList;
    }

    //Разделяет по табуляции и сохраняет все строки из source-data.tsv
    public List<List<String>> getTsvDataList() throws IOException {
        List<List<String>> tsvDataList = new ArrayList<>();
        for (String t : getTsvData()) {
            List<String> s = Arrays.asList(t.split("\\t"));
            tsvDataList.add(s);
        }
        return tsvDataList;
    }
}
