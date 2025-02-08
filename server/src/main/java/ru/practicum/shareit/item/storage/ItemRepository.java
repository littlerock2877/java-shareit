package ru.practicum.shareit.item.storage;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;
import java.util.Collection;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select i from Item i where i.available = true " +
            " and (lower(i.name) like lower(concat('%',:query,'%')) " +
            " or lower(i.description) like lower(concat('%',:query,'%')))")
    Collection<Item> searchByQuery(@Param("query") String query);

    List<Item> findAllByOwnerId(Long userId);

    List<Item> getByRequestId(Long id, Sort sort);
}
