package com.iqvia.dao;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.iqvia.model.BatchItem;

public class BatchItemServices {

	public List<BatchItem> getBatchItems() throws IOException {
		List<BatchItem> result = null;
		Reader reader = Resources.getResourceAsReader("mybatis.xml");
		Connection connection = null;
		SqlSession session = null;
		try {
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "development");
			connection = getConnection();
			session = sqlSessionFactory.openSession(connection);
			result = session.selectList("com.iqvia.dao.BatchItemsMapper.selectBatchItems");
			session.close();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			if (session != null) {
				session.close();
			}
		}
		return result;
	}

	private Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection connection = null;
		String url = "jdbc:mysql://localhost:3306/iqvia";
		String user = "iqvia-admin";
		String password = "2dEt7j@";
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(url, user, password);
		return connection;

	}

}
