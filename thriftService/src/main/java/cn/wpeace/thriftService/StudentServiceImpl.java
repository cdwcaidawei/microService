package cn.wpeace.thriftService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import cn.wpeace.thrift.HelloException;
import cn.wpeace.thrift.Request;
import cn.wpeace.thrift.Student;
import cn.wpeace.thrift.StudentService;
import cn.wpeace.thrift.StudentService.Iface;
import cn.wpeace.thrift.StudentService.Processor;

public class StudentServiceImpl implements Iface {// 实现的是StudentService类下面的接口

	@Override
	public List<Student> getAllStudent(Request request) throws HelloException, TException {
		System.out.println("调用studentService");
		System.out.println(request.getUserName());
		System.out.println(request.getPassword());
		List<Student> students = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Student student = new Student();
			student.setNaem("peace" + i);
			student.setAge(22 + i);
			students.add(student);
		}
		return students;
	}
}
