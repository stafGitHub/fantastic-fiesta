package ru.shift.dto;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput {
    private Integer userNumber;
    private boolean valid = true;

    {
       init();
    }

    public void init(){
        System.out.print("Введите размер таблицы: ");
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                userNumber = sc.nextInt();
                break;
            }catch (InputMismatchException e) {
                System.out.println("Можно вводить только integer , Введите число заново");
                sc.nextLine();
            }
        }
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Integer getUserNumber() {
        return userNumber;
    }
}
