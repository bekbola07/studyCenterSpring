package org.example.studycenterspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.studycenterspring.entity.StudentAttendance;
import org.example.studycenterspring.repo.StudentAttendanceRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/change")
public class ChangeAttendanceController {
    private final StudentAttendanceRepo attRepo;
    @GetMapping
    public String changeAtt(@RequestParam Integer id,
                            @RequestParam Integer groupId,
                            @RequestParam Integer timetableId){
        StudentAttendance studentAttendance = attRepo.findById(id).orElseThrow();
        studentAttendance.setHasInLesson(!studentAttendance.isHasInLesson());

        attRepo.save(studentAttendance);
        return "redirect:/?groupId=%s&timetableId=%s".formatted(groupId,timetableId);


    }
}
