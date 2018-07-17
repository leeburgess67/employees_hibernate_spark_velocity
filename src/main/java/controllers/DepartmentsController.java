package controllers;

import db.DBHelper;
import db.DBManager;
import models.Department;
import models.Engineer;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class DepartmentsController {

    public DepartmentsController() {
        this.setUpEndpoints();
    }

    private void setUpEndpoints() {
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        //CREATE

        get("/departments/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        post("/departments", (req, res) -> {
            String deptName = req.queryParams("name");

            Department newDepartment = new Department(deptName);
            DBHelper.save(newDepartment);

            res.redirect("/departments");
            return null;
        }, velocityTemplateEngine);


        //READ
        get("/departments", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/index.vtl");

            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);

            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);


        get("/departments/:id", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/id.vtl");

            int departmentId = Integer.parseInt(req.params(":id"));
            Department department= DBHelper.find(departmentId, Department.class);
            model.put("department", department);

            Manager manager = DBManager.findManagerForDept(department);
            model.put("manager", manager);

            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);


        //UPDATE
        get("/departments/:id/edit", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();

            int departmentId = Integer.parseInt(req.params(":id"));
            Department department = DBHelper.find(departmentId, Department.class);

            model.put("department", department);
            model.put("template", "templates/departments/edit.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        post("/departments/:id", (req, res) -> {
            int departmentId = Integer.parseInt(req.params(":id"));
            Department department = DBHelper.find(departmentId, Department.class);
            String deptName = (req.queryParams("name"));

            department.setTitle(deptName);
            DBHelper.update(department);

            res.redirect("/departments");
            return null;
        }, velocityTemplateEngine);



        //DELETE
        post("/departments/:id/delete", (req, res) -> {
            int departmentId = Integer.parseInt(req.params(":id"));
            Department department = DBHelper.find(departmentId, Department.class);
            DBHelper.delete(department);

            res.redirect("/departments");

            return null;
        }, velocityTemplateEngine);

        //WILL DELETE ONE BUT NOT THE LAST ONE???



























    }




}






