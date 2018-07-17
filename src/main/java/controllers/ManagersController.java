package controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.sun.tools.internal.xjc.model.Model;
import db.DBHelper;
import models.Department;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;


public class ManagersController {

    public ManagersController() {
        this.setUpEndpoints();
    }

    private void setUpEndpoints() {
        //TODO: add routes in here
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();


        get("/managers", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/managers/index.vtl");

            List<Manager> managers = DBHelper.getAll(Manager.class);
            model.put("managers", managers);

            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);


        get("/managers/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            model.put("template", "templates/managers/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);


        post("/managers", (req, res) -> {
            String firstName = req.queryParams("first-name");

            String lastName = req.queryParams("last-name");

            int salary = Integer.parseInt(req.queryParams("salary"));

            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);

            double budget = Double.parseDouble(req.queryParams("budget"));

            Manager newManager = new Manager(firstName, lastName, salary, department, budget);
            DBHelper.save(newManager);

            res.redirect("/managers");
            return null;
        }, velocityTemplateEngine);

        get("/managers/:id", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/managers/id.vtl");

            int managerId = Integer.parseInt(req.params(":id"));
            Manager manager= DBHelper.find(managerId, Manager.class);

            model.put("manager", manager);
            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);

        get("/managers/:id/edit", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);

            int managerId = Integer.parseInt(req.params(":id"));
            Manager manager = DBHelper.find(managerId, Manager.class);

            model.put("manager", manager);
            model.put("departments", departments);
            model.put("template", "templates/managers/edit.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);










































    }


}
