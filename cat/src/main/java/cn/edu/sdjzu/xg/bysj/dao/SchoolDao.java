package cn.edu.sdjzu.xg.bysj.dao;

import cn.edu.sdjzu.xg.bysj.domain.School;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class SchoolDao {
	private static SchoolDao profTitleDao=new SchoolDao();
	private SchoolDao(){}
	public static SchoolDao getInstance(){
		return profTitleDao;
	}

	public Collection<School> findAll() throws SQLException{
		Set<School> schools = new HashSet<School>();
		Connection connection = JdbcHelper.getConn();
		Statement statement = connection.createStatement();
		//执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
		ResultSet resultSet = statement.executeQuery("select * from school");
		//若结果集仍然有下一条记录，则执行循环体
		while (resultSet.next()){
			//创建Degree对象，根据遍历结果中的id,description,no,remarks值
			School school = new School(resultSet.getInt("id"),resultSet.getString("description"),resultSet.getString("no"),resultSet.getString("remarks"));
			//向degrees集合中添加Degree对象
			schools.add(school);
		}
		return schools;
	}
	public School find(Integer id) throws SQLException {
		Set<School> schools = new HashSet<School>();
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		Statement statement = connection.createStatement();
		//执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
		ResultSet resultSet = statement.executeQuery("select * from school");
		//若结果集仍然有下一条记录，则执行循环体
		while (resultSet.next()){
			//创建Degree对象，根据遍历结果中的id,description,no,remarks值
			School school= new School(resultSet.getInt("id"),resultSet.getString("description"),resultSet.getString("no"),resultSet.getString("remarks"));
			//向degrees集合中添加Degree对象
			schools.add(school);
		}
		//关闭资源
		JdbcHelper.close(resultSet,statement,connection);
		School desiredSchool = null;
		for (School school : schools) {
			if(id.equals(school.getId())){
				desiredSchool =  school;
				break;
			}
		}
		return desiredSchool;
	}

	public boolean update(School school) throws SQLException {
//获得连接对象
		Connection connection = JdbcHelper.getConn();
		//创建sql语句，“？”作为占位符
		String addSchool_sql = "update school set no=?,description=?,remarks=? where id=?";
		//创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
		PreparedStatement pstmt = connection.prepareStatement(addSchool_sql);
		//为预编译的语句参数赋值
		pstmt.setString(1,school.getNo());
		pstmt.setString(2,school.getDescription());
		pstmt.setString(3,school.getRemarks());
		pstmt.setInt(4,school.getId());
		//执行预编译对象的executeUpdate()方法，获取增加记录的行数
		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("修改了 "+affectedRowNum+" 条");
		return affectedRowNum > 0;
	}

	public boolean delete(School school) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		//创建sql语句，“？”作为占位符
		String delete = "DELETE FROM school WHERE ID =?";
		PreparedStatement pstmt = connection.prepareStatement(delete);
		pstmt.setInt(1,school.getId());
		int delete1 = pstmt.executeUpdate();
		return delete1>0;
	}

	public boolean add(School school) throws SQLException {
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		//创建sql语句，“？”作为占位符
		String addSchool_sql = "INSERT INTO school(no,description,remarks) VALUES" + " (?,?,?)";
		//创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
		PreparedStatement pstmt = connection.prepareStatement(addSchool_sql);
		//为预编译的语句参数赋值
		pstmt.setString(1,school.getNo());
		pstmt.setString(2,school.getDescription());
		pstmt.setString(3,school.getRemarks());
		//执行预编译对象的executeUpdate()方法，获取增加记录的行数
		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("增加了 "+affectedRowNum+" 条");
		return affectedRowNum > 0;
	}
}

