package com.labs;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

public class ClientApp {
    private static final String URL = "http://localhost:8080/rest/students";

    public static void main(String[] args) {
        Client client = Client.create();

        // Консольный выбор CRUD метода
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose CRUD method (input CREATE, READ, UPDATE or DELETE), or input 'exit' for exit:");
        String chosenMethod;
        do {
            chosenMethod = scanner.nextLine();
            // проверим строку на наличие аргумента: если строка не является пустой и не состоит из пробелов, то
            // проверяем на наличие одной из возможных операций
            if (chosenMethod != null && !chosenMethod.trim().isEmpty()) {

                switch (chosenMethod) {
                    case ("CREATE"):
                        createStudent(client);
                        System.out.println("That's it! You can choose another CRUD method or input 'exit' for exit");
                        break;
                    case ("READ"):
                        searchStudentsByFields(client);
                        System.out.println("That's it! You can choose another CRUD method or input 'exit' for exit");
                        break;
                    case ("UPDATE"):
                        updateStudent(client);
                        System.out.println("That's it! You can choose another CRUD method or input 'exit' for exit");
                        break;
                    case ("DELETE"):
                        deleteStudent(client);
                        System.out.println("That's it! You can choose another CRUD method or input 'exit' for exit");
                        break;
                    case ("exit"):
                        System.out.println("Bye-Bye!");
                        break;
                    default:
                        System.out.println("You can input just CREATE, READ, UPDATE or DELETE!");
                        System.out.println("Try again or use 'exit' for exit.");
                        break;
                }
            }
        } while (!Objects.equals(chosenMethod, "exit"));

        scanner.close();

    }

    private static void updateStudent(Client client) {
        // Консольный ввод аргументов
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input rowID (integer): ");
        String rowIDString = scanner.nextLine();
        // Проверяем rowId на число
        int rowId = -1;
        if (rowIDString != null && !rowIDString.trim().isEmpty()) {
            try {
                rowId = Integer.parseInt(rowIDString.trim());
            } catch (NumberFormatException ex) {
                Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Your request is incorrect! RowId is not an integer.");
            }
        }

        if (rowId != -1) {

            System.out.println("What fields you want update for this row? \n" +
                    "Choose fields from 'name', 'surname', 'age', 'student_id', 'mark' and input it's below \n" +
                    " separated by comma without spaces");
            String updateRows = scanner.nextLine();

            // Преобразуем полученную строку в список аргументов
            String[] updateRowsList = updateRows.split(",", -1);

            Map<String, String> updateRowsMap = new HashMap<>();
            updateRowsMap.put("name", "");
            updateRowsMap.put("surname", "");
            updateRowsMap.put("age", "");
            updateRowsMap.put("student_id", "");
            updateRowsMap.put("mark", "");

            /*
            Проходим по списку аргументов и определяем совпадение с названиями полей таблицы БД.
            Проверка на пустую строку или строку из пробелов не нужна, т.к. в подобных случаях
            мы сразу перейдем к операции по умолчанию (default) в switch-case. То же самое мы получим,
            если не было совпадений по шаблону.
            Если совпадение найдено, то мы сразу запрашиваем ввести соответствующее значение, проверяем его на пустую
            строку и добавляем в массив, который будет формировать значения в запросе.
            Если в итоге сформирован пустой массив (ни одно из значений не подошло), то мы выводим сообщение,
            что введен некорректный запрос. Целочисленные значения age и student_id дополнительно проверяем на число (int).
            */
            for (String row : updateRowsList) {
                switch (row) {
                    case "name":
                        System.out.println("Input new value for 'name' field:");
                        String name = scanner.nextLine();
                        if (name != null && !name.trim().isEmpty()) {
                            updateRowsMap.put("name", name);
                        } else {
                            System.out.println("Field 'name' is incorrect and will not be updated!");
                        }
                        break;
                    case "surname":
                        System.out.println("Input new value for 'surname' field:");
                        String surname = scanner.nextLine();
                        if (surname != null && !surname.trim().isEmpty()) {
                            updateRowsMap.put("surname", surname);
                        } else {
                            System.out.println("Field 'surname' is incorrect and will not be updated!");
                        }
                        break;

                    case "age":
                        System.out.println("Input new value for 'age' field (integer):");
                        String age = scanner.nextLine();
                        try {
                            Integer.parseInt(age.trim());
                        } catch (NumberFormatException ex) {
                            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
                            age = "";
                        }
                        if (!age.trim().isEmpty()) {
                            updateRowsMap.put("age", age);
                        } else {
                            System.out.println("Field 'age' is not an integer and will not be updated!");
                        }
                        break;

                    case "student_id":
                        System.out.println("Input new value for 'student_id' field (integer):");
                        String student_id = scanner.nextLine();
                        try {
                            Integer.parseInt(student_id.trim());
                        } catch (NumberFormatException ex) {
                            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("Field 'student_id' is not an integer and will not be updated!");
                            student_id = "";
                        }
                        if (!student_id.trim().isEmpty()) {
                            updateRowsMap.put("student_id", student_id);
                        } else {
                            System.out.println("Field 'student_id' is not an integer and will not be updated!");
                        }
                        break;

                    case "mark":
                        System.out.println("Input new value for 'mark' field:");
                        String mark = scanner.nextLine();
                        if (mark != null && !mark.trim().isEmpty()) {
                            updateRowsMap.put("mark", mark);
                        } else {
                            System.out.println("Field 'mark' is incorrect and will not be updated!");
                        }
                        break;

                    default:
                        System.out.println("Incorrect request. Try again!");
                        break;
                }
            }

            int i = 0;
            for(String val : updateRowsMap.values()){
                if (val != null && !val.trim().isEmpty()) {
                    i++;
                }
            }

            if (i != 0) {

                System.out.println("You input new values for row " + rowId + ":\n" + updateRowsMap);
                System.out.println("Do you really want to change this fields for row " + rowId + "? (y -> yes, other -> no)");
                String agree = scanner.nextLine();

                if (agree.equals("y")) {
                    String name = updateRowsMap.get("name");
                    String surname = updateRowsMap.get("surname");
                    String age = updateRowsMap.get("age");
                    String studentId = updateRowsMap.get("student_id");
                    String mark = updateRowsMap.get("mark");

                    WebResource webResource = client.resource(URL);
                    webResource = webResource.queryParam("rowId",
                            rowIDString).queryParam("studentName", name).queryParam("studentSurname",
                            surname).queryParam("studentAge", age).queryParam("studentId",
                            studentId).queryParam("studentMark", mark);
                    ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
                    if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                        throw new IllegalStateException("Request failed");
                    }
                    System.out.println(response.getStatus());

                } else {
                    System.out.println("You just cancel your request. Try another request or exit.");
                }

            } else {
                System.out.println("All arguments is empty. Row update can not be completed.");
            }
        }
    }

    private static void deleteStudent(Client client) {
        // Консольный ввод аргументов
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input rowId (integer): ");
        String rowId = scanner.nextLine();

        try {
            Integer.parseInt(rowId.trim());

            WebResource webResource = client.resource(URL);
            webResource = webResource.queryParam("rowId", rowId);
            ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
            if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                throw new IllegalStateException("Request failed");
            }
            System.out.println(response.getStatus());

        } catch (NumberFormatException ex) {
            System.out.println("Incorrect rowId value! Input just one integer.");
        }
    }

    private static void createStudent(Client client) {

        // Консольный ввод аргументов
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input name: ");
        String name = scanner.nextLine();
        System.out.print("Input surname: ");
        String surname = scanner.nextLine();
        System.out.print("Input age (integer): ");
        String age = scanner.nextLine();
        System.out.print("Input student_id (integer): ");
        String studentId = scanner.nextLine();
        System.out.print("Input mark: ");
        String mark = scanner.nextLine();

        // проверим ввод на наличие значений: строка не является пустой и не состоит из пробелов
        if ((name != null && !name.trim().isEmpty())  &&
                (surname != null && !surname.trim().isEmpty()) &&
                (age != null && !age.trim().isEmpty()) &&
                (studentId != null && !studentId.trim().isEmpty()) &&
                (mark != null && !mark.trim().isEmpty())) {
            try {
                Integer.parseInt(age.trim());
                Integer.parseInt(studentId.trim());

                WebResource webResource = client.resource(URL);

                webResource = webResource.queryParam("studentName", name).queryParam("studentSurname",
                        surname).queryParam("studentAge", age).queryParam("studentId",
                        studentId).queryParam("studentMark", mark);

                ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class);
                if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                    throw new IllegalStateException("Request failed");
                }
                System.out.println(response.getStatus());

            } catch (NumberFormatException ex) {
                System.out.println("Incorrect age or studentId value!");
            }
        }
        else {
            System.out.println("Your request is incorrect!");
        }
    }

    private static void searchStudentsByFields(Client client) {
        List<String> searchThis = new ArrayList<>();
        // Консольный ввод аргументов (по аналогии с лаб. раб. 1)
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input arguments for search (one line = one argument, input 'exit' for exit): ");
        String given_arg;
        do {
            given_arg = scanner.nextLine();
            // проверим строку на наличие аргумента: если строка не является пустой и не состоит из пробелов, то
            // добавляем аргумент в массив
            if (given_arg != null && !given_arg.trim().isEmpty()) {
                searchThis.add(given_arg);
            }
        } while (!Objects.equals(given_arg, "exit"));

        WebResource webResource = client.resource(URL);
        // Передача набора параметров может производиться при помощи queryParams,
        // который на вход принимает структуру MultivaluedMap<String, String>,
        // которая в свою очередь состоит из ключа типа String и значения типа List<String>,
        // где в качестве списка передается массив параметров для поиска.
        MultivaluedMap<String, String> reqParams = new MultivaluedMapImpl();
        reqParams.put("searchParams", searchThis);
        webResource = webResource.queryParams(reqParams);

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<List<Student>> type = new GenericType<List<Student>>() {};

        for (Student student : response.getEntity(type)) {
            System.out.println(student);
        }
    }
}