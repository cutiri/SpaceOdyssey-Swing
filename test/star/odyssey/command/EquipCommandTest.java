package star.odyssey.command;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EquipCommandTest extends BaseTest {

    @Before
    public void localSetUp(){
        testCommand = new EquipCommand(testGameState);
    }

    @Test
    public void testExecuteMethod_ShouldPass_WhenPassedAValidWeaponNameAsAString() {
        // pick up a weapon in this case the Gravity Hammer
        testGameState.getPlayer().getInventory().add(iManager.getItem("gravity_hammer"));
        // make sure the Gravity Hammer is in the player's inventory
        if(testGameState.getPlayer().getInventory().get(0).getName().equals("Gravity Hammer")){
            // equip the Gravity Hammer and make sure the player's equipped weapon is now the Gravity Hammer
            testCommand.execute("Gravity Hammer");
            // make sure the player's equipped weapon is now the Gravity Hammer
            assertTrue(testGameState.getPlayer().getEquippedWeapon().getName().equals("Gravity Hammer"));
        }
    }

    @Test
    public void testExecuteMethod_ShouldPassWithAnErrorMessage_WhenPassedAnInvalidWeaponName(){
        // try to equip a weapon that is not in the player's inventory
        assertTrue(testCommand.execute("Gravity Hammer").equals("Weapon not found in inventory."));
    }
}