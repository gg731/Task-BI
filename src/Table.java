import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<List<String>> tsvList;
    private Config config;
    private List<String> result = new ArrayList<>();


    public Table(Config config, List<List<String>> tsvList) {
        this.tsvList = tsvList;
        this.config = config;
    }

    private List<String> getResult() {
        return result;
    }



    public void createTable() throws Exception {
        List<String> header = createHeader();
        int counter = header.size();
        result.addAll(header);
        for (List<String> t : tsvList) {
            Row row = new Row(t, config);
            checkHeightRow(row.getHeight(), header.size());
            counter += row.getHeight() + 1;

            if (counter <= config.getHeight()) {
                rowsBreaker();
                result.addAll(row.getFilledRow());
            } else {
                pageBreaker();
                result.addAll(header);
                rowsBreaker();
                result.addAll(row.getFilledRow());
                counter = header.size() + row.getHeight() + 1;
            }
        }
    }

    private List<String> createHeader() throws Exception {
        Row header = new Row(this.config.getHeadColNames(), this.config);
        if (header.getHeight() > (this.config.getHeight() / 4)) {
            throw new Exception("Высотока заголовка слишком большая, измените конфигурации");
        } else return header.getFilledRow();
    }


    private void checkHeightRow(int heightRow, int heightHeader) throws Exception {
        if (heightHeader + (heightRow + 1) > config.getHeight()) {
            throw new Exception("Строка  не может быть добавлена, высота строки слишком большая");
        }
    }


    private void pageBreaker() {
        result.add("~ " + System.lineSeparator());
    }

    private void rowsBreaker() {
        result.add(String.format("%" + config.getWidthPage() + "s" + System.lineSeparator(), "")
                .replace(' ', '-'));
    }


    public void printResult(String path) throws IOException {
        Writer fos = null;
        BufferedWriter out = null;
        try {
           fos = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_16);

            for (String t : getResult()) {
                fos.append(t);
            }
            fos.flush();
        } catch (IOException e) {
            System.err.println("Ошибка записи в " + path);
            e.getMessage();
        } finally {
            fos.close();
        }
    }
}


