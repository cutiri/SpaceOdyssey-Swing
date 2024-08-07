package star.odyssey.character;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import star.odyssey.inventory.Item;
import star.odyssey.location.Location;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {
    Location location;
    Player player;
    Item itemRock;
    Item itemPaper;
    Item itemScissors;
    Item itemSpock;
    Item itemLizard;

    @Before
    public void setUp(){
        location = new Location("kitchen", "kitchen", "This is a kitchen", "This is a normal kitchen", new ArrayList<>(), new ArrayList<>(), "");
        itemRock = new Item("rock", "rock", "", "", true, true, false, true, false, "You use a rock", "kitchen");
        itemPaper = new Item("paper", "paper", "", "", true, true, false, true, false, "You use a rock", "kitchen");
        itemScissors = new Item("scissors", "scissors", "", "", true, true, false, true, false, "You use a rock", "kitchen");
        itemSpock = new Item("spock", "spock", "", "", true, true, false, true, false, "You use a rock", "kitchen");
        itemLizard = new Item("lizard", "lizard", "", "", true, true, false, true, false, "You use a rock", "kitchen");
        location.addInventory(itemRock);
        location.addInventory(itemPaper);
        location.addInventory(itemScissors);
        location.addInventory(itemSpock);
        //location.addInventory(itemLizard);
        player = new Player("index", "name",100, 50, 50, "This is the main character", location, new ArrayList<>(), true, null);
    }
    @Test
    public void getItem() {
        Assert.assertEquals(itemLizard.getName() + " has been successfully teleported to your inventory", player.getItem(itemLizard));
        Assert.assertEquals(itemRock.getName() + " has been successfully teleported to your inventory", player.getItem(itemRock));
        Assert.assertEquals(itemSpock.getName() + " has been successfully teleported to your inventory", player.getItem(itemSpock));
    }

    @Test
    public void dropItem() {
        Assert.assertEquals("The art of letting go is profound, but first it would help if you actually had " + itemSpock.getName(), player.dropItem(itemSpock.getName()));
        player.getItem(itemSpock);
        Assert.assertEquals( itemSpock.getName() + " has been dropped. It's now contemplating its life choices on the ground.", player.dropItem(itemSpock.getName()));
    }

    @Test
    public void useItem() {
        Assert.assertEquals(itemRock.getName() + " must be in your inventory to be used.", player.useItem(itemRock));
        player.getItem(itemRock);
        Assert.assertEquals("You use a " + itemRock.getName(), player.useItem(itemRock));
    }
}