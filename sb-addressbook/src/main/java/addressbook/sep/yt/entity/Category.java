package addressbook.sep.yt.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Category(カテゴリ)エンティティ
 * テーブルの物理名：category
 */
@Table(name = "category")
@Entity
@Data
public class Category implements Serializable{

	/**
	 * カテゴリID
	 */
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String categoryId;

    /**
     * カテゴリ名
     */
    @Column(name = "category_name")
    private String categoryName;
}
