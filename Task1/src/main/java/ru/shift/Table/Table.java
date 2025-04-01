package ru.shift.Table;

public class Table {
    private final int size;
    private final int maxValueLength;
    private final int firstColumnLength;

    public Table(int size) {
        this.size = size;
        this.maxValueLength = String.valueOf(size * size).length();
        this.firstColumnLength = String.valueOf(size).length();
    }

    public void createTable() {
        StringBuilder tableBuilder = new StringBuilder();
        String line = createLine();

        //Создание заголовка таблицы
        tableBuilder.append(createColumn("", firstColumnLength, true));
        for (int i = 1; i <= size; i++) {
            tableBuilder.append(createColumn(String.valueOf(i), maxValueLength, i != size));
        }

        System.out.println(tableBuilder);
        tableBuilder.setLength(0);

        // Создание тела таблицы
        for (int i = 1; i <= size; i++) {
            System.out.println(line);

            tableBuilder.append(createColumn(String.valueOf(i), firstColumnLength, true));

            for (int j = 1; j <= size; j++) {
                tableBuilder.append(createColumn(String.valueOf(i * j), maxValueLength, j != size));
            }

            System.out.println(tableBuilder);
            tableBuilder.setLength(0);
        }
    }

    private String createLine() {
        return "-".repeat(firstColumnLength) + "+" +
                ("-".repeat(maxValueLength) + "+").repeat(size - 1) +
                "-".repeat(maxValueLength);
    }

    private String createColumn(String content,
                                int width,
                                boolean addSeparator) {

        return String.format("%" + width + "s" + (addSeparator ? "|" : ""), content);

    }
}