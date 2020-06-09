package addressbook.sep.yt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import addressbook.sep.yt.dto.AddRequest;
import addressbook.sep.yt.entity.Addressbook;
import addressbook.sep.yt.service.AddressbookService;

@Controller
public class AddressbookController {
    @Autowired

    AddressbookService addressbookService;

    @RequestMapping(value = "/addressbook/list", method = RequestMethod.GET)
    public String displayList(Model model) {
        List<Addressbook> addressbooklist = addressbookService.searchAll();
        model.addAttribute("addressbooklist", addressbooklist);
        return "addressbook/list";
    }

    @RequestMapping(value = "/addressbook/add")
    public String displayAdd(Model model) {
        model.addAttribute("addRequest", new AddRequest());
        return "addressbook/add";
    }
}
