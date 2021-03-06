package com.bd.dao.impl;

import java.sql.*;

public class BaseDao {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public Connection getConnection() {

        String url = "jdbc:mysql://localhost:3306/ExamSystem?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true";
        String user = "root";
        String password = "root";
        //1、加载驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeAll() {
        try {
            if (resultSet!=null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int executeUpdate(String sql) {
        int result = -1;
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            result = preparedStatement.executeUpdate();
            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int excuteUpdate(String sql, Object[] objs) {
        int result = -1;
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            if (objs != null) {
                for (int i = 0; i < objs.length; i++) {
                    preparedStatement.setObject(i+1,objs[i]);
                }
            }
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeAll();
        return result;
    }

    public ResultSet excuteQuery(String sql){
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet excuteQuery(String sql,Object[] objs){
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            if (objs != null) {
                for (int i = 0; i < objs.length; i++) {
//                    System.out.println("param:"+objs[i]);
                    preparedStatement.setObject(i+1, objs[i]);
                }
            }
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}
