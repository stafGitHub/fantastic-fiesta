import ch.qos.logback.core.testUtil.RandomUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru_shift.args.ApplicationArgumentParser;
import ru_shift.dto.ApplicationData;
import ru_shift.dto.figure.FigureNameMap;
import ru_shift.dto.figure.specific.figure.Circle;
import ru_shift.dto.figure.specific.figure.Rectangle;
import ru_shift.dto.figure.specific.figure.Triangle;
import ru_shift.exception.NotFileReadPath;
import ru_shift.file.SystemFileReader;
import ru_shift.repository.application.args.repo.ApplicationArgsRepoController;
import ru_shift.repository.application.args.repo.operations.ReadFilePathOperation;
import ru_shift.repository.application.args.repo.operations.SaveFilePathOperation;
import ru_shift.repository.conservation.strategy.specific.strategy.ConsoleSaveStrategy;
import ru_shift.repository.conservation.strategy.specific.strategy.CurrentDirectorySaveFileStrategy;
import ru_shift.repository.conservation.strategy.specific.strategy.UserFileSaveStrategy;
import ru_shift.repository.fabric.FigureFactory;
import ru_shift.repository.fabric.SaveStrategyFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.*;

public class Task2Test {
    private static final Logger log = LoggerFactory.getLogger(Task2Test.class);
    // Создание команд для cоздания dto
    static ApplicationArgsRepoController applicationArgsRepoController = new ApplicationArgsRepoController();
    static ReadFilePathOperation readFilePathOperation = new ReadFilePathOperation();
    static SaveFilePathOperation saveFilePathOperation = new SaveFilePathOperation();

    // Создание map с именами фигур
    static FigureNameMap figureNameMap = new FigureNameMap();

    //Создание объектов для работы с файлом
    static FigureFactory figureFactory = new FigureFactory();
    static ApplicationArgumentParser applicationArgumentParser = new ApplicationArgumentParser();
    static SystemFileReader systemFileReader = new SystemFileReader();

    // Тестовые файлы
    @TempDir
    static Path tempDir;
    static Path testReadFile;
    static Path testSaveFile;

    @BeforeAll
    static void init() throws IOException {
        figureNameMap.addFigure(Rectangle.class);
        figureNameMap.addFigure(Triangle.class);
        figureNameMap.addFigure(Circle.class);
        applicationArgsRepoController.addOperation(readFilePathOperation, saveFilePathOperation);

        testReadFile = tempDir.resolve("test_input.txt"+ RandomUtil.getPositiveInt());
        Files.createFile(testReadFile);
        testSaveFile = tempDir.resolve("test_output.txt"+ RandomUtil.getPositiveInt());
        Files.createFile(testSaveFile);
    }

    @AfterEach
    void cleanUp() throws IOException {
        testReadFile = tempDir.resolve("test_input.txt"+ RandomUtil.getPositiveInt());
        Files.createFile(testReadFile);
        testSaveFile = tempDir.resolve("test_output.txt"+ RandomUtil.getPositiveInt());
        Files.createFile(testSaveFile);
    }

    @Test
    void parseArgsReadFilePathAndSaveFilePath() {
        String[] args = {"-r::s", "-s::s"};
        var parseArgs = applicationArgumentParser.argumentConfigure(args);

        assertAll(
                () -> assertTrue(parseArgs.containsKey("r")),
                () -> assertTrue(parseArgs.containsKey("s"))
        );
    }

    @Test
    void parseArgsErrorSaveFilePathAndErrorReadFilePath() {
        String[] args = {"-r::", "-s::"};
        assertThrows(IndexOutOfBoundsException.class, () -> applicationArgumentParser.argumentConfigure(args));
    }

    @Test
    void notReadFilePath() {
        String[] args = {"-s::s"};
        assertThrows(NotFileReadPath.class, () -> applicationArgumentParser.argumentConfigure(args));
    }

    @Test
    void applicationDataСontainsReadFilePathAndNotSaveFilePath() {
        String readFilePath = "-r::"+testReadFile.toString();
        String[] args = {readFilePath};

        var parseConsoleArgs = applicationArgumentParser.argumentConfigure(args);
        var builder = ApplicationData.builder();

        var applicationData = applicationArgsRepoController.operationsExecute(parseConsoleArgs, builder);

        assertAll(
                () -> assertEquals(testReadFile.toString(), applicationData.getReadFilePath()),
                () -> assertEquals("console", applicationData.getSaveFilePath())
        );


    }

    @Test
    void applicationDataContainsCurrentDirectory() {
        String readFilePath = "-r::"+testReadFile.toString();
        String saveFilePath = "-s::errorSaveFilePath";
        String[] args = {readFilePath, saveFilePath};
        var parseConsoleArgs = applicationArgumentParser.argumentConfigure(args);

        var builder = ApplicationData.builder();

        var applicationData = applicationArgsRepoController.operationsExecute(parseConsoleArgs, builder);

        assertAll(
                () -> assertEquals(testReadFile.toString(), applicationData.getReadFilePath()),
                () -> assertEquals(".", applicationData.getSaveFilePath())
        );

    }

    @Test
    void applicationDataContainsSaveFilePath() {
        String readFilePath = "-r::"+testReadFile.toString();
        String saveFilePath = "-s::"+testSaveFile.toString();
        String[] args = {readFilePath, saveFilePath};
        var parseConsoleArgs = applicationArgumentParser.argumentConfigure(args);

        var builder = ApplicationData.builder();

        var applicationData = applicationArgsRepoController.operationsExecute(parseConsoleArgs, builder);

        assertAll(
                () -> assertEquals(testReadFile.toString(), applicationData.getReadFilePath()),
                () -> assertEquals(testSaveFile.toString(), applicationData.getSaveFilePath())
        );

    }

    @Test
    void readingFileByReadFilePath() {
        String readFilePath = "-r::"+testReadFile.toString();
        String[] args = {readFilePath};
        var parseConsoleArgs = applicationArgumentParser.argumentConfigure(args);

        var builder = ApplicationData.builder();

        var applicationData = applicationArgsRepoController.operationsExecute(parseConsoleArgs, builder);

        assertEquals(testReadFile.toString(), applicationData.getReadFilePath());
    }

    @Test
    void checkCreateFigureCircle() throws IOException {
        writeFileFigureCircle();
        String readFilePath = "-r::"+testReadFile.toString();
        String[] args = {readFilePath};
        var parseConsoleArgs = applicationArgumentParser.argumentConfigure(args);

        var builder = ApplicationData.builder();

        var applicationData = applicationArgsRepoController.operationsExecute(parseConsoleArgs, builder);
        var bufferedReader = systemFileReader.readComputerFile(applicationData.getReadFilePath());

        bufferedReader.readLine();
        var figure = figureFactory.createFigure(figureNameMap.getFigureNameMap().get(Circle.class.getSimpleName().toUpperCase()), bufferedReader);

        bufferedReader.close();

        assertEquals(Circle.class, figure.getClass());

    }

    @Test
    void checkCreateFigureRectangle() throws IOException {
        writeFileFigureRectangle();
        String readFilePath = "-r::"+testReadFile.toString();
        String[] args = {readFilePath};
        var parseConsoleArgs = applicationArgumentParser.argumentConfigure(args);

        var builder = ApplicationData.builder();

        var applicationData = applicationArgsRepoController.operationsExecute(parseConsoleArgs, builder);
        var bufferedReader = systemFileReader.readComputerFile(applicationData.getReadFilePath());

        bufferedReader.readLine();
        var figure = figureFactory.createFigure(figureNameMap.getFigureNameMap().get(Rectangle.class.getSimpleName().toUpperCase()), bufferedReader);

        bufferedReader.close();

        assertEquals(Rectangle.class, figure.getClass());

    }

    @Test
    void checkCreateFigureTriangle() throws IOException {
        writeFileFigureTriangle();
        String readFilePath = "-r::"+testReadFile.toString();
        String[] args = {readFilePath};
        var parseConsoleArgs = applicationArgumentParser.argumentConfigure(args);

        var builder = ApplicationData.builder();

        var applicationData = applicationArgsRepoController.operationsExecute(parseConsoleArgs, builder);
        var bufferedReader = systemFileReader.readComputerFile(applicationData.getReadFilePath());

        bufferedReader.readLine();
        var figure = figureFactory.createFigure(figureNameMap.getFigureNameMap().get(Triangle.class.getSimpleName().toUpperCase()), bufferedReader);

        bufferedReader.close();

        assertEquals(Triangle.class, figure.getClass());

    }

    @Test
    void checkCreateFigureAll() throws IOException {
        writeFileFigureTriangle();
        writeFileFigureRectangle();
        writeFileFigureCircle();

        String readFilePath = "-r::"+testReadFile.toString();
        String[] args = {readFilePath};
        var parseConsoleArgs = applicationArgumentParser.argumentConfigure(args);

        var builder = ApplicationData.builder();

        var applicationData = applicationArgsRepoController.operationsExecute(parseConsoleArgs, builder);
        var bufferedReader = systemFileReader.readComputerFile(applicationData.getReadFilePath());

        bufferedReader.readLine();
        var triangle = figureFactory.createFigure(figureNameMap.getFigureNameMap().get(Triangle.class.getSimpleName().toUpperCase()), bufferedReader);

        bufferedReader.readLine();
        var rectangle = figureFactory.createFigure(figureNameMap.getFigureNameMap().get(Rectangle.class.getSimpleName().toUpperCase()), bufferedReader);

        bufferedReader.readLine();
        var circle = figureFactory.createFigure(figureNameMap.getFigureNameMap().get(Circle.class.getSimpleName().toUpperCase()), bufferedReader);


        bufferedReader.close();

        assertAll(
                ()->assertEquals(Triangle.class,triangle.getClass()),
                ()->assertEquals(Rectangle.class,rectangle.getClass()),
                ()->assertEquals(Circle.class,circle.getClass())
        );

    }

    @Test
    void checkCreateConsoleSaveStrategy(){
        String readFilePath = "-r::"+testReadFile.toString();
        String[] args = {readFilePath};
        var parseConsoleArgs = applicationArgumentParser.argumentConfigure(args);

        var builder = ApplicationData.builder();

        var applicationData = applicationArgsRepoController.operationsExecute(parseConsoleArgs, builder);

        var saveStrategyFactory = new SaveStrategyFactory(applicationData);
        var strategy = saveStrategyFactory.createStrategy(applicationData.getSaveFilePath());

        assertEquals(ConsoleSaveStrategy.class, strategy.getClass());

    }

    @Test
    void checkCreateFileSaveStrategy() {
        String readFilePath = "-r::"+testReadFile.toString();
        String saveFilePath = "-s::"+testSaveFile.toString();
        String[] args = {readFilePath, saveFilePath};
        var parseConsoleArgs = applicationArgumentParser.argumentConfigure(args);

        var builder = ApplicationData.builder();

        var applicationData = applicationArgsRepoController.operationsExecute(parseConsoleArgs, builder);

        var saveStrategyFactory = new SaveStrategyFactory(applicationData);
        var strategy = saveStrategyFactory.createStrategy(applicationData.getSaveFilePath());

        assertEquals(UserFileSaveStrategy.class, strategy.getClass());
    }

    @Test
    void checkCreateCurrentSaveStrategy() {
        String readFilePath = "-r::"+testReadFile.toString();
        String saveFilePath = "-s::errorFilePath";
        String[] args = {readFilePath, saveFilePath};
        var parseConsoleArgs = applicationArgumentParser.argumentConfigure(args);

        var builder = ApplicationData.builder();

        var applicationData = applicationArgsRepoController.operationsExecute(parseConsoleArgs, builder);
        var saveStrategyFactory = new SaveStrategyFactory(applicationData);
        var strategy = saveStrategyFactory.createStrategy(applicationData.getSaveFilePath());

        assertEquals(CurrentDirectorySaveFileStrategy.class, strategy.getClass());
    }

    @Test
    void checkExecuteCurrentDirectorySaveStrategy() throws IOException {
        var circle = new Circle(10);

        String readFilePath = "-r::"+testReadFile.toString();
        String saveFilePath = "-s::errorFilePath";
        String[] args = {readFilePath, saveFilePath};
        var parseConsoleArgs = applicationArgumentParser.argumentConfigure(args);
        var builder = ApplicationData.builder();
        var applicationData = applicationArgsRepoController.operationsExecute(parseConsoleArgs, builder);

        var currentDirectorySaveFile = new CurrentDirectorySaveFileStrategy(applicationData);

        currentDirectorySaveFile.save(circle);

        Path savePath = Paths.get(applicationData.getSaveFilePath()).toAbsolutePath().normalize();
        savePath = savePath.resolve(CurrentDirectorySaveFileStrategy.FILE_NAME);


        var lineFile = String.join(" ",Files.readAllLines(savePath));
        var lineApplication = String.join(" ",circle.getDescription().split("\n"));

        assertEquals(lineApplication,lineFile);
    }

    @Test
    void checkExecuteFileSaveStrategy() throws IOException {
        String readFilePath = "-r::"+testReadFile.toString();
        String saveFilePath = "-s::"+testSaveFile.toString();
        String[] args = {readFilePath, saveFilePath};
        var parseConsoleArgs = applicationArgumentParser.argumentConfigure(args);
        var builder = ApplicationData.builder();
        var applicationData = applicationArgsRepoController.operationsExecute(parseConsoleArgs, builder);

        var circle = new Circle(10);

        var userFileSaveStrategy = new UserFileSaveStrategy(applicationData);
        userFileSaveStrategy.save(circle);

        var lineFile = String.join(" ",Files.readAllLines(Path.of(applicationData.getSaveFilePath())));
        var lineApplication = String.join(" ",circle.getDescription().split("\n"));

        assertEquals(lineFile, lineApplication);



    }














    private void writeFileFigureCircle(){
        try {
            Files.writeString(testReadFile, "CIRCLE\n5.22\n",StandardOpenOption.APPEND);
        }catch (IOException e){
            log.error("writeFileFigureCircle ошибка записи в файл чтения {} ","CIRCLE");
        }
    }

    private void writeFileFigureRectangle(){
        try {
            Files.writeString(testReadFile, "RECTANGLE\n22.5 10.5\n", StandardOpenOption.APPEND);
        }catch (IOException e){
            log.error("writeFileFigureRectangle ошибка записи в файл чтения {} ","RECTANGLE");
        }
    }

    private void writeFileFigureTriangle(){
        try {
            Files.writeString(testReadFile, "TRIANGLE\n100 100 100\n",StandardOpenOption.APPEND);
        }catch (IOException e){
            log.error("writeFileFigureTriangle ошибка записи в файл чтения {} ","RECTANGLE");
        }
    }

}
