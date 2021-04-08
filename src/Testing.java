import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Testing {

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Введите url-ссылку");
        String urlStr = input.next();
        URL url = new URL(urlStr);
        try (InputStream reader = url.openStream();
             FileOutputStream writer = new FileOutputStream("newFile")) {
            byte[] buffer = new byte[reader.available()];
            reader.read(buffer, 0, buffer.length);
            writer.write(buffer, 0, buffer.length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        File file = new File("newFile");
        Scanner scanner = new Scanner(file);
        StringBuffer buffer = new StringBuffer();
        while (scanner.hasNextLine()) {
            buffer.append(scanner.nextLine());
        }
        String[] strings = buffer.toString().split("[\r\n\t [A-z]\\d,=:\"\\-*@$_«\\]\\[».—#'+?&|\\{\\}\\(\\)!/><;]");
        List<String> collect = Arrays.stream(strings)
                .map(String::strip)
                .filter(s -> !s.isEmpty() || !s.isBlank())
                .collect(Collectors.toList());
        System.out.println(collect);
        System.out.println("Количество слов в тексте:" + collect.size());
        Collections.sort(collect);
        String temp = "";
        int countWords = 0;
        for (String item : collect) {
            if (item.compareTo(temp) != 0) {
                if (temp.length() != 0)
                    System.out.println("слово : " + temp + ", количество = " + countWords);
                temp = item;
                countWords = 0;
            }
            countWords++;
        }
    }
}