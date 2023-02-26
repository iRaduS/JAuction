package Services;

import Entities.UserEntity;

public class UserService extends CrudService<UserEntity> {
    private static UserService userServiceInstance;

    private UserService() {
        this.dbTable = UserEntity.dbTable;
    }

    public static UserService getInstance() {
        if (userServiceInstance == null) {
            userServiceInstance = new UserService();
        }

        return userServiceInstance;
    }
}
