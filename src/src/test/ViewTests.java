import net.sprauer.sitzplaner.view.ClassRoom;
import net.sprauer.sitzplaner.view.panels.StatisticsPanel;
import net.sprauer.sitzplaner.view.panels.ToolsPanel;

import org.junit.Test;

public class ViewTests {

	@Test
	public void testFakeUiTest() {
		new ToolsPanel();
		new StatisticsPanel();
		ClassRoom.instance();
	}

}
