package org.example.studycenterspring.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.studycenterspring.entity.Group;
import org.example.studycenterspring.entity.StudTimeTable;
import org.example.studycenterspring.entity.Student;
import org.example.studycenterspring.entity.TimeTable;
import org.example.studycenterspring.repo.GroupRepo;
import org.example.studycenterspring.repo.StudentTimeTableRepo;
import org.example.studycenterspring.repo.TimeTableRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final GroupRepo groupRepo;
    private final TimeTableRepo timeTableRepo;
    private final StudentTimeTableRepo studentTimeTableRepo;

    @GetMapping("/")
    public String mainPage(Model model,
                           @RequestParam(required = false) Integer groupId,
                           @RequestParam(required = false) Integer timetableId,
                           HttpSession session){
        List<Group> groups = groupRepo.findAll();

        if (groupId!=null){
            Group chosenGroup = groupRepo.findById(groupId).orElseThrow();
            model.addAttribute("chosenGroup", chosenGroup);
            session.setAttribute("chosenGroup",chosenGroup);
            List<TimeTable> timeTables = timeTableRepo.findAllByGroupId(groupId);
            model.addAttribute("timeTables", timeTables);
            if (timetableId!=null){
                TimeTable chosenTimeTable = timeTableRepo.findById(timetableId).orElseThrow();
                model.addAttribute("chosenTimeTable", chosenTimeTable);
                session.setAttribute("chosenTimeTable", chosenTimeTable);

                List<StudTimeTable> studTimeTables = studentTimeTableRepo.findAllByTimeTableId(chosenTimeTable.getId());
                List<Student> students = studTimeTables.stream()
                        .map(StudTimeTable::getStudent)
                        .toList();
                model.addAttribute("students",students);
                if (!students.isEmpty()){
                    int size = students.get(0).getStudTimeTables().get(0).getStudentAttendances().size();
                    session.setAttribute("currentLessonOrder", size+2);

                }else {
                    session.setAttribute("currentLessonOrder", 1);

                }
            }
        }
        model.addAttribute("groups", groups);

        if (session.getAttribute("currentLessonOrder")==null){
            model.addAttribute("currentLessonOrder", 1);

        }else {
            Integer currentLessonOrder = (Integer) session.getAttribute("currentLessonOrder");

            model.addAttribute("currentLessonOrder", currentLessonOrder-1);
        }
        return "/index";
    }
}
