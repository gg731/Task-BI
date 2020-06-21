public class Generator {
    public static void main(String[] args) throws Exception {
        String pathXml = args[0];
        String dataTsv = args[1];
        String exampleReport = args[2];

        Config config = new Config(pathXml);  //        "settings.xml"
        Data data = new Data(dataTsv);    //        "source-data.tsv"

        Table table = new Table(config, data.getTsvDataList());
        table.createTable();
        table.printResult(exampleReport);   //        "example-report.txt"


//        Вывод таблицы в консоль
//        Config configС = new Config("settings.xml");
//        Data dataС = new Data("source-data.tsv");
//
//        Table tableС = new Table(config, data.getTsvDataList());
//        table.createTable();
//        table.printResult("example-report.txt");
    }
}
