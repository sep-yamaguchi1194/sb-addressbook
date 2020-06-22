package addressbook.sep.yt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import addressbook.sep.yt.entity.Addressbook;
import addressbook.sep.yt.entity.Category;
import addressbook.sep.yt.form.Form;
import addressbook.sep.yt.form.ModifyForm;
import addressbook.sep.yt.service.AddressbookService;

@Controller
public class AddressbookController {
    @Autowired
    AddressbookService addressbookService;

    /**
     * 住所録一覧画面を表示する
     * @param model
     * @return 住所録一覧ページ
     */
    /*
    @RequestMapping(value = "/addressbook/list", method = RequestMethod.GET)
    public String displayList(Model model) {
        List<Addressbook> addressbookList = addressbookService.searchIsNotDeleted();
        model.addAttribute("addressbooklist", addressbookList);
        return "addressbook/list";
    }
    */

    @GetMapping(value = "/addressbook/list")
    public String displayList(@PageableDefault(page = 0, size = 10)Pageable pageable, Model model) {
        Page<Addressbook> addressbookPage = addressbookService.getAddressbooks(pageable);

        model.addAttribute("page", addressbookPage);
        model.addAttribute("addressbooks", addressbookPage.getContent());

        return "addressbook/list";
    }

    /**
     * 住所録新規登録画面を表示する
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
         * Formクラスを新規登録用Form(addForm)として使用する
         */
        Form addForm = new Form();
        model.addAttribute("addForm", addForm);
        return "addressbook/add";
    }

    /**
     * 住所録新規登録画面のFormに入力された値が妥当かチェックを行う
     * @param addForm
     * @param result
     * @param model
     * @return (バリデーションエラー有)新規登録ページ / (バリデーションエラー無)新規登録確認ページ
     */
    @RequestMapping(value = "/addressbook/add", method = RequestMethod.POST)
    public String checkAddRequest(@ModelAttribute("addForm") @Validated Form addForm, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("addForm", addForm);
            /**
             * Categoryエンティティのリスト
             */
            List<Category> categoryList = addressbookService.showCategory();
            model.addAttribute("categorylist", categoryList);
           return "addressbook/add";
        }
        /*
         * Redirectを使って実装する方法が見つかった場合再考する
        ModelMap modelMap = new ModelMap();

        modelMap.addAttribute("addForm", addForm);
        redirectAttributes.addFlashAttribute("model", modelMap);

        return "redirect:/addressbook/add_confirm";
        */

        /**
         * FormフィールドのcategoryIdに対応するCategoryエンティティの取得
         */
        Category category = addressbookService.showSelectedCategory(addForm.getCategoryId());
        model.addAttribute("category", category);
        model.addAttribute("addForm", addForm);
        return "addressbook/add_confirm";
    }

    /*
     * 現状、add_confirmへのRequestMappingは不要。この先必要な場合に備えてコメントアウトしておく。

     * 住所録新規登録確認画面を表示する
     * @param addForm
     * @param model
     * @return 住所録新規登録確認画面

    @RequestMapping(value = "/addressbook/add_confirm")
    public String displayAddConfirm(@ModelAttribute("addForm") Form addForm, Model model) {
        //model.addAttribute("addForm", addForm);

        return "addressbook/add_confirm";
    }
    */

    /**
     * 住所録新規登録確認画面からデータベースへ新規登録を行う
     * @param addForm
     * @param result
     * @param model
     * @return (バリデーションエラー有)新規登録ページ / (バリデーションエラー無)住所録一覧画面へリダイレクト
     */
    @RequestMapping(value = "/addressbook/create", method = RequestMethod.POST)
    public String createAddressbook(@ModelAttribute("addForm") @Validated Form addForm, BindingResult result, Model model) {
        /**
         * hiddenフィールドは改竄の可能性があるため、再度バリデーションエラーをチェックする
         */
        if(result.hasErrors()) {
            model.addAttribute("addForm", addForm);
            /**
             * Categoryエンティティのリスト
             */
            List<Category> categoryList = addressbookService.showCategory();
            model.addAttribute("categorylist", categoryList);
           return "addressbook/add";
        }
        /**
         * AddressbookServiceクラスのcreateメソッドでデータベースへ新規登録
         * 終了後、住所録一覧画面へリダイレクト
         */
        addressbookService.create(addForm);
        return "redirect:/addressbook/list";
    }

    @RequestMapping(value = "/addressbook/modify", method = RequestMethod.POST)
    public String displayModify(@RequestParam("id")int id, Model model) {
        Addressbook addressbook = addressbookService.showSelectedAddressbook(id);

        /**
         * 住所録編集画面用のform(ModifyFormクラス)
         */
        ModifyForm modifyForm = new ModifyForm();
        modifyForm.setId(addressbook.getAbId());
        modifyForm.setName(addressbook.getAbName());
        modifyForm.setAddress(addressbook.getAbAddress());
        //AddressbookエンティティのabPhone(電話番号フィールド)にハイフンを追加する
        modifyForm.setPhone(addressbookService.appendHyphen(addressbook.getAbPhone()));
        modifyForm.setCategoryId(addressbook.getAbCategoryId());
        model.addAttribute("modifyForm", modifyForm);

        /**
         * Categoryエンティティのリスト
         */
        List<Category> categoryList = addressbookService.showCategory();
        model.addAttribute("categorylist", categoryList);
        return "addressbook/modify";
    }

    @RequestMapping(value = "/addressbook/modify_confirm", method = RequestMethod.POST)
    public String checkModifyRequest(@ModelAttribute("modifyForm") @Validated ModifyForm modifyForm, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("modifyForm", modifyForm);
            /**
             * Categoryエンティティのリスト
             */
            List<Category> categoryList = addressbookService.showCategory();
            model.addAttribute("categorylist", categoryList);
           return "addressbook/modify";
        }

        Category category = addressbookService.showSelectedCategory(modifyForm.getCategoryId());
        model.addAttribute("category", category);
        model.addAttribute("modifyForm", modifyForm);
        return "addressbook/modify_confirm";
    }

    @RequestMapping(value = "/addressbook/update", method = RequestMethod.POST)
    public String updateAddressbook(@ModelAttribute("modifyForm") @Validated ModifyForm modifyForm, BindingResult result, Model model) {
        /**
         * hiddenフィールドは改竄の可能性があるため、再度バリデーションエラーをチェックする
         */
        if(result.hasErrors()) {
            model.addAttribute("modifyForm", modifyForm);
            /**
             * Categoryエンティティのリスト
             */
            List<Category> categoryList = addressbookService.showCategory();
            model.addAttribute("categorylist", categoryList);
           return "addressbook/modify";
        }
        /**
         * AddressbookServiceクラスのupdateメソッドでデータベースへ更新処理
         * 終了後、住所録一覧画面へリダイレクト
         */
        addressbookService.update(modifyForm);
        return "redirect:/addressbook/list";
    }

    @PostMapping("/addressbook/delete_confirm")
    public String displayDeleteConfirm(@RequestParam("id")int id, Model model) {
        Addressbook addressbook = addressbookService.showSelectedAddressbook(id);
        model.addAttribute("addressbook", addressbook);
        return "addressbook/delete_confirm";
    }

    @PostMapping("/addressbook/delete")
    public String deleteAddressbook(@RequestParam("id")int id) {
        addressbookService.logicalDelete(id);
        return "redirect:/addressbook/list";
    }
}
