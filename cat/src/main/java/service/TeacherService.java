package service;


import cn.edu.sdjzu.xg.bysj.dao.TeacherDao;
import cn.edu.sdjzu.xg.bysj.domain.Teacher;

import java.sql.SQLException;
import java.util.Collection;

public final class TeacherService {
	private static TeacherDao teacherDao= TeacherDao.getInstance();
	private static TeacherService teacherService=new TeacherService();
	private TeacherService(){}
	
	public static TeacherService getInstance(){
		return teacherService;
	}
	
	public Collection<Teacher> findAll() throws SQLException {
		return teacherDao.findAll();
	}
	
	public Teacher find(Integer id) throws SQLException {
		return teacherDao.find(id);
	}

	public Teacher findToNo(String no) throws SQLException {
		return teacherDao.findToNo(no);
	}
	
	public boolean update(Teacher teacher) throws SQLException {
		return teacherDao.update(teacher);
	}
	
	public boolean add(Teacher teacher) throws SQLException {
		return teacherDao.add(teacher);
	}

	public boolean delete(Integer id) throws SQLException {
		return teacherDao.delete(id);
	}
}
