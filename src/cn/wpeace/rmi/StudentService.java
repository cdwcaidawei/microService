package cn.wpeace.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
/**
 * 接口须继承Remote
 * 方法需要抛出RemoteException
 * @author peace
 *
 */
public interface StudentService extends Remote{
	public List<Student> getAllStudent()throws RemoteException;
}
