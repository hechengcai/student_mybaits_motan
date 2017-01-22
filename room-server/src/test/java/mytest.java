import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.student.room.server.impl.User;
import com.student.room.server.mapper.ColumnsMapper;
import com.student.room.server.mapper.MybatisHelper;
import com.student.room.server.mapper.UserMapper;
import com.student.room.service.model.ColumnsInfo;
import com.student.room.service.table.model.UserInfo;

import tk.mybatis.mapper.entity.Example;
 
//@Configuration({"classpath*:/motan_server.xml"})      
public class mytest {

	  @Autowired
	    private SqlSession sqlSession;
	 @Test
	    public void testSelectByIds() {
		 UserMapper countryMapper = sqlSession.getMapper(UserMapper.class);
	        Example example = new Example(UserInfo.class);
	        example.createCriteria().andGreaterThan("id",100);
	        PageHelper.startPage(2,10);
	        List<UserInfo> countries = countryMapper.selectByExample(example);
				 
	    }
}
