package ru_shift.Table;

public class Table {
    private final int size;
    private final int maxValueLength;
    private final int firstColumnLength;

    public Table(int size) {
        this.size = size;
        this.maxValueLength = String.valueOf(size * size).length();
        this.firstColumnLength = String.valueOf(size).length();
    }

    public String createTable() {
        StringBuilder tableBuilder = new StringBuilder();

        // Создание первой строки таблицы
        // Пустая клетка в начале
        tableBuilder.append(
                createColumn("",
                        firstColumnLength,
                        true));

        for (int i = 1; i <= size; i++) {
            tableBuilder.append(
                    createColumn(String.valueOf(i),
                            maxValueLength,
                            i != size)
            );
        }
        tableBuilder.append("\n");

        // Создание тела таблицы
        String line = createLine();
        for (int i = 1; i <= size; i++) {
            tableBuilder.append(line).append("\n");

            tableBuilder.append(
                    createColumn(String.valueOf(i),
                            firstColumnLength,
                            true)
            );

            for (int j = 1; j <= size; j++) {
                tableBuilder.append(
                        createColumn(String.valueOf(i * j),
                                    maxValueLength,
                                j != size)
                );
            }
            tableBuilder.append("\n");
        }
        tableBuilder.append(line);

        return tableBuilder.toString();
    }

    /**
     * <p>
     * Пустая клетка в начале таблицы: {@code "-".repeat(firstColumnLength) + "+"}.
     * </p>
     * <p>
     * Основная линия: {@code ("-".repeat(maxValueLength) + "+").repeat(size - 1)}.
     * </p>
     * <p>
     * Последняя ячейка: {@code "-".repeat(maxValueLength)}.
     * </p>
     * <p>
     * Я использую {@code size -1} , что бы отдельно добавить последнюю ячейку , которая содержит только {@code ---}
     * </p>
     */
    private String createLine() {
        return "-".repeat(firstColumnLength) + "+" +
                ("-".repeat(maxValueLength) + "+").repeat(size -1) +
                "-".repeat(maxValueLength);
    }

    /**
     * @param content      Содержимое ячейки.
     * @param width        Ширина ячейки.
     * @param addSeparator Если true, добавляет символ "|" в конце ячейки.
     */
    private String createColumn(String content,
                                int width,
                                boolean addSeparator) {

        return String.format("%" + width + "s" + (addSeparator ? "|" : ""), content);

    }
}