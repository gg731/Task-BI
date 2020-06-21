import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Config {
    private int widthPage;
    private int heightPage;
    private String pathXml;
    private List<Column> columns;


    public Config(String pathXml) {
        this.pathXml = pathXml;
        parseHeightAndWidth();
        getConfigColumns();
    }


    public int getHeight() {
        return heightPage;
    }

    public int getWidthPage() {
        return widthPage;
    }

    public List<Column> getColumns() {
        return columns;
    }

    //     Сохраняет ширину и длину страницы из xml
    public void parseHeightAndWidth() {
        Node node = getDocument().getElementsByTagName("page").item(0);
        Element element = (Element) node.getChildNodes();
        this.widthPage = Integer.valueOf(element.getElementsByTagName("width").item(0).getTextContent());
        this.heightPage = Integer.valueOf(element.getElementsByTagName("height").item(0).getTextContent());
    }


    // Возращает list колонок, имеющих имя и ширину
    public void getConfigColumns() {
        List<Column> columnList = new ArrayList<>();
        NodeList column = getDocument().getElementsByTagName("column");
        for (int i = 0; i < column.getLength(); i++) {
            Element values = (Element) column.item(i).getChildNodes();
            String title = values.getElementsByTagName("title").item(0).getTextContent();
            int widthCol = Integer.valueOf(values.getElementsByTagName("width").item(0).getTextContent());
            columnList.add(new Column(title, widthCol));
        }
        this.columns = columnList;
    }


    private Document getDocument() {
        Document document = null;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            document = builder.parse(new File(this.pathXml));
        } catch (Exception e) {
            System.err.print("Ошибка обработки xml-файла");
            e.getMessage();
        }
        return document;
    }

    public List<Integer> getColumnsWidth() throws Exception {
        List<Integer> columnsWidth = new ArrayList<>();

        for (Column c : getColumns()) columnsWidth.add(c.getWidthCol());
        int widthSum = columnsWidth.stream().mapToInt(w -> w).sum() + (columnsWidth.size() * 3 + 1);
        if (widthSum > getWidthPage()) {
            throw new Exception("Ширина колонок больше ширины таблицы, измените конфигурации в xml");
        }

        return columnsWidth;
    }

    public List<String> getHeadColNames() {
        List<String> nameCols = new ArrayList<>();
        for (Column c : getColumns()) nameCols.add(c.getTitle());

        return nameCols;
    }
}

class Column {
    private String title;
    private int widthCol;

    public Column(String title, int width) {
        this.title = title;
        this.widthCol = width;
    }

    public String getTitle() {
        return title;
    }

    public int getWidthCol() {
        return widthCol;
    }
}