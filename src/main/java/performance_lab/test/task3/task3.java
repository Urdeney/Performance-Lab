package performance_lab.test.task3;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * На вход в качестве аргументов программы поступают три пути к файлу (в
 * приложении к заданию находятся примеры этих файлов):
 * 
 * -- values.json содержит результаты прохождения тестов с уникальными id
 * -- tests.json содержит структуру для построения отчета на основе прошедших
 * тестов (вложенность может быть большей, чем в примере)
 * -- report.json - сюда записывается результат.
 * 
 * Напишите программу, которая формирует файл report.json с заполненными полями
 * value для структуры tests.json на основании values.json.
 * Структура report.json такая же, как у tests.json, только заполнены поля
 * “value”.
 * 
 * На вход программы передается три пути к файлу!
 */
public class task3 {

    public static void main(String[] args) {

        if (args.length != 3) {
            throw new IllegalArgumentException("Программа должна принимать только имена трех файлов");
        }

        try {
            ObjectMapper object_mapper = new ObjectMapper();

            HashMap<Integer, String> test_status = ParseJson_Values(args[0], object_mapper);

            JsonNode result_json = ParseJson_Tests(args[1], object_mapper, test_status);

            object_mapper.writerWithDefaultPrettyPrinter().writeValue(new File(args[2]), result_json); // запись
                                                                                                       // результата в
                                                                                                       // report.json
            System.out.println("Файл report.json перезаписан");
            
        } catch (NullPointerException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Функция для обработки файла с результатами прохождения тестов (values.json)
     */
    static HashMap<Integer, String> ParseJson_Values(String filename_path, ObjectMapper object_mapper)
            throws IOException {
        HashMap<Integer, String> res_list = new HashMap<>();
        File json_file = new File(filename_path);
        JsonNode array_node = object_mapper.readTree(json_file).at("/values");

        for (JsonNode single_node : array_node) {
            res_list.put(single_node.findValue("id").intValue(), single_node.findValue("value").textValue());
        }

        return res_list;
    }

    /**
     * Функция для обработки файла отчета (tests.json)
     */
    static JsonNode ParseJson_Tests(String filename_path, ObjectMapper object_mapper,
            HashMap<Integer, String> test_status) throws IOException {
        File json_file = new File(filename_path);
        JsonNode res_node = object_mapper.readTree(json_file);
        JsonNode parse_node = res_node.at("/tests");

        ParseJsonNode_Recur(parse_node, test_status);

        return res_node;

    }

    /**
     * Функция для рекурсивного обхода дерева JSON-файла
     */
    static void ParseJsonNode_Recur(JsonNode json_node, HashMap<Integer, String> test_status) {

        for (JsonNode single_node : json_node) {
            int node_id = single_node.findValue("id").intValue();
            if (test_status.containsKey(node_id)) {
                ((ObjectNode) single_node).put("value", test_status.get(node_id));
            }
            if (single_node.has("values")) {
                ParseJsonNode_Recur(single_node.findValue("values"), test_status);
            }
        }

    }
}
