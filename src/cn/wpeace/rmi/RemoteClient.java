package cn.wpeace.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class RemoteClient {
	public static void main(String[] args) {
		try {
			/**
			 * 客户端通过loockup找到对应的服务对象。
			 * 
			 */
			StudentService service=(StudentService)Naming.lookup("rmi://192.168.1.112:1099/hello");
			//可以进行方法调用
			List<Student> allStudent = service.getAllStudent();
			for(Student student:allStudent)
			{
				System.out.println("name :"+student.getName());
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
}
