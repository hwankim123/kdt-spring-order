package org.prgrms.kdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.sql.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private static final String SELECT_SQL = "select * from customers WHERE name = ?";
    private static final String INSERT_SQL = "insert into customers(customerId, name, email) VALUES(UUID_TO_BIN(?), ?, ?)";
    private static final String DELETE_ALL_SQL = "DELETE FROM customers";
    private static final String UPDATE_SQL = "UPDATE customers SET name = ? WHERE customerId = ?";

    private List<String> findAll(){
        List<String> names = new ArrayList<>();

        try ( // try resource 는 autoclosable을 구현한 내용이 들어가야 됨.얘네 셋다 이 구현해놓음. 끝나거나, 예외가 발생하면 알아서 닫힘
              Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/order_mgmt", "root", "cjsak123");
              PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL);
              ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while(resultSet.next()){
                String customerName = resultSet.getString("name");
                UUID customerId = toUUID(resultSet.getBytes("customerId"));
                LocalDateTime createAt = resultSet.getTimestamp("create_at").toLocalDateTime();
                logger.info("customer name -> {} / UUID -> {} / createdAt -> {}", customerName, customerId, createAt);
                names.add(customerName);
            }
        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }

        return names;
    }

    private List<String> findNames(String name){
        List<String> names = new ArrayList<>();

        try ( // try resource 는 autoclosable을 구현한 내용이 들어가야 됨.얘네 셋다 이 구현해놓음. 끝나거나, 예외가 발생하면 알아서 닫힘
              Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/order_mgmt", "root", "cjsak123");
              PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL);
        ) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String customerName = resultSet.getString("name");
                UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customerId"));
                LocalDateTime createAt = resultSet.getTimestamp("create_at").toLocalDateTime();
                logger.info("customer name -> {} / UUID -> {} / createdAt -> {}", customerName, customerId, createAt);
                names.add(customerName);
            }
        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }

        return names;
    }

    private int updateCustomerName(UUID customerId, String name){
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/order_mgmt", "root", "cjsak123");
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setBytes(2, customerId.toString().getBytes());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
        return 0;
    }

    private int insertCustomer(UUID customerId, String name, String email){
        try (
              Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/order_mgmt", "root", "cjsak123");
              PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
        ) {
             preparedStatement.setBytes(1, customerId.toString().getBytes());
             preparedStatement.setString(2, name);
             preparedStatement.setString(3, email);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
        return 0;
    }

    private int deleteAll(){
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/order_mgmt", "root", "cjsak123");
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_SQL);
        ) {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
        return 0;
    }

    //코드에서 UUID 관련 문제가 있음. github 코드 참고
    static UUID toUUID(byte[] bytes){
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        return new UUID(wrap.getLong(), wrap.getLong());
    }

    public static void main(String[] args) throws SQLException {
        JdbcCustomerRepository jdbcCustomerRepository = new JdbcCustomerRepository();
        List<String> resultNames = jdbcCustomerRepository.findNames("test01");
        jdbcCustomerRepository.insertCustomer(UUID.randomUUID(), "newUser", "newUser@gmail.com");
        int i = jdbcCustomerRepository.deleteAll();
        System.out.println("i = " + i);
        resultNames.forEach(System.out::println);
    }
}
