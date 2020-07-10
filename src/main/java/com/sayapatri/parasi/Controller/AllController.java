package com.sayapatri.parasi.Controller;

import com.sayapatri.parasi.Model.Notice;
import com.sayapatri.parasi.Repository.NoticeRepository;
import com.sayapatri.parasi.Service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
//@RequestMapping("/notice/")
public class AllController {
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/principaldesk")
    public String showPrincipal(Notice notice)

    {
        return "principal";
    }

   @GetMapping("/faculty")
    public String showFaculty(Notice notice)
    {

        return "faculty";
    }

    @GetMapping("/admin")
    public String showAdmin(Notice notice)
    {
        return "admission";
    }

    @GetMapping("/infra")
    public String showInfra(Notice notice)
    {
        return "infrastructures";
    }

    @GetMapping("/affil")
    public String showAffilation(Notice notice)

    {
        return "affilation";
    }

    @GetMapping("/home")
    public String showHome(Model model)
    {
        model.addAttribute("notices",noticeService.findAll());
        return "Home";
    }
    @GetMapping("/houseSystem")
    public String showHouseSystem(Notice notice)

    {
        return "HouseSystem";
    }
    @GetMapping("/schoolTransport")
    public String showSchoolTransport(Notice notice)

    {
        return "SchoolTransport";
    }

    @GetMapping("/attendance")
    public String showAttendance(Notice notice)

    {
        return "Attendance";
    }

    @GetMapping("/sports")
    public String showSports(Notice notice)

    {
        return "Sports";
    }


    @GetMapping("/project")
    public String showProjectWork(Notice notice)

    {
        return "ProjectWork";
    }

    @GetMapping("/curriculum")
    public String showCurriculum(Notice notice)

    {
        return "Curriculum";
    }

    @GetMapping("/studentcouncil")
    public String showStudentCouncil(Notice notice)

    {
        return "StudentCouncil";
    }

    @GetMapping("/cocurricular")
    public String showCoCurricular(Notice notice)

    {
        return "cocurricular";
    }

    @GetMapping("/gallery")
    public String showGallery(Notice notice)

    {
        return "Gallery";
    }


    @GetMapping("/readmore")
    public String showReadMore(Notice notice)

    {
        return "ReadMore";
    }




    @GetMapping("/fullnotice/{id}")
    public String showFullnotice(@PathVariable("id") long id,Model model)
    {

        Notice notice=noticeRepository.findById(id)
        .orElseThrow(()->new IllegalArgumentException("Invalid notice Id:"+id));
        System.out.println(noticeRepository.findById(id));

        model.addAttribute("noticess",notice);
        return "fullnotice";
    }



}
