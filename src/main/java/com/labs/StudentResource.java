package com.labs;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/students")
@Produces({MediaType.APPLICATION_JSON})

public class StudentResource {
    @GET
    public LinkedHashSet<Student> getStudents(@QueryParam("searchParams") final List<String> searchArgs) {
        System.out.println(searchArgs);
        return new PostgreSQLDAO().getStudentsByFields(searchArgs);
    }

    @POST
    public String createStudent(
            @QueryParam("studentName") String name,
            @QueryParam("studentSurname") String surname,
            @QueryParam("studentAge") String age,
            @QueryParam("studentId") String studentId,
            @QueryParam("studentMark") String mark)
            throws EmptyFieldException, CastToIntException, MarkFieldValueException {

        String status = "-1";

        if (name != null && !name.trim().isEmpty() &&
                surname != null && !surname.trim().isEmpty() &&
                age != null && !age.trim().isEmpty() &&
                studentId != null && !studentId.trim().isEmpty() &&
                mark != null && !mark.trim().isEmpty()) {

            try {
                Integer.parseInt(age.trim());
                Integer.parseInt(studentId.trim());

                if (mark.equals("неудовлетворительно") ||
                        mark.equals("удовлетворительно") ||
                        mark.equals("хорошо") ||
                        mark.equals("отлично")) {

                    status = new PostgreSQLDAO().createStudent(name, surname, age, studentId, mark);

                } else {
                    throw MarkFieldValueException.DEFAULT_INSTANCE;
                }

            } catch (NumberFormatException ex) {
                throw new CastToIntException("An error occurred when trying to convert 'age' and `student_id` to integers. ");
            }

        } else {
            throw EmptyFieldException.DEFAULT_INSTANCE;
        }

        return status;

    }

    @DELETE
    public String deleteStudent(@QueryParam("rowId") String rowId)
            throws EmptyFieldException, rowIsNotExistsException, CastToIntException {
        String status;
        if (rowId != null && !rowId.trim().isEmpty()) {
            try {
                Integer.parseInt(rowId.trim());
                status = new PostgreSQLDAO().deleteStudent(rowId);
                if (status.equals("0")) {
                    throw rowIsNotExistsException.DEFAULT_INSTANCE;
                }
            } catch (NumberFormatException ex) {
                throw new CastToIntException("An error occurred when trying to convert 'rowId' to integer. ");
            }
        } else {
            throw EmptyFieldException.DEFAULT_INSTANCE;
        }
        return status;
    }

    @PUT
    public String updateStudent(
            @QueryParam("rowId") String rowId,
            @QueryParam("studentName") String name,
            @QueryParam("studentSurname") String surname,
            @QueryParam("studentAge") String age,
            @QueryParam("studentId") String studentId,
            @QueryParam("studentMark") String mark)
            throws EmptyFieldException, CastToIntException, MarkFieldValueException,
            rowIsNotExistsException {

        String status;
        List<String> updateArgs = new ArrayList<>();

        if (rowId != null && !rowId.trim().isEmpty()) {
            if ((name != null && !name.trim().isEmpty()) ||
                    (surname != null && !surname.trim().isEmpty()) ||
                    (age != null && !age.trim().isEmpty()) ||
                    (studentId != null && !studentId.trim().isEmpty()) ||
                    (mark != null && !mark.trim().isEmpty())) {

                try {
                    Integer.parseInt(rowId.trim());
                    if (name != null && !name.trim().isEmpty()) updateArgs.add("name = '" + name + "'");
                    if (surname != null && !surname.trim().isEmpty()) updateArgs.add("surname = '" + surname + "'");
                    try {
                        if (age != null && !age.trim().isEmpty()) {
                            Integer.parseInt(age.trim());
                            updateArgs.add("age = '" + age + "'");
                        }
                        if (studentId != null && !studentId.trim().isEmpty()) {
                            Integer.parseInt(studentId.trim());
                            updateArgs.add("student_id = '" + studentId + "'");
                        }
                    } catch (NumberFormatException e) {
                        throw new CastToIntException("An error occurred when trying to convert " +
                            "'age' or 'studentId' to integer. ");
                    }
                } catch (NumberFormatException e) {
                    throw new CastToIntException("An error occurred when trying to convert 'rowId' to integer. ");
                }
            } else {
                throw EmptyFieldException.DEFAULT_INSTANCE;
            }
        } else {
            throw new EmptyFieldException("rowID cannot be empty!");
        }

        if (mark != null && !mark.trim().isEmpty()) {
             if (mark.equals("неудовлетворительно") ||
                    mark.equals("удовлетворительно") ||
                    mark.equals("хорошо") ||
                    mark.equals("отлично")) {
                updateArgs.add("mark = '" + mark + "'");
            } else {
                throw MarkFieldValueException.DEFAULT_INSTANCE;
            }
        }

        status = new PostgreSQLDAO().updateStudent(rowId, updateArgs);
        if (status.equals("0")) {
            throw rowIsNotExistsException.DEFAULT_INSTANCE;
        }

        return status;
    }

}