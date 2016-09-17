//命名空间定义：java包
namespace java cn.wpeace.thrift
//结构体定义：转化java中的实体类
struct Request{
      1:required string userName;
      2:required string password;
}
//定义返回类型
struct Student{
        1:required string naem;
        2:required i32 age;
}
struct People{
        1:required string naem;
        2:required i32 age;
        3:required string sex;
}
//异常描述定义
exception HelloException{
       1:required string msg;
}
//服务定义，生成接口用
service StudentService{
			list<Student> getAllStudent(1:Request request)throws (1:HelloException e);
}
//服务定义，生成接口用
service PeopleService{
			list<People> getAllPeople(1:Request request)throws (1:HelloException e);
}
//thrift -gen java ./hello.thrift