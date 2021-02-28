package ua.kiev.prog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kiev.prog.model.Contact;
import ua.kiev.prog.model.Group;
import ua.kiev.prog.services.ContactService;
import ua.kiev.prog.services.ContactServiceImpl;

@Controller
public class GroupController {
    static final int DEFAULT_GROUP_ID = -1;

    @Autowired
    private ContactService contactService;


    @RequestMapping("/groups")
    public String showGroups(Model model) {
        model.addAttribute("groups", contactService.listGroups());

        return "groups";
    }

    @RequestMapping("/group_add_page")
    public String groupAddPage() {
        return "group_add_page";
    }

    @PostMapping("/group_search")
    public String groupSearch(@RequestParam String pattern, Model model) {
        model.addAttribute("groups", contactService.searchGroups(pattern));
        return "groups";
    }

    @RequestMapping(value="/group/add",
            method = RequestMethod.POST)
    public String groupAdd(@RequestParam String name) {
        contactService.addGroup(new Group(name));
        return "redirect:/";
    }

    @RequestMapping("/group/{id}")
    public String listGroup(@PathVariable(value = "id") long groupId,
                            Model model) {
        Group group = (groupId != DEFAULT_GROUP_ID) ?
                contactService.findGroup(groupId) : null;

        model.addAttribute("groups", contactService.listGroups());
        model.addAttribute("contacts", contactService.listContacts(group));

        return "index";
    }

    @RequestMapping(value = "/group/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> delete(
            @RequestParam(value = "toDelete[]", required = false)
                    long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            contactService.deleteGroups(toDelete);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
