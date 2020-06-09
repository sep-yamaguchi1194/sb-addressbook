package addressbook.sep.yt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import addressbook.sep.yt.entity.Addressbook;

@Repository
public interface AddressbookRepository extends JpaRepository<Addressbook, Integer> {

}
