package de.majudegi.db;

import de.majudegi.models.Department;
import de.majudegi.models.Device;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    String query = null;

    Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "postgres");
        } catch (Exception e) {
            LOGGER.error("Couldn't connect to database: " + e.getMessage());
        }
        return conn;
    }

    public Department getDepartmentData(String department) {
        Department result = null;
        try (Connection conn = connect(); PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM department WHERE name=?")) {
            preparedStatement.setString(1, department);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            int id = rs.getInt("id");
            result = new Department(id, rs.getString("name"), rs.getString("location"));
            List<Device> deviceList = new ArrayList<Device>();
            deviceList = getDevices(id);
            for (Device device : deviceList) {
                result.getDeviceList().add(device);
            }
            LOGGER.info("Department with id " + id + " has been retrieved.");
        } catch (Exception e) {
            LOGGER.error("(getDepartmentData) Couldn't get data from database: " + e.getMessage());
        }
        return result;
    }

    public List<String> getDepartmentsByCity(String location) {
        List<String> result = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM department WHERE location=?")) {
            preparedStatement.setString(1, location);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                result.add(rs.getString("name"));
            }

            LOGGER.info("Departments in city:  " + result);
        } catch (Exception e) {
            LOGGER.error("(getDepartmentData) Couldn't get data from database: " + e.getMessage());
        }
        return result;
    }

    List<Device> getDevices(int departmentID) {
        List<Device> result = new ArrayList<Device>();
        try (Connection conn = connect(); PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM device WHERE department_id=?")) {
            preparedStatement.setInt(1, departmentID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                result.add(new Device(rs.getInt("id"), rs.getInt("department_id"), rs.getString("name"), rs.getString("ip")));
            }
            LOGGER.info("Devices with department_id " + departmentID + " have been retrieved.");
        } catch (Exception e) {
            LOGGER.error("(getDevices) Couldn't get data from database: " + e.getMessage());
        }
        return result;
    }

    public List<String> getCities() {
        List<String> result = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement preparedStatement = conn.prepareStatement("SELECT DISTINCT location FROM department")) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                result.add(rs.getString("location"));
            }
            LOGGER.info("List of all cities");
        } catch (Exception e) {
            LOGGER.error("(getDevices) Couldn't get data from database: " + e.getMessage());
        }
        return result;
    }

    public List<String> getDepartments() {
        List<String> result = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM department")) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                result.add(rs.getString("name"));
            }
            LOGGER.info("List of all Departments");
        } catch (Exception e) {
            LOGGER.error("(getDevices) Couldn't get data from database: " + e.getMessage());
        }
        return result;
    }

    public String createDepartment(String name, String location) {
        if (getDepartmentData(name) != null)
            return "This department already exists";
        try (Connection conn = connect(); PreparedStatement preparedStatement = conn.prepareStatement(("INSERT INTO department(name, location) VALUES(?, ?)"), Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, location);
            preparedStatement.executeUpdate();

            ResultSet rs;
            rs = preparedStatement.getGeneratedKeys();
            rs.next();
            LOGGER.info("Successfully created new department '" + name + "' in '" + location + "' with the id " + rs.getInt(1));
            return "Successful";
        } catch (Exception e) {
            LOGGER.error("Couldn't create new department: " + e.getMessage());
            return "An error occured";
        }
    }

    public void createDevice(String name, int department_id, String ip) {
        for (Device device : getDevices(department_id)) {
            if (device.getIp().equals(ip))
                return;
        }
        try (Connection conn = connect(); PreparedStatement preparedStatement = conn.prepareStatement(("INSERT INTO device(name, department_id, ip) VALUES(?, ?, ?)"), Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, department_id);
            preparedStatement.setString(3, ip);
            preparedStatement.executeUpdate();

            ResultSet rs;
            rs = preparedStatement.getGeneratedKeys();
            rs.next();
            LOGGER.info("Successfully created new device '" + name + "': " + ip + " in department " + department_id + " with the id " + rs.getInt(1));
        } catch (Exception e) {
            LOGGER.error("Couldn't create new device: " + e.getMessage());
        }
    }


    public boolean deleteDepartment(String name) {
        try (Connection conn = connect(); PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM department WHERE name=?")) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();

            LOGGER.info("Department '" + name + "' has been deleted");
            return true;
        } catch (Exception e) {
            LOGGER.error("Failed to delete department '" + name + "'");
            return false;
        }
    }

    public boolean deleteDevice(String ip) {
        try (Connection conn = connect(); PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM device WHERE ip=?")) {
            preparedStatement.setString(1, ip);
            preparedStatement.executeUpdate();

            LOGGER.info("Device with ip '" + ip + "' has been deleted");
            return true;
        } catch (Exception e) {
            LOGGER.error("Failed to delete device with ip '" + ip + "'");
            return false;
        }
    }
}