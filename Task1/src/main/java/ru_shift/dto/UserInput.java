package ru_shift.dto;

import java.util.Scanner;

public class UserInput {
    private Integer userNumber;
    private boolean valid = true;

    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите размер таблицы: ");
        try {
            userNumber = sc.nextInt();
        }catch (Exception e) {
            System.out.println("Можно вводить только integer , будет взято значение по умолчанию --> 10");
            userNumber = 10;
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
