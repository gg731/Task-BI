import java.util.ArrayList;
import java.util.List;

public class Row {
    private Config config;
    private List<String> columnData;
    private List<String> rowPattern;
    private int height;
    private int[] from;
    private List<String> filledRow;


    public Row(List<String> columnData, Config config) throws Exception {
        this.config = config;
        this.columnData = columnData;
        initFrom();
        countMaxRowHeight();
        createRowPattern();
    }

    public int getHeight() {
        return height;
    }


    private void createRowPattern() throws Exception {
        List<String> patternForRow = new ArrayList<>();
        for (int i = 0; i < getHeight(); i++) {
            patternForRow.add(createPattern());
        }
        this.rowPattern = patternForRow;
    }

    private String createPattern() throws Exception {
        StringBuilder str = new StringBuilder();
        str.append("| ");
        for (Integer c : this.config.getColumnsWidth()) {
            str.append(String.format("%" + c + "s", ""));
            str.append(" | ");
        }
        String patternResult = str.deleteCharAt(str.length() - 1).toString();
        return patternResult + System.lineSeparator();
    }


    private void initFrom() throws Exception {
        int[] from = new int[this.config.getColumnsWidth().size()];
        from[0] = 2;
        for (int i = 1; i < this.config.getColumnsWidth().size(); i++) {
            from[i] = from[i - 1] + this.config.getColumnsWidth().get(i - 1) + 3;
        }
        this.from = from;
    }

    private void countMaxRowHeight() throws Exception {
        int maxHeight = 0;
        for (int i = 0; i < this.columnData.size(); i++) {
            Cell cell = new Cell(this.config.getColumnsWidth().get(i), this.columnData.get(i));
            int result = cell.getHeight();
            if (maxHeight < result) {
                maxHeight = result;
            }
        }
        this.height = maxHeight;
    }

    //Возвращает лист заполненных строк
    public List<String> getFilledRow() throws Exception {
        for (int i = 0; i < columnData.size(); i++) {
            Cell cell = new Cell(this.config.getColumnsWidth().get(i), from[i], columnData.get(i), rowPattern);
            this.filledRow = cell.toFill(columnData.get(i).toCharArray());
        }
        return this.filledRow;
    }

    public String getNumber(){
        return this.columnData.get(0);
    }
}
