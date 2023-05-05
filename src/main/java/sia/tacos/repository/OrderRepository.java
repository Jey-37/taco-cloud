package sia.tacos.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacos.model.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long>
{
}
