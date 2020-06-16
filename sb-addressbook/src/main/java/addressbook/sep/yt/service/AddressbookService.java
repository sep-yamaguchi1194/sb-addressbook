package addressbook.sep.yt.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import addressbook.sep.yt.entity.Addressbook;
import addressbook.sep.yt.entity.Category;
import addressbook.sep.yt.form.Form;
import addressbook.sep.yt.repository.AddressbookRepository;
import addressbook.sep.yt.repository.CategoryRepository;

@Service
@Transactional(rollbackOn = Exception.class)
public class AddressbookService {
    @Autowired
    AddressbookRepository addressbookRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public List<Addressbook> searchAll() {
        return addressbookRepository.findAll();
    }

    public List<Category> showCategory() {
        return categoryRepository.findAll();
    }

    public Category showSelectedCategory(String categoryId) {
        return categoryRepository.findById(categoryId).get();
    }

    /**
     * 住所録テーブルへ新規登録を行うメソッド
     * @param form
     */
    public void create(Form addForm) {
        /**
         * 住所録エンティティ
         */
        Addressbook addressbook = new Addressbook();
        addressbook.setAbName(addForm.getName());
        addressbook.setAbAddress(addForm.getAddress());
        /**
         * 電話番号からハイフンを除去
         */
        if(addForm.getPhone() != null) {
            addressbook.setAbPhone(addForm.getPhone().replace("-", ""));
        } else {
            addressbook.setAbPhone(addForm.getPhone());
        }
        addressbook.setAbCategoryId(addForm.getCategoryId());
        addressbook.setAbIsDeleted("0");
        addressbookRepository.save(addressbook);
    }
}
