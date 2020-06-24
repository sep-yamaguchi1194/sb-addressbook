package addressbook.sep.yt.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import addressbook.sep.yt.entity.Addressbook;
import addressbook.sep.yt.entity.Category;
import addressbook.sep.yt.form.Form;
import addressbook.sep.yt.form.ModifyForm;
import addressbook.sep.yt.repository.AddressbookRepository;
import addressbook.sep.yt.repository.CategoryRepository;

@Service
@Transactional(rollbackOn = Exception.class)
public class AddressbookService {
    @Autowired
    AddressbookRepository addressbookRepository;

    @Autowired
    CategoryRepository categoryRepository;

    /**
     * 未削除住所録レコードを返す
     * @param pageable
     * @return
     */
    public Page<Addressbook> showINotDeletedAddressbooks(Pageable pageable) {
        return addressbookRepository.findByAbIsDeletedOrderByAbId("0", pageable);
    }

    /**
     * 指定の住所キーワードを含み、かつ未削除の住所録レコードを返す
     * @param address(検索ワード)
     * @param pageable
     * @return
     */
    public Page<Addressbook> searchIsNotDeletedAddressbooks(String address, Pageable pageable) {
        return addressbookRepository.findByAbAddressContainingAndAbIsDeletedOrderByAbId(address, "0", pageable);
    }

    /**
     * 全カテゴリレコードを返す
     * @return
     */
    public List<Category> showCategory() {
        return categoryRepository.findAll();
    }

    /**
     * 指定のIDの住所録レコードを返す
     * @param abId
     * @return
     */
    public Addressbook showSelectedAddressbook(int abId) {
        return addressbookRepository.findById(abId).get();
    }

    /**
     * 指定のIDのカテゴリレコードを返す
     * @param categoryId
     * @return
     */
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
        if (addForm.getPhone() != null) {
            addressbook.setAbPhone(addForm.getPhone().replace("-", ""));
        } else {
            addressbook.setAbPhone(addForm.getPhone());
        }
        addressbook.setAbCategoryId(addForm.getCategoryId());
        addressbook.setAbIsDeleted("0");
        addressbookRepository.save(addressbook);
    }

    /**
     * 電話番号にハイフンを追加するメソッド
     * @param phone
     */
    public String appendHyphen(String phone) {
        if (phone.isEmpty()) {
            return phone;
        } else {
            StringBuilder phoneAppendedHyphen = new StringBuilder(phone);
            phoneAppendedHyphen.insert(3, "-").insert(8, "-");
            return phoneAppendedHyphen.toString();
        }
    }

    /**
     * 住所録レコードを更新するメソッド
     * @param modifyForm 編集用form
     */
    public void update(ModifyForm modifyForm) {
        Addressbook targetAddressbook = showSelectedAddressbook(modifyForm.getId());
        targetAddressbook.setAbName(modifyForm.getName());
        targetAddressbook.setAbAddress(modifyForm.getAddress());
        if(modifyForm.getPhone() != null) {
            targetAddressbook.setAbPhone(modifyForm.getPhone().replace("-", ""));
        } else {
            targetAddressbook.setAbPhone(modifyForm.getPhone());
        }
        targetAddressbook.setAbCategoryId(modifyForm.getCategoryId());
        addressbookRepository.saveAndFlush(targetAddressbook);
    }

    /**
     * 住所録レコードを論理削除するメソッド
     * @param id 対象レコードID
     */
    public void logicalDelete(int id) {
        Addressbook targetAddressbook = showSelectedAddressbook(id);
        targetAddressbook.setAbIsDeleted("1");
        addressbookRepository.saveAndFlush(targetAddressbook);
    }
}
