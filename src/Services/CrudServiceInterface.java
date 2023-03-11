package Services;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface CrudServiceInterface<Entity> {
    public void create(Connection connection, Map<String, ?> dataToFill);

    public Entity read(Long id);

    public List<Entity> getAll();

    public void update(Long id, Map<String, ?> dataToFill);

    public void delete(Long id);
}
