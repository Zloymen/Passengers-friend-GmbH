import com.passengers.friend.data.entity.DebriefingTask;
import com.passengers.friend.data.service.DebriefingService;
import com.passengers.friend.producer.AppProducer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppProducer.class)
@Slf4j
public class TestProducer extends Assert {

    @Autowired
    private DebriefingService service;

    @Test
    public void testTask() throws Exception {
        Page<DebriefingTask> page = service.getNewTask();
        assertNotNull(page);
    }
}
