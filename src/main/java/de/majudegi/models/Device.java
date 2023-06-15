package de.majudegi.models;

public class Device {
    @Override
    public String toString() {
        return "Device [id=" + id + ", department_id=" + department_id + ", name=" + name + ", ip=" + ip + "]";
    }

    int id, department_id;
    String name;
    String ip;

    public Device(int id, int department_id, String name, String ip) {
        this.id = id;
        this.department_id = department_id;
        this.name = name;
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Device() {
        // Default constructor
    }
}
