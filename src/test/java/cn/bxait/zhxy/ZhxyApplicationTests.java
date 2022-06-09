package cn.bxait.zhxy;

import cn.bxait.zhxy.util.MD5;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

@SpringBootTest
class ZhxyApplicationTests {

    @Autowired
    private DataSource dataSource;


    @Test
    void contextLoads() throws SQLException {

        Connection connection = dataSource.getConnection();

        System.out.println(connection);
    }


    @Test
    void md5Text(){

        String encrypt = MD5.encrypt("admin");
        System.out.println("123456的加密密码是"+encrypt);
    }


    @Test
    void upload(){

        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //String name = multipartFile.getOriginalFilename();
        String name  = "Capture001.png";
        String filename = uuid.concat(name);
        String portraitPath = "D:/IdeaProjects/zhxy/target/classes/public/upload/".concat(filename);
        System.out.println("图片路径是"+portraitPath);
    }

}
