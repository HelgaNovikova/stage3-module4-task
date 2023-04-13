package com.mjc.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {

      //  ApplicationContext context = new AnnotationConfigApplicationContext("com.mjc.school");
       // AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        SpringApplication.run(Main.class, args);
//        Menu menu = context.getBean(Menu.class);
//        menu.showMenu();
//        Scanner sc = new Scanner(System.in);
//        int userChoice = sc.nextInt();
//        while (userChoice != 0) {
//            menu.getMenuItemById(userChoice).run(sc);
//            userChoice = sc.nextInt();
//        }
    }
}
