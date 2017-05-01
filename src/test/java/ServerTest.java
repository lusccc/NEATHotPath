import com.lusichong.LogicModel;
import org.junit.Test;

/**
 * Created by lusichong on 2017/4/29 23:31.
 */
public class ServerTest {

    @Test
    public void testGetFlowCluster() {
        LogicModel model = new LogicModel();
        model.getFlowCluster();
    }
    @Test
    public void testGenerateTrajectory() {
        LogicModel model = new LogicModel();
        model.generateTrajectory(200);
    }

}
