package org.example.pmas.model;

public class Role {
    private Integer id;
    private String name;

    public Role(){

    }

    public Role (int id, String name){
    this.id = id;
    this.name = name;
    }


    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    @Override
    public String toString(){
        return name + "";
    }
}
