package kz.kadyrbekov.studentapi;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @Email
    private String email;

    private String studentGroup;

    public Student() {
    }

    public Student(String name, String surname, String email, String studentGroup) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.studentGroup = studentGroup;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStudentGroup() { return studentGroup; }
    public void setStudentGroup(String studentGroup) { this.studentGroup = studentGroup; }
}
