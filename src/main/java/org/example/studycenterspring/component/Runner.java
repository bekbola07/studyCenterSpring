package org.example.studycenterspring.component;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.example.studycenterspring.entity.*;
import org.example.studycenterspring.entity.enums.CSEStatus;
import org.example.studycenterspring.repo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;


@Component
@RequiredArgsConstructor

public class Runner implements CommandLineRunner {
    private final GroupRepo groupRepo;
    private final StudentRepo studentRepo;
    private final StudentAttendanceRepo studentAttendanceRepo;
    private final StudentTimeTableRepo studentTimeTableRepo;
    private final TimeTableRepo timeTableRepo;
    private final LessonRepo lessonRepo;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    String ddl;
    @Override
    public void run(String... args) throws Exception {
        if (ddl.equals("create")) {
            Role rol1 = new Role("ROLE_ADMIN");
            Role rol2 = new Role("ROLE_MENTOR");
            roleRepo.save(rol2);
            roleRepo.save(rol1);

                userRepo.save(User.builder()
                        .fullName("admin Eshmat")
                        .password(passwordEncoder.encode("123"))
                        .username("admin")
                        .phoneNumber("+9981234567")
                        .roles(List.of(rol1))
                        .build());

            userRepo.save(User.builder()
                    .fullName("mentor Eshmat")
                    .password(passwordEncoder.encode("123"))
                    .username("mentor")
                    .phoneNumber("+9981234567")
                    .roles(List.of(rol2))
                    .build());

            Faker faker = new Faker();
            Group g34=new Group("G34");
            Group g35=new Group("G35");
            Group g36=new Group("G36");
            Group g37=new Group("G37");

            groupRepo.save(g34);
            groupRepo.save(g35);
            groupRepo.save(g36);
            groupRepo.save(g37);

            List<Group> groups = groupRepo.findAll();

            for (Group group : groups) {
                for (int i = 1; i <= 10; i++) {
                    TimeTable timeTable = TimeTable.builder()
                            .name("Time Table" + i)
                            .price(i * 100000)
                            .group(group)
                            .isStarted(true)
                            .build();
                    timeTableRepo.save(timeTable);
                }
            }

            List<TimeTable> timeTables = timeTableRepo.findAll();

            for (TimeTable timeTable : timeTables) {
                for (int i = 0; i < 20; i++) {

                    Student student = Student.builder()
                            .firstName(faker.name().firstName())
                            .lastName(faker.name().lastName())
                            .phoneNumber(faker.phoneNumber().phoneNumber())
                            .build();
                        studentRepo.save(student);

                    StudTimeTable studTimeTable = StudTimeTable.builder()
                            .timeTable(timeTable)
                            .student(student)
                            .build();

                    studentTimeTableRepo.save(studTimeTable);
                }
            }
            List<TimeTable> all = timeTableRepo.findAll();
            List<Integer> days=List.of(1,3,5);
            for (TimeTable timeTable : all) {
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
            }
            List<StudTimeTable> studTimeTables = studentTimeTableRepo.findAll();
            for (StudTimeTable studTimeTable : studTimeTables) {
                for (int i = 1; i <=5; i++) {
                    StudentAttendance studentAttendance = StudentAttendance.builder()
                            .lesson(studTimeTable.getTimeTable().getLessons().get(i))
                            .hasInLesson(new Random().nextBoolean())
                            .studTimeTable(studTimeTable)
                            .build();
                    studentAttendanceRepo.save(studentAttendance);
                }
            }
        }
    }
}
