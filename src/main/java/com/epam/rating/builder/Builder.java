package com.epam.rating.builder;

import com.epam.rating.entity.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Builder<T extends Identifiable> {
        T build(ResultSet resultSet) throws SQLException;
}
