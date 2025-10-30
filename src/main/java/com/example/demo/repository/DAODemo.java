package com.example.demo.repository;

/**
 *
 *  SQL
 * execute SQL ? springboot
 *
 *
 * SpringBoot  <Connector>           DB
 *              username/password
 *
 * JDBC -> Connection ->
 *
 * Connection instantiation
 * String sql = "SELECT * FROM TABLE1 WHERE PRIMARYKEY=?";
 * connection.executeQuery(sql);
 * (DB response)ResultSet  rs = connection.executeQuery(sql);
 *
 * User table
 * userId, firstName, LastName ...
 * Long userid = rs.get("userId");
 *
 *
 *
 *
 *
 *
 * 
 *
 * ORM -> Object Relational Mapping
 *
 * * Connection instantiation
 *
 * User Model -> insert, delete update
 *      conection.getUserById() -> Hibernate -> SELECT * FROM USER WHERE userID=? ;
 * * (DB response)User user = connection.executeQuery(sql);
 *
 * Employee e = connection.executeQuery(sql);
 *
 * Order order = connection.executeQuery(sql);
 *
 * @NativeQuery("SELECT * LEFT JOIN table 1 on table 2 where table1.key1= table2.key2)
 * ORM -> hibernate, MyBatis, MYBtisPLus, Alchemy
 *
 *
 *
 *
 *
 *
 * SpringBoot -> Spring data JPA -> Hibernete -> JDBC
 *
 *
 */


public class DAODemo {
}
