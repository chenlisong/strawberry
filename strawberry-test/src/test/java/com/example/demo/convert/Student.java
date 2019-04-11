package com.example.demo.convert;

public class Student {

    @Convert(field = "id")
    private Long id;

    @Convert(field = "name")
    private String name;

    @Convert(field = "sex")
    private Integer sex;

    private String content2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }


}
