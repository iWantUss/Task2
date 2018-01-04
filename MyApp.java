import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

interface DataConnection {

    int loadDatas(int sum) throws Exception;

    void saveData(int year, int qq) throws IOException;
}

public class MyApp implements DataConnection {



    public static class MyAppFactory {

        public static MyApp create(String y) {
            return new MyApp(y);
        }
    }

    public MyApp(String y) {
        this.year = y;
    }
    private final String year;
    private static int COUNT = 0;
    private static int COUNT1 = 0;
    protected static int startYear = 1990;
    protected static int endYear = 2020;
    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            System.out.println("app v.1.13");
            for (int i = startYear; i < endYear; i++) {
                int sum = 0;
                COUNT = 0;
                COUNT1 = 0;
                String y = i + "";
                sum = new MyAppFactory().create(y).loadDatas(sum);
                double qq = sum > 0 ? (double) sum / (double) COUNT : 0;
                if (qq > 0) {
                    System.out.println(i + " " + qq);
                }
                new /*new*/ MyAppFactory().create(y).saveData(i, (int) qq);
            }
            System.out.println("gotovo");
        } catch (Exception e) {
            System.out.println("oshibka!");
        }
    }

    @Override
    public int loadDatas(int sum) throws Exception {
        
        String[] sourceList = Files.readAllLines(Paths.get("1.txt")).toArray(new String[0]);
        
        for (String line : sourceList) {
            
            String[] arrayNumbers = line.split("    ");
            if (arrayNumbers[2].contains(this.year)) {
                sum += Integer.parseInt(arrayNumbers[3]);
            }
            
            COUNT++;
        }
        return sum;
    }

    @Override
    public void saveData(int year, int qq) throws IOException {
                
        String resultLine = COUNT1 + "    " + year + "    " + qq + "\n";
        Files.write(Paths.get("statistika.txt"), resultLine.getBytes(),
                    StandardOpenOption.APPEND,
                    StandardOpenOption.CREATE);
        COUNT1++;
    }
}
