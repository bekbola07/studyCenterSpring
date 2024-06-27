package org.example.studycenterspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.studycenterspring.entity.Group;
import org.example.studycenterspring.repo.GroupRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {
    private final GroupRepo groupRepo;

    @GetMapping("/add")
    public String addGroupPage(){
        return "add-group";
    }
    @PostMapping("/add")
    public String addGroup(@RequestParam String name){
        Group group = new Group(name);

        groupRepo.save(group);

        return "redirect:/";
    }
}
