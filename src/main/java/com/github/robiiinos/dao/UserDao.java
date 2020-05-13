package com.github.robiiinos.dao;

import com.github.robiiinos.datasource.ReadDataSource;
import com.github.robiiinos.datasource.WriteDataSource;
import com.github.robiiinos.model.User;
import com.github.robiiinos.request.UserLoginRequest;
import com.github.robiiinos.request.UserRegisterRequest;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import static jooq.generated.Tables.*;

public class UserDao {
    private static final SQLDialect dialect = SQLDialect.MYSQL;

    private static final DSLContext readContext = DSL.using(ReadDataSource.getDataSource(), dialect);
    private static final DSLContext writeContext = DSL.using(WriteDataSource.getDataSource(), dialect);

    public UserDao() {
    }

    public final User findByUsernameAndPassword(UserLoginRequest userRequest) {
        return readContext.select()
                .from(USERS)
                .where(USERS.USERNAME.eq(userRequest.getUsername()))
                .and(USERS.PASSWORD.eq(userRequest.getPassword()))
                .fetchOneInto(User.class);
    }

    public final int create(UserRegisterRequest userRequest) {
        return writeContext.insertInto(USERS)
                .set(USERS.FIRSTNAME, userRequest.getFirstName())
                .set(USERS.LASTNAME, userRequest.getLastName())
                .set(USERS.USERNAME, userRequest.getUsername())
                .set(USERS.PASSWORD, userRequest.getPassword())
                .execute();
    }
}
