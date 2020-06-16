package addressbook.sep.yt.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * Addressbook(住所録)エンティティ
 * テーブルの物理名:addressbook
 */
@Table(name = "addressbook")
@Entity
@Data
public class Addressbook implements Serializable {

    /**
     * 住所録のID
     */
    @Id
    @Column(name = "addressbook_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int abId;

    /**
     * 住所録の名前
     */
    @Column(name = "addressbook_name")
    private String abName;

    /**
     * 住所録の住所
     */
    @Column(name = "addressbook_address")
    private String abAddress;

    /**
     * 住所録の電話番号
     */
    @Column(name = "addressbook_phone")
    private String abPhone;

    /**
     * 住所録のカテゴリID
     */
    @Column(name = "addressbook_category_id")
    private String abCategoryId;

    /**
     * 住所録が論理削除済みであるか('0':未 / '1':済)
     */
    @Column(name = "addressbook_is_deleted")
    private String abIsDeleted;

    /**
     * Categoryエンティティと結合
     * Categoryエンティティを参照している(Addressbook.addressbook_category_id = Category.category_id)ので、
     * AddressbookエンティティからはCategoryエンティティへのinsert, updateを許可しない
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressbook_category_id", referencedColumnName = "category_id", insertable = false, updatable = false)
    private Category category;
}
