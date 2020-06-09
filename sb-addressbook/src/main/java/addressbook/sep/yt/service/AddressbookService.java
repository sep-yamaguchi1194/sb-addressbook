package addressbook.sep.yt.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import addressbook.sep.yt.entity.Addressbook;
import addressbook.sep.yt.entity.Category;
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

}
