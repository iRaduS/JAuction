package Services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class CrudService<Entity> implements CrudServiceInterface<Entity> {
    protected String dbTable;

    @Override
    public Long create(Connection connection, Map<String, ?> dataToFill) {
        String query = "INSERT INTO " + this.dbTable + " (" +
                dataToFill.keySet().stream().map(Object::toString)
                        .collect(Collectors.joining(","))
                + ") VALUES (" +
                dataToFill.values().stream().map(value -> {
                    return "?";
                }).collect(Collectors.joining(","))
                + ")";

        Long idCreated = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < dataToFill.values().size(); i++) {
                if (dataToFill.values().toArray()[i] instanceof String) {
                    preparedStatement.setString(i + 1, dataToFill.values().toArray()[i].toString());
                }

                if (dataToFill.values().toArray()[i] instanceof Double || dataToFill.values().toArray()[i] instanceof Float) {
                    preparedStatement.setDouble(i + 1, ((Number) dataToFill.values().toArray()[i]).doubleValue());
                }

                if (dataToFill.values().toArray()[i] instanceof Long || dataToFill.values().toArray()[i] instanceof Integer) {
                    preparedStatement.setLong(i + 1, ((Number) dataToFill.values().toArray()[i]).longValue());
                }

                if (dataToFill.values().toArray()[i] instanceof ZonedDateTime) {
                    preparedStatement.setDate(i + 1, new Date(java.util.Date.from(((ZonedDateTime) dataToFill.values().toArray()[i]).toInstant()).getTime()));
                }
            }

            int successful = preparedStatement.executeUpdate();

            if (successful <= 0) {
                throw new Exception("can't create new entry in database");
            }

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                idCreated = rs.getLong(1);
            }
        } catch (Exception exception) {
            System.out.printf("[SQL]: An error was produced, %s\n", exception.getMessage());
        }

        return idCreated;
    }

    @Override
    public Entity read(Long id) {
        return null;
    }

    @Override
    public List<Entity> getAll() {
        return null;
    }

    @Override
    public void update(Long id, Map<String, ?> dataToFill) {

    }

    @Override
    public void delete(Connection connection, Long id) {
        String query = "DELETE FROM " + this.dbTable + " WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
    }
}
