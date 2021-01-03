package test;
import java.awt.*;
import java.util.Scanner;

public class main {
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
//                test.DetailView detailView = new test.DetailView(new DetailViewController());
//                detailView.initializeComponents();
//                detailView.setViewVisible();
                //anh thu
                Scanner scanner = new Scanner(System.in);
                System.out.printf("Enter table: ");
                String tableName = scanner.nextLine();
                System.out.println(tableName);
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        DetailView detailView = new DetailView(tableName);
                        detailView.setVisible(true);
                    }
                });
            }
        });
    }
}
