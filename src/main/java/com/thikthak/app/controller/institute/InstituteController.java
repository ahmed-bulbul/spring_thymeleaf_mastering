package com.thikthak.app.controller.institute;


import com.thikthak.app.domain.institute.Institute;
import com.thikthak.app.service.institute.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/institute")
public class InstituteController {
    private InstituteService instituteService;

    @Autowired
    public void setInjectedBean(InstituteService instituteService) {
        this.instituteService = instituteService;
    }

    @RequestMapping("/index9")
    public String getAll(Model model)
    {
        List<Institute> list = instituteService.getAll();

        model.addAttribute("institute", list);
        return "view/institute/index";
    }

    @Secured({ "ROLE_EDITOR", "ROLE_USER" })
    @RequestMapping("/index")
    public String getAllPaginated(HttpServletRequest request, Model model)
    {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        System.out.println(securityContext.getAuthentication().getName());
        System.out.println(securityContext.getAuthentication().getAuthorities());
        System.out.println(securityContext.getAuthentication().getPrincipal());
        System.out.println(securityContext.getAuthentication().getDetails());

        int pageNum = 1;   //default page number is 0 (yes it is weird)
        int pageSize = 5;  //default page size is 10
        String sortField = "instituteId";
        String sortDir = "desc";

        if (request.getParameter("pageNum") != null && !request.getParameter("pageNum").isEmpty()) pageNum = Integer.parseInt(request.getParameter("pageNum"));
        if (request.getParameter("pageSize") != null && !request.getParameter("pageSize").isEmpty()) pageSize = Integer.parseInt(request.getParameter("pageSize"));
        if (request.getParameter("sortField") != null && !request.getParameter("sortField").isEmpty()) sortField = request.getParameter("sortField");
        if (request.getParameter("sortDir") != null && !request.getParameter("sortDir").isEmpty()) sortDir = request.getParameter("sortDir");

        Page<Institute> page = instituteService.getAllPaginated(pageNum, pageSize, sortField, sortDir);
        List< Institute > list = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("institute", list);
        model.addAttribute("objectList", list);

        return "view/institute/index";
    }

    @GetMapping(value = "/show/{id}")
    public String show(Model model, @PathVariable long id)
    {
        Institute institute = null;
        try {
            institute = instituteService.findById(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Entity not found");
        }
        model.addAttribute("object", institute);
        return "view/institute/show";
    }

    @RequestMapping(path = "/create")
    public String create(Model model)
    {
        model.addAttribute("institute", new Institute());
        return "view/institute/create";
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public String save(@Valid Institute institute, BindingResult result, Model model, RedirectAttributes redirAttrs)
    {
        if (result.hasErrors()) {
            return "view/institute/create";
        }
        institute = instituteService.createOrUpdate(institute);
        model.addAttribute("object", institute);
        redirAttrs.addFlashAttribute("successMgs", "Successfully save transaction");

        // return "redirect:/user/index";
        return "redirect:/institute/show/" + institute.getInstituteId();
    }

    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String edit(Model model, @PathVariable("id") Optional<Long> id) throws Exception
    {
        if (id.isPresent()) {
            Institute institute = instituteService.getById(id.get());
            model.addAttribute("institute", institute);
        } else {
            model.addAttribute("institute", new Institute());
        }
        return "view/institute/edit";
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String update(Institute institute, RedirectAttributes redirAttrs)
    {
        institute = instituteService.createOrUpdate(institute);
        redirAttrs.addFlashAttribute("successMgs", "Successfully update transaction");
        // return "redirect:/user/index";
        return "redirect:/institute/show/" + institute.getInstituteId();
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteById(@PathVariable("id") Long id, RedirectAttributes redirAttrs) throws Exception
    {
        instituteService.deleteById(id);
        redirAttrs.addFlashAttribute("warningMgs", "Successfully delete transaction");
        return "redirect:/institute/index";
    }



}
