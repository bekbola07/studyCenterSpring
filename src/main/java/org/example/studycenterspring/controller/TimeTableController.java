package org.example.studycenterspring.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.studycenterspring.entity.*;
import org.example.studycenterspring.entity.enums.CSEStatus;
import org.example.studycenterspring.repo.LessonRepo;
import org.example.studycenterspring.repo.StudentRepo;
import org.example.studycenterspring.repo.StudentTimeTableRepo;
import org.example.studycenterspring.repo.TimeTableRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/time-table")
public class TimeTableController {
    private final TimeTableRepo timeTableRepo;
    private final LessonRepo lessonRepo;
    private final StudentTimeTableRepo studentTimeTableRepo;
    private final StudentRepo studentRepo;

    @GetMapping("/add")
    public String addTimeTablePage(HttpSession session, Model model){
        Group chosenGroup = (Group) session.getAttribute("chosenGroup");
        List<TimeTable> timeTables = timeTableRepo.findAllByGroupId(chosenGroup.getId());
        if (timeTables.isEmpty()){
            return "add-time-table";
        }else {

            List<TimeTable> tables = timeTables.stream()
                    .sorted(Comparator.comparingInt(TimeTable::getId))
                    .toList();
            TimeTable timeTable = tables.get(tables.size() - 1);
            Set<Integer> weekdays = timeTable.getLessons().stream()
                    .map(lesson -> lesson.getLessonTime().getDayOfWeek().getValue())
                    .collect(Collectors.toSet());
            List<StudTimeTable> studTimeTables = studentTimeTableRepo.findAllByTimeTableId(timeTable.getId());
            List<Student> students = studTimeTables.stream()
                    .map(StudTimeTable::getStudent)
                    .toList();

            model.addAttribute("students",students);

            model.addAttribute("pastTimeTable",timeTable);

            model.addAttribute("weekdays",weekdays);
            return "add-second-timeTable";
        }

    }
    @Transactional
    @PostMapping("/add")
    public String addTimeTable(HttpSession session,
                               @RequestParam String name,
                               @RequestParam Integer price,
                               @RequestParam List<Integer> days,
                               @RequestParam(required = false) List<Integer> studentIds) {

        Group chosenGroup = (Group) session.getAttribute("chosenGroup");
        TimeTable timeTable = TimeTable.builder()
                .name(name)
                .price(price)
                .group(chosenGroup)
                .build();

        timeTableRepo.save(timeTable);

        if (studentIds!=null){
            List<Student> students = studentRepo.findAllById(studentIds);
            for (Student student : students) {
                StudTimeTable studTimeTable = StudTimeTable.builder()
                        .timeTable(timeTable)
                        .student(student)
                        .build();
                studentTimeTableRepo.save(studTimeTable);
            }
        }

        int count = 0;
        LocalDate today = LocalDate.now();
        while (count < 12) {
            if (days.contains(today.getDayOfWeek().getValue())) {
                Lesson lesson = Lesson.builder()
                        .lessonOrder(count+1)
                        .lessonTime(today)
                        .lessonStatus(CSEStatus.CREATED)
                        .timeTable(timeTable)
                        .build();
                lessonRepo.save(lesson);
                count++;
            }

            today = today.plusDays(1);
        }

        return "redirect:/?groupId=%s&timetableId=%s".formatted(chosenGroup.getId(),timeTable.getId());
    }
}
