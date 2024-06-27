package org.example.studycenterspring.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.studycenterspring.entity.*;
import org.example.studycenterspring.entity.enums.CSEStatus;
import org.example.studycenterspring.repo.StudentAttendanceRepo;
import org.example.studycenterspring.repo.StudentTimeTableRepo;
import org.example.studycenterspring.repo.TimeTableRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lesson")
public class LessonController {
    private final StudentTimeTableRepo studentTimeTableRepo;
    private final StudentAttendanceRepo studentAttendanceRepo;
    private final TimeTableRepo timeTableRepo;

    @PostMapping("/start")
    public String startLesson(@RequestParam Integer timeTableId,
                              @RequestParam Integer groupId,
                              HttpSession session){

        List<StudTimeTable> studTimeTables = studentTimeTableRepo.findAllByTimeTableId(timeTableId);
        TimeTable timeTable = timeTableRepo.findById(timeTableId).orElseThrow(() -> new RuntimeException("no time table"));
        Integer currentLessonOrder = (Integer) session.getAttribute("currentLessonOrder");

        timeTable.setStarted(true);

        Lesson lesson = timeTable.getLessons().get(currentLessonOrder-1);
        lesson.setLessonStatus(CSEStatus.STARTED);

        for (StudTimeTable studTimeTable : studTimeTables) {

            StudentAttendance studentAttendance = new StudentAttendance(lesson,false,studTimeTable);
            studentAttendanceRepo.save(studentAttendance);
            studTimeTable.getStudentAttendances().add(studentAttendance);

            int newLessonOrder = studTimeTable.getStudentAttendances().size()+1 ;

            session.setAttribute("currentLessonOrder",newLessonOrder);

            studentTimeTableRepo.save(studTimeTable);

        }

        return "redirect:/?groupId=%s&timetableId=%s".formatted(groupId,timeTableId);

    }
    @PostMapping("/end")
    public String endLesson(@RequestParam Integer timeTableId,
                            @RequestParam Integer groupId,
                            HttpSession session){

        TimeTable timeTable = timeTableRepo.findById(timeTableId).orElseThrow(() -> new RuntimeException("no time table"));
        Integer currentLessonOrder = (Integer) session.getAttribute("currentLessonOrder");

        if (currentLessonOrder != null && currentLessonOrder > 0) {
            Lesson lesson = timeTable.getLessons().get(currentLessonOrder - 1);
            lesson.setLessonStatus(CSEStatus.ENDED);
            timeTableRepo.save(timeTable);
        }

        return "redirect:/?groupId=%s&timetableId=%s".formatted(groupId, timeTableId);

    }

}
