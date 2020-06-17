package addressbook.sep.yt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import addressbook.sep.yt.entity.Addressbook;
import addressbook.sep.yt.entity.Category;
import addressbook.sep.yt.form.Form;
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
    @RequestMapping(value = "/addressbook/list", method = RequestMethod.GET)
    public String displayList(Model model) {
        List<Addressbook> addressbookList = addressbookService.searchAll();
        model.addAttribute("addressbooklist", addressbookList);
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
        System.out.println(category.getCategoryName());
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

    @RequestMapping(value = "/addressbook/modify/{id}")
    public String displayModify(@PathVariable int id, Model model) {
    	Addressbook addressbook = addressbookService.showSelectedAddressbook(id);

    	/**
    	 * Formクラスを住所録編集用Form(modifyForm)として使用する
    	 */
    	Form modifyForm = new Form();
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

    @RequestMapping(value = "/addressbook/modify", method = RequestMethod.POST)
    public String checkModifyRequest(@ModelAttribute("modifyForm") @Validated Form modifyForm, BindingResult result, Model model) {
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
        System.out.println(category.getCategoryName());
        model.addAttribute("modifyForm", modifyForm);
        return "addressbook/modify_confirm";
    }
}
