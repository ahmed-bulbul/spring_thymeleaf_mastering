package com.thikthak.app.controller.test;

import com.thikthak.app.domain.auth.User;
import com.thikthak.app.domain.test.ServiceItems;
import com.thikthak.app.service.auth.UserService;
import com.thikthak.app.service.test.ServiceItemService;
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
@RequestMapping("/items")
public class ServiceItemsController {

    private ServiceItemService service;

    @Autowired
    public void setInjectedBean(ServiceItemService service) {
        this.service = service;
    }



    @RequestMapping("/index8")
    public String getAll(Model model)
    {
        List<ServiceItems> list = service.getAll();

        model.addAttribute("users", list);
        return "view/items/index";
    }

    // @Secured("ROLE_ADMIN")
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
        String sortField = "id";
        String sortDir = "desc";

        if (request.getParameter("pageNum") != null && !request.getParameter("pageNum").isEmpty()) pageNum = Integer.parseInt(request.getParameter("pageNum"));
        if (request.getParameter("pageSize") != null && !request.getParameter("pageSize").isEmpty()) pageSize = Integer.parseInt(request.getParameter("pageSize"));
        if (request.getParameter("sortField") != null && !request.getParameter("sortField").isEmpty()) sortField = request.getParameter("sortField");
        if (request.getParameter("sortDir") != null && !request.getParameter("sortDir").isEmpty()) sortDir = request.getParameter("sortDir");

        Page< ServiceItems > page = service.getAllPaginated(pageNum, pageSize, sortField, sortDir);
        List < ServiceItems > list = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("users", list);
        model.addAttribute("objectList", list);

        return "view/items/index";
    }

    //    @RequestMapping(path = {"/edit", "/edit/{id}"})
//    public String edit(Model model, @PathVariable("id") Optional<Long> id) throws Exception
    @GetMapping(value = "/show/{id}")
    public String show(Model model, @PathVariable long id)
    {
        ServiceItems user = null;
        try {
            user = service.findById(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Entity not found");
        }
        model.addAttribute("object", user);
        return "view/items/show";
    }

    @RequestMapping(path = "/create")
    public String create(Model model)
    {
        model.addAttribute("items", new ServiceItems());
        return "view/items/create";
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public String save(@Valid ServiceItems serviceItems, BindingResult result, Model model, RedirectAttributes redirAttrs)
    {
        if (result.hasErrors()) {
            return "view/items/create";
        }
        serviceItems = service.createOrUpdate(serviceItems);
        model.addAttribute("object", serviceItems);
        redirAttrs.addFlashAttribute("successMgs", "Successfully save transaction");

        // return "redirect:/user/index";
        return "redirect:/items/show/" + serviceItems.getId();
    }

    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String edit(Model model, @PathVariable("id") Optional<Long> id) throws Exception
    {
        if (id.isPresent()) {
            ServiceItems entity = service.getById(id.get());
            model.addAttribute("items", entity);
        } else {
            model.addAttribute("items", new ServiceItems());
        }
        return "view/items/edit";
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String update(ServiceItems items, RedirectAttributes redirAttrs)
    {
        items = service.createOrUpdate(items);
        redirAttrs.addFlashAttribute("successMgs", "Successfully update transaction");
        // return "redirect:/user/index";
        return "redirect:/items/show/" + items.getId();
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteById(@PathVariable("id") Long id, RedirectAttributes redirAttrs) throws Exception
    {
        service.deleteById(id);
        redirAttrs.addFlashAttribute("warningMgs", "Successfully delete transaction");
        return "redirect:/items/index8";
    }


}

