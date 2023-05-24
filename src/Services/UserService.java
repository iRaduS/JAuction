package Services;

import Bootstrappers.DatabaseBootstrapper;
import Entities.BidderEntity;
import Entities.SellerEntity;
import Entities.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService extends CrudService<UserEntity> {
    private static final String passwordSuffix = "MdJ@cT!oNJ@v";
    private static final String passwordPrefix = "JDk!c$@231";
    private static UserService userServiceInstance;

    private final DatabaseBootstrapper databaseInstance;

    private UserService(DatabaseBootstrapper databaseInstance) {
        this.dbTable = UserEntity.dbTable;
        this.databaseInstance = databaseInstance;
    }

    public static UserService getInstance(DatabaseBootstrapper databaseInstance) {
        if (userServiceInstance == null) {
            userServiceInstance = new UserService(databaseInstance);
        }

        return userServiceInstance;
    }

    public UserEntity loginUser(String email, String password) throws Exception {
        Connection connection = this.databaseInstance.getConnectionInstance();
        password = passwordPrefix + password + passwordSuffix;

        String query = "SELECT * FROM " + this.dbTable + " WHERE email = ? AND password = SHA1(?) LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            throw new Exception("[AUTH]: No account found with this password or email.");
        }

        return this.returnCorrectType(preparedStatement, resultSet);
    }

    public UserEntity getUserById(Long id) throws Exception {
        Connection connection = this.databaseInstance.getConnectionInstance();

        String query = "SELECT * FROM " + this.dbTable + " WHERE id = ? LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            throw new Exception("No account with this ID.");
        }

        return this.returnCorrectType(preparedStatement, resultSet);
    }

    private UserEntity returnCorrectType(PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
        if (resultSet.getLong("type") == 1) {
            return new SellerEntity(
                    resultSet.getLong("id"), resultSet.getString("name"),
                    resultSet.getString("email"), resultSet.getString("password"),
                    resultSet.getString("phone")
            );
        }

        return new BidderEntity(
                resultSet.getLong("id"), resultSet.getString("name"),
                resultSet.getString("email"), resultSet.getString("password"),
                resultSet.getString("phone")
        );
    }
}
