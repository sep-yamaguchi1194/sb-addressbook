package addressbook.sep.yt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import addressbook.sep.yt.entity.Addressbook;

@Repository
public interface AddressbookRepository extends JpaRepository<Addressbook, Integer> {
    public Page<Addressbook> findByAbIsDeletedOrderByAbId(String abIsDeleted, Pageable pageable);
    public Page<Addressbook> findByAbAddressContainingAndAbIsDeletedOrderByAbId(String abAddress,
            String abIsDeleted, Pageable pageable);
}
