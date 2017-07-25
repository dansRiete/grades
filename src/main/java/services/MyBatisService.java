package services;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Aleks on 25.07.2017.
 */
public class MyBatisService {

    private static SqlSessionFactory sqlSessionFactory;

    private static void initFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static SqlSessionFactory getSqlSessionFactory() throws IOException {
        if(sqlSessionFactory == null){
            initFactory();
        }
        return sqlSessionFactory;
    }
}
