import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class Cell {
    private int width;
    private int from;
    private String data;
    private List<String> pattern;
    private int with = 0;
    private int height = 0;


    public Cell(int width, String data) {
        this.width = width;
        this.data = data;
    }

    public Cell(int width, int from, String data, List<String> pattern) {
        this.width = width;
        this.from = from;
        this.data = data;
        this.pattern = pattern;
    }


    //Считает высоту ячейки
    public int getHeight() {
        if (this.data.matches("\\w")) {
            this.height = (int) Math.ceil(this.data.length() / (double) this.width);
            return this.height;
        } else {
            char[] strToChar = this.data.toCharArray();
            this.height = countRowsToCreate(strToChar);
        }
        return this.height;
    }

    public int countRowsToCreate(char[] toCheck) {
        if (toCheck.length > width) {
            if (Character.isWhitespace(toCheck[width])) {
                StringBuilder toCut = new StringBuilder(String.valueOf(toCheck));
                String residue = toCut.delete(0, this.width).toString();
                this.height++;
                char[] next = residue.trim().toCharArray();
                if (next.length > 0) {
                    countRowsToCreate(next);
                }
                return this.height;
            }
        }
        if (toCheck.length <= this.width) {
            this.height++;
            return this.height;
        } else {
            int iter = this.width;
            int cutHere = this.width;
            for (int i = 0; i < iter; i++) {
                if (!(Character.isLetterOrDigit(toCheck[i]))) {
                    cutHere = i + 1;
                }
            }
            StringBuilder toCut = new StringBuilder(String.valueOf(toCheck));
            String residue = toCut.delete(0, cutHere).toString();
            this.height++;
            char[] next = residue.trim().toCharArray();
            if (next.length > 0) {
                countRowsToCreate(next);
            }
        }
        return this.height;
    }


    //Заполняет паттерн данными
    public List<String> toFill(char[] toCheck) {
        if (toCheck.length > this.width) {
            if (Character.isWhitespace(toCheck[this.width])) {
                StringBuilder sb = new StringBuilder(this.pattern.get(this.with));
                StringBuilder toCut = new StringBuilder(String.valueOf(toCheck));
                String cuttedPart = toCut.substring(0, this.width);
                sb.replace(this.from, this.from + this.width, cuttedPart);
                String residue = toCut.delete(0, this.width).toString();
                String res = sb.toString();
                this.pattern.set(this.with, res);
                char[] next = residue.trim().toCharArray();
                this.with++;
                if (next.length > 0) {
                    return toFill(next);
                }
            }
        }
        if (toCheck.length <= this.width) {
            StringBuilder sb = new StringBuilder(this.pattern.get(this.with));
            StringBuilder toCut = new StringBuilder(String.valueOf(toCheck));
            String cuttedPart = toCut.substring(0, toCheck.length);
            sb.replace(this.from, this.from + cuttedPart.length(), cuttedPart);
            String res = sb.toString();
            this.pattern.set(this.with, res);
            return this.pattern;
        } else {
            int iter = this.width;
            int cutHere = this.width;
            for (int i = 0; i < iter; i++) {
                if (!(Character.isLetterOrDigit(toCheck[i]))) {
                    cutHere = i + 1;
                }
            }
            StringBuilder sb = new StringBuilder(this.pattern.get(this.with));
            StringBuilder toCut = new StringBuilder(String.valueOf(toCheck));
            String cuttedPart = toCut.substring(0, cutHere);
            String residue = toCut.delete(0, cutHere).toString();
            sb.replace(this.from, this.from + cuttedPart.length(), cuttedPart);
            String res = sb.toString();
            this.pattern.set(this.with, res);
            char[] next = residue.trim().toCharArray();
            this.with++;
            if (next.length > 0) {
                return toFill(next);
            }
        }
        return this.pattern;
    }



}
