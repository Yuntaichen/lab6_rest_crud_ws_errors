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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input rowID (integer): ");
        String rowId = scanner.nextLine();
        System.out.println("What fields you want update for this row? \n" +
                    "Choose fields from 'name', 'surname', 'age', 'student_id', 'mark' and input it's below \n" +
                    " separated by comma without spaces");
        String updateRows = scanner.nextLine();
        String[] updateRowsList = updateRows.split(",", -1);
        Map<String, String> updateRowsMap = new HashMap<>();
        updateRowsMap.put("name", "");
        updateRowsMap.put("surname", "");
        updateRowsMap.put("age", "");
        updateRowsMap.put("student_id", "");
        updateRowsMap.put("mark", "");

        for (String row : updateRowsList) {
            switch (row) {
                case "name":
                    System.out.println("Input new value for 'name' field:");
                    String name = scanner.nextLine();
                    updateRowsMap.put("name", name);
                    break;
                case "surname":
                    System.out.println("Input new value for 'surname' field:");
                    String surname = scanner.nextLine();
                    updateRowsMap.put("surname", surname);
                    break;
                case "age":
                    System.out.println("Input new value for 'age' field (integer):");
                    String age = scanner.nextLine();
                    updateRowsMap.put("age", age);
                    break;
                case "student_id":
                    System.out.println("Input new value for 'student_id' field (integer):");
                    String student_id = scanner.nextLine();
                    updateRowsMap.put("student_id", student_id);
                    break;
                case "mark":
                    System.out.println("Input new value for 'mark' field:");
                    String mark = scanner.nextLine();
                    updateRowsMap.put("mark", mark);
                    break;
            }
        }

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
                            rowId).queryParam("studentName", name).queryParam("studentSurname",
                            surname).queryParam("studentAge", age).queryParam("studentId",
                            studentId).queryParam("studentMark", mark);
                ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);

                System.out.println(response.getStatus());
                System.out.println(response.getEntity(new GenericType<String>() {}));

        } else {
                System.out.println("You just cancel your request. Try another request or exit.");
        }
    }

    private static void deleteStudent(Client client) {
        // Консольный ввод аргументов
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input rowId (integer): ");
        String rowId = scanner.nextLine();


        WebResource webResource = client.resource(URL);
        webResource = webResource.queryParam("rowId", rowId);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
        System.out.println(response.getStatus());
        System.out.println(response.getEntity(new GenericType<String>() {}));

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
        WebResource webResource = client.resource(URL);
        webResource = webResource.queryParam("studentName", name).queryParam("studentSurname",
                        surname).queryParam("studentAge", age).queryParam("studentId",
                        studentId).queryParam("studentMark", mark);

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class);

        System.out.println(response.getStatus());
        System.out.println(response.getEntity(new GenericType<String>() {}));

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