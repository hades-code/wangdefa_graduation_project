import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lhq.FileServiceApplication;
import org.lhq.entity.Directory;
import org.lhq.entity.UserFile;
import org.lhq.service.DirectoryService;
import org.lhq.service.UserFileService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.LinkedList;

@SpringBootTest(classes = FileServiceApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class FileServiceTest {
    @Resource
    UserFileService userFileService;
    @Resource
    DirectoryService directoryService;
    @Test
    public void addUserFile(){
        UserFile userFile = new UserFile();
        userFile.setUserId(1337064620415483905L);
        userFile.setDirectoryId(1350503432093126658L);
        userFile.setFileName("大鳥轉轉轉");
        userFile.setFileType("mp4");
        userFileService.save(userFile);
    }
    @Test
    public void treeDir(Directory root){
        if (root == null){
            return;
        }
        LinkedList<Directory> linkedList = new LinkedList<>();
        linkedList.add(root);
    }
}
