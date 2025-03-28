import ch.qos.logback.core.testUtil.RandomUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.args.Parser;
import ru.shift.factory.figure.FigureFactory;
import ru.shift.factory.figure.TriangleFactory;
import ru.shift.factory.output.WriterFactory;
import ru.shift.figures.specific.figures.Circle;
import ru.shift.figures.specific.figures.Rectangle;
import ru.shift.figures.specific.figures.Triangle;
import ru.shift.args.NotFileReadPath;
import ru.shift.file.SystemFileReader;
import ru.shift.output.specific.writer.ConsoleFigureWriter;
import ru.shift.output.specific.writer.UserFileFigureWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.*;

public class Task2Test {
    private static final Logger log = LoggerFactory.getLogger(Task2Test.class);
    static SystemFileReader systemFileReader = new SystemFileReader();

    // Тестовые файлы
    @TempDir
    static Path tempDir;
    static Path testReadFile;
    static Path testSaveFile;

    @BeforeAll
    static void init() throws IOException {
        testReadFile = tempDir.resolve("test_input.txt" + RandomUtil.getPositiveInt());
        Files.createFile(testReadFile);
        testSaveFile = tempDir.resolve("test_output.txt" + RandomUtil.getPositiveInt());
        Files.createFile(testSaveFile);
    }

    @AfterEach
    void cleanUp() throws IOException {
        testReadFile = tempDir.resolve("test_input.txt" + RandomUtil.getPositiveInt());
        Files.createFile(testReadFile);
        testSaveFile = tempDir.resolve("test_output.txt" + RandomUtil.getPositiveInt());
        Files.createFile(testSaveFile);
    }

    @Test
    void parseArgsReadFilePathAndSaveFilePath() {
        String[] args = {"-r::s", "-s::s"};
        var parseArgs = Parser.parseApplicationArgs(args);

        assertAll(
                () -> assertTrue(parseArgs.containsKey("r")),
                () -> assertTrue(parseArgs.containsKey("s"))
        );
    }

    @Test
    void parseArgsErrorSaveFilePathAndErrorReadFilePath() {
        String[] args = {"-r::", "-s::"};
        assertThrows(IndexOutOfBoundsException.class, () -> Parser.parseApplicationArgs(args));
    }

    @Test
    void notReadFilePath() {
        String[] args = {"-s::s"};
        assertThrows(NotFileReadPath.class, () -> Parser.parseApplicationArgs(args));
    }

    @Test
    void checkCreateFigureCircle() throws IOException {
        writeFileFigureCircle();
        String readFilePath = "-r::" + testReadFile.toString();
        String[] args = {readFilePath};
        var parseConsoleArgs = Parser.parseApplicationArgs(args);

        var bufferedReader = systemFileReader.readComputerFile(parseConsoleArgs.get("r"));
        try(bufferedReader) {
            var abstractFigure = FigureFactory.create(bufferedReader);
            assertEquals(Circle.class, abstractFigure.getClass());
        }


    }

    @Test
    void checkCreateFigureRectangle() throws IOException {
        writeFileFigureRectangle();
        String readFilePath = "-r::" + testReadFile.toString();
        String[] args = {readFilePath};
        var parseConsoleArgs = Parser.parseApplicationArgs(args);

        var bufferedReader = systemFileReader.readComputerFile(parseConsoleArgs.get("r"));
        try(bufferedReader) {
            var abstractFigure = FigureFactory.create(bufferedReader);
            assertEquals(Rectangle.class, abstractFigure.getClass());
        }
    }

    @Test
    void checkCreateFigureTriangle() throws IOException {
        writeFileFigureTriangle();
        String readFilePath = "-r::" + testReadFile.toString();
        String[] args = {readFilePath};
        var parseConsoleArgs = Parser.parseApplicationArgs(args);

        var bufferedReader = systemFileReader.readComputerFile(parseConsoleArgs.get("r"));
        try(bufferedReader) {
            var abstractFigure = FigureFactory.create(bufferedReader);
            assertEquals(Triangle.class, abstractFigure.getClass());
        }
    }

    @Test
    void checkCreateConsoleSaveStrategy() {
        String readFilePath = "-r::" + testReadFile.toString();
        String[] args = {readFilePath};
        var parseConsoleArgs = Parser.parseApplicationArgs(args);

        var abstractFigureFigureWriter = WriterFactory.create(parseConsoleArgs);

        assertEquals(ConsoleFigureWriter.class, abstractFigureFigureWriter.getClass());

    }

    @Test
    void checkCreateFileSaveStrategy() {
        String readFilePath = "-r::" + testReadFile.toString();
        String saveFilePath = "-s::" + testSaveFile.toString();
        String[] args = {readFilePath , saveFilePath};
        var parseConsoleArgs = Parser.parseApplicationArgs(args);

        var abstractFigureFigureWriter = WriterFactory.create(parseConsoleArgs);

        assertEquals(UserFileFigureWriter.class, abstractFigureFigureWriter.getClass());

    }
    @Test
    void checkExecuteFileSaveStrategy() throws IOException {
        String readFilePath = "-r::" + testReadFile.toString();
        String saveFilePath = "-s::" + testSaveFile.toString();
        String[] args = {readFilePath, saveFilePath};
        var parseConsoleArgs = Parser.parseApplicationArgs(args);
        var circle = new Circle(10);
        var abstractFigureFigureWriter = WriterFactory.create(parseConsoleArgs);

        abstractFigureFigureWriter.write(circle,parseConsoleArgs);

        var lineFile = String.join(" ", Files.readAllLines(Path.of(parseConsoleArgs.get("s"))));
        var lineApplication = String.join(" ", circle.getDescription().split("\n"));

        assertEquals(lineFile, lineApplication);
    }

    @Test
    void checkIllegalArgumentExceptionFigure() throws IOException {
        writeErrorFileFigureTriangle();
        String readFilePath = "-r::" + testReadFile.toString();
        String saveFilePath = "-s::" + testSaveFile.toString();
        String[] args = {readFilePath, saveFilePath};

        var parseConsoleArgs = Parser.parseApplicationArgs(args);
        var fileBr = systemFileReader.readComputerFile(parseConsoleArgs.get("r"));
        var triangleFactory = new TriangleFactory();
        try(fileBr) {
            fileBr.readLine();
            assertThrows(IllegalArgumentException.class,()-> triangleFactory.read(fileBr));
        }


    }


    private void writeFileFigureCircle() {
        try {
            Files.writeString(testReadFile, "CIRCLE\n5.22\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("writeFileFigureCircle ошибка записи в файл чтения {} ", "CIRCLE");
        }
    }

    private void writeFileFigureRectangle() {
        try {
            Files.writeString(testReadFile, "RECTANGLE\n22.5 10.5\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("writeFileFigureRectangle ошибка записи в файл чтения {} ", "RECTANGLE");
        }
    }

    private void writeFileFigureTriangle() {
        try {
            Files.writeString(testReadFile, "TRIANGLE\n100 100 100\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("writeFileFigureTriangle ошибка записи в файл чтения {} ", "TRIANGLE");
        }
    }

    private void writeErrorFileFigureTriangle() {
        try {
            Files.writeString(testReadFile, "TRIANGLE\n100 100  \n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("writeErrorFileFigureTriangle ошибка записи в файл чтения {} ", "TRIANGLE");
        }
    }

}
