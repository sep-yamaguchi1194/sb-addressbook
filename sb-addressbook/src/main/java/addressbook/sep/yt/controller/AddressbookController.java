package addressbook.sep.yt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import addressbook.sep.yt.dto.AddRequest;
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
        model.addAttribute("addForm", addForm);
        return "addressbook/add";
    }

    /**
     * @param addForm
     * @param result
     * @param model
     * @return (バリデーションエラー有)新規登録ページ / (バリデーションエラー無)新規登録確認ページ
     */
    @RequestMapping(value = "/addressbook/add", method = RequestMethod.POST)
    public String checkAddRequest(@ModelAttribute("addForm") @Validated AddForm addForm, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            model.addAttribute("addForm", addForm);
            /**
             * Categoryエンティティのリスト
             */
            List<Category> categoryList = addressbookService.showCategory();
            model.addAttribute("categorylist", categoryList);
           return "addressbook/add";
        }
        ModelMap modelMap = new ModelMap();

        AddRequest addRequest = new AddRequest();
        addRequest.setName(addForm.getName());
        addRequest.setAddress(addForm.getAddress());
        addRequest.setPhone(addForm.getPhone());
        addRequest.setCategory(addForm.getCategoryId());

        modelMap.addAttribute("addRequest", addRequest);
        redirectAttributes.addFlashAttribute("model", modelMap);

        return "redirect:/addressbook/add_confirm";
    }

    @RequestMapping(value = "/addressbook/add_confirm")
    public String displayAddConfirm(Model model) {
        return "addressbook/add_confirm";
    }

}
