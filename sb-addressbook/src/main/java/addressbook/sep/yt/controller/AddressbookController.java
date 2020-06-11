package addressbook.sep.yt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import addressbook.sep.yt.entity.Addressbook;
import addressbook.sep.yt.entity.Category;
import addressbook.sep.yt.form.AddForm;
import addressbook.sep.yt.service.AddressbookService;

@Controller
public class AddressbookController {
    @Autowired
    AddressbookService addressbookService;

    /**
     * @param model
     * @return 住所録一覧ページ
     */
    @RequestMapping(value = "/addressbook/list", method = RequestMethod.GET)
    public String displayList(Model model) {
        List<Addressbook> addressbookList = addressbookService.searchAll();
        model.addAttribute("addressbooklist", addressbookList);
        return "addressbook/list";
    }

    /**
     * @param model
     * @return 新規登録ページ
     */
    @RequestMapping(value = "/addressbook/add")
    public String displayAdd(Model model) {
        /**
         * Categoryエンティティのリスト
         */
        List<Category> categoryList = addressbookService.showCategory();
        model.addAttribute("categorylist", categoryList);

        /**
         * AddForm
         */
        AddForm addForm = new AddForm();
        model.addAttribute("addform", addForm);
        return "addressbook/add";
    }

    /**
     * @param addForm
     * @param result
     * @param model
     * @return 新規登録ページ
     */
    @RequestMapping(value = "/addressbook/add", method = RequestMethod.POST)
    public String checkAddRequest(@ModelAttribute @Validated AddForm addForm, BindingResult result, Model model) {
        if(result.hasErrors()) {
        	model.addAttribute("addform", addForm);
            /**
             * Categoryエンティティのリスト
             */
            List<Category> categoryList = addressbookService.showCategory();
            model.addAttribute("categorylist", categoryList);
           return "addressbook/add";
        }

        return "addressbook/add_confirm";
    }


}
