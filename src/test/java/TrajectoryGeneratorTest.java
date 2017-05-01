import com.lusichong.simulation.MobileObjectSimulation;
import com.lusichong.util.Const;
import org.junit.Test;

/**
 * Created by lusichong on 2017/4/29 17:25.
 */
public class TrajectoryGeneratorTest {
    @Test
    public void testGenerate() {
        MobileObjectSimulation simulation = new MobileObjectSimulation();
        simulation.initSimulation();
        simulation.generateTrace(Const.BEIJING_MAP_TRAJECTORY_CONFIG);
    }

}
