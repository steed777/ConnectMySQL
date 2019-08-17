
import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

import java.util.*;

public class Main {
    private static String titleBook;
    private static boolean exit = true;

    public static final String url = "jdbc:mysql://localhost:3306/books?user=root&password=alberto&ssl=true";

   public static Map<String, String> createBooks(){
        Map<String, String> books = new HashMap<>();
       books.put("Руслан и Людмила", "Александр Пушкин");
       books.put("Философия java", "Брюс Эккель");
       books.put("Java 8 руководство для начинающих", "Герберт Шилдт");
       books.put("Князь Владмир", "Юрий Никитин");
       books.put("Князь Рус", "Юрий Никитин");
       return books;
   }

    @Override
    public String toString() {
        return "Библиотека книг:\n" +
                "Руслан и Людмила\n" +
                "Философия java\n" +
                "Java 8 руководство для начинающих\n" +
                "Князь Владимир\n" +
                "Князь Рус";
    }

    public static void request() throws IOException{

       BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            titleBook = reader.readLine();
            if (titleBook.equals("стоп")){
                exit = false;
            }
           else if (!createBooks().containsKey(titleBook)) {
               while (!createBooks().containsKey(titleBook)) {
                    System.out.println("Такой книги нет в библиотеке, пожалуйста вводите только те названия, которые есть в библиотеке.");
                    titleBook = reader.readLine();
                }
            }
        }
        public static void main(String[] args) throws Exception{
       Main otherMain = new Main();

       try {
           Connection conn = DriverManager.getConnection(url);
           System.out.println(otherMain.toString());
           System.out.println("Введите название книги, соблюдая регистр букв(название книги и автор будут помещены в SQL таблицу), ");
           System.out.println("для выхода введите стоп:");
           while (exit) {
               request();
               if (exit == false) break;
               PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO books (books_title, books_author) Values (?, ?)");
               preparedStatement.setString(1, titleBook);
               preparedStatement.setString(2, createBooks().get(titleBook));
               preparedStatement.execute();
           }
       } catch (SQLException x) {
           System.out.println("net podklucheniya!!");
       }
   }
}