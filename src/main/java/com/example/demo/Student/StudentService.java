package com.example.demo.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }



    public List<Student> getStudents()
    {

                return studentRepository.findAll();

    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional =
                studentRepository.findStudentbyEmail(student.getEmail());
        if(studentOptional.isPresent())
        {
            throw new IllegalStateException("email taken already!!");
        }

        studentRepository.save(student);

        System.out.println(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists)
        {
            throw new IllegalStateException("There is not given Id..");
        }
        studentRepository.deleteById(studentId);

    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email)
    {
        Student student = studentRepository.findById(studentId).orElseThrow(()->new IllegalStateException("does not exist"));
        if(name!=null && name.length()>0 && Objects.equals(name,student.getName()))
        {
            student.setName(name);
        }
        if(email!=null && email.length()>0 && Objects.equals(email,student.getEmail()))
        {
            student.setEmail(email);
        }
    }
}
