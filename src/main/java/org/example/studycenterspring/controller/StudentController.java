package org.example.studycenterspring.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.studycenterspring.entity.Group;
import org.example.studycenterspring.entity.StudTimeTable;
import org.example.studycenterspring.entity.Student;
import org.example.studycenterspring.entity.TimeTable;
import org.example.studycenterspring.repo.StudentRepo;
import org.example.studycenterspring.repo.StudentTimeTableRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {
    private final StudentRepo studentRepo;
    private final StudentTimeTableRepo studentTimeTableRepo;

    @GetMapping("/add")
    public String addStudentPage(){
        return "add-student";
    }
    @PostMapping("/add")
    public String addStudent(HttpSession session,
                             @RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam String phoneNumber){
        Student student = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .build();
        studentRepo.save(student);

        TimeTable chosenTimeTable = (TimeTable) session.getAttribute("chosenTimeTable");

        StudTimeTable studTimeTable = StudTimeTable.builder()
                .timeTable(chosenTimeTable)
                .student(student)
                .build();

        studentTimeTableRepo.save(studTimeTable);
        Group chosenGroup = (Group) session.getAttribute("chosenGroup");
        
        return "redirect:/?groupId=%s&timetableId=%s".formatted(chosenGroup.getId(),chosenTimeTable.getId());
    }
}
