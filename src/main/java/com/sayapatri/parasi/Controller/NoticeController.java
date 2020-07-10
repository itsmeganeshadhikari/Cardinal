package com.sayapatri.parasi.Controller;

import com.sayapatri.parasi.Model.Notice;
import com.sayapatri.parasi.Repository.NoticeRepository;
import com.sayapatri.parasi.Service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
//@RequestMapping("/notice/")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;

//    @GetMapping("/")
//    public String showuserNoticeList(Model model)
//    {
//        model.addAttribute("notices",noticeService.findAll());
//        return "Home";
//
//    }


//    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/form")
    public String showNoticeForm(Notice notice)
    {
      return "form";
    }

//    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/add")
    public String addNotice(@Valid Notice notice,BindingResult result,Model model)
    {
     if(result.hasErrors())
     {
         return "form";
     }
     noticeService.save(notice);
       return "redirect:/adminlist";
    }
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/adminlist")
    public String showadminNoticeList(Model model)
    {
        model.addAttribute("notices",noticeService.findAll());
        return "message";
    }


//    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id,Model model)
    {
        Notice notice=noticeRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Invalid notice Id:"+id));
        model.addAttribute("notice",notice);
        return "update-notice";

    }
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/update/{id}")
    public String updateNotice(@PathVariable("id") long id,@Valid Notice notice,BindingResult result,Model model)
    {
        if(result.hasErrors()){
            notice.setId(id);
            return "update-notice";
        }
        noticeService.save(notice);
        model.addAttribute("notices",noticeService.findAll());
        return "redirect:/adminlist";
    }
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") long id, Model model) {
        Notice notice=noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        noticeRepository.delete(notice);
        model.addAttribute("students", noticeRepository.findAll());
        return "redirect:/adminlist";
    }
}
