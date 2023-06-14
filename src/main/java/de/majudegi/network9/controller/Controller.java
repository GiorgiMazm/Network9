package de.majudegi.network9.controller;

import de.majudegi.db.DatabaseHandler;
import de.majudegi.models.Department;
import de.majudegi.models.Device;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping
public class Controller {
    DatabaseHandler dbHandler = new DatabaseHandler();

    @GetMapping(path = "/")
    public String showHome() {
        return "Welcome to our network management API! Feel free to try: <br><br>"
                + "/department/{name} - To get infoormation about a department and it's devices, <br>"
                + "/create/department - To create a new appartment by sending a POST request, <br>"
                + "/create/device - To create a new device by sending a POST request, <br>"
                + "/delete/department/{name} - To delete a department via DELETE";
    }

    @GetMapping(path = "/department/{name}")
    public Department showDepartmentData(@PathVariable String name) {
        return dbHandler.getDepartmentData(name);
    }

    @GetMapping(path = "/cities")
    public List<java.lang.String> showCities() {
        String defaultResponse = "No cities available";
        List<String> result = dbHandler.getCities();
        return (result == null ? Collections.singletonList(defaultResponse) : result);
    }

    @GetMapping(path = "/departments")
    public
    List<String> showDepartments() {
        String defaultResponse = "No departments available";
        List<String> result = dbHandler.getDepartments();
        return (result == null ? Collections.singletonList(defaultResponse) : result);
    }

    @GetMapping(path = "/city/{city}")
    public
    List<String> showDepartmentsByCity(@PathVariable String city) {
        String defaultResponse = "No departments available";
        List<String> result = dbHandler.getDepartmentsByCity(city);
        return (result == null ? Collections.singletonList(defaultResponse) : result);
    }

    @PostMapping(path = "/create/department")
    String createDepartment(@RequestBody Department newDepartment) {
        return dbHandler.createDepartment(newDepartment.getName(), newDepartment.getLocation());
    }

    @PostMapping(path = "/create/device")
    String createDevice(@RequestBody Device newDevice) {
        return dbHandler.createDevice(newDevice.getName(), newDevice.getDepartment_id(), newDevice.getIp());
    }

    @DeleteMapping(path = "/delete/department/{name}")
    String deleteDepartment(@PathVariable String name) {
        if (dbHandler.deleteDepartment(name))
            return "Successfully deleted the " + name + " department";
        else
            return "Failed to delete the + " + name + " department";
    }

    @DeleteMapping(path = "/delete/device/{ip}")
    String deleteDevice(@PathVariable String ip) {
        if (dbHandler.deleteDevice(ip))
            return "Successfully deleted device with the ip '" + ip + "'";
        else
            return "Failed to delete device with the ip '" + ip + "'";
    }
}
