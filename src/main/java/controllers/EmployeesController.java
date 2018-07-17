package controllers;

import db.DBHelper;
import db.Seeds;
import models.Employee;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;

public class EmployeesController {

    public static void main(String[] args) {

        Seeds.seedData();

        ManagersController managersController = new ManagersController();
        EngineersController engineersController = new EngineersController();

        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        get("/employees", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/employees/index.vtl");

            List<Employee> employees = DBHelper.getAll(Employee.class);
            model.put("employees", employees);

            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        get("/employees/:id", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/employees/id.vtl");

            int employeeId = Integer.parseInt(req.params(":id"));
            Employee employee = DBHelper.find(employeeId, Employee.class);

            model.put("employee", employee);

            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);
    }
}
