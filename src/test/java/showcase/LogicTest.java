package showcase;

import org.junit.Test;
import static org.junit.Assert.*;
import showcase.panels.CipherPanel;
import showcase.panels.TripPlannerPanel;
import java.lang.reflect.Method;

public class LogicTest {

    @Test
    public void testCipherLogic() throws Exception {
        // Since methods are private in Panels, we would normally make them package-private for testing
        // For now, we will test the public behavior if possible, or use reflection for deep verification
        
        System.out.println("Testing Cipher Logic...");
        CipherPanel panel = new CipherPanel();
        
        // Use reflection to access the private helper methods
        Method caesar = CipherPanel.class.getDeclaredMethod("caesar", String.class, int.class);
        caesar.setAccessible(true);
        
        String input = "HELLO";
        String encrypted = (String) caesar.invoke(panel, input, 1);
        assertEquals("IFMMP", encrypted);
        
        String inputZoom = "ZOO";
        String encryptedZoom = (String) caesar.invoke(panel, inputZoom, 1);
        assertEquals("APP", encryptedZoom);
        
        Method group = CipherPanel.class.getDeclaredMethod("group", String.class, int.class);
        group.setAccessible(true);
        
        String raw = "HELLOTHERE";
        String grouped = (String) group.invoke(panel, raw, 5);
        assertEquals("HELLO THERE", grouped);
    }

    @Test
    public void testTripPlannerMath() throws Exception {
        System.out.println("Testing Haversine Formula...");
        TripPlannerPanel panel = new TripPlannerPanel();
        
        Method haversine = TripPlannerPanel.class.getDeclaredMethod("haversine", double.class, double.class, double.class, double.class);
        haversine.setAccessible(true);
        
        // Distance roughly between New York (40.7128, -74.0060) and London (51.5074, -0.1278) -> ~5570km
        double dist = (double) haversine.invoke(panel, 40.7128, -74.0060, 51.5074, -0.1278);
        assertTrue(dist > 5500 && dist < 5600);
    }
}
