package star.odyssey.combat;

import star.odyssey.character.Entity;
import star.odyssey.character.NPC;
import star.odyssey.character.Player;
import star.odyssey.inventory.Weapon;
import star.odyssey.ui.ConsoleDisplayUtils;

import java.util.Random;

import static star.odyssey.ui.ConsoleDisplayUtils.makeGreen;
import static star.odyssey.ui.ConsoleDisplayUtils.makeRed;

public class CombatEngine {

    // INSTANCE VARIABLES
    private final Player player;
    private final NPC npc;
    private static final Random random = new Random();
    private static final double CRITICAL_HIT_CHANCE = 0.15;
    private static final double DODGE_CHANCE = 0.1;
    private static final double DAMAGE_VARIATION_FACTOR = 0.2;
    private static final int CRITICAL_HIT_MULTIPLIER = 2;

    // CONSTRUCTORS
    public CombatEngine(Player player, NPC npc) {
        this.player = player;
        this.npc = npc;
    }

    // METHODS
    public String startCombat() {
        StringBuilder combatLog = new StringBuilder();
        //Start by showing initial stats
        combatLog.append(getNPCStats()).append("\n");
        //Condition to determine whether to print "Player defeated"
        boolean playerDefeated = false;

        while (player.isAlive() && npc.isAlive()) {
            //Player attacks the NPC, and if NPC dies force break out of the loop
            combatLog.append(performAttack(player, npc)).append("\n");
            if (!npc.isAlive()) break;

            //NPC attacks the Player and if player dies break out of the loop
            combatLog.append(performAttack(npc, player)).append("\n");
            if (!player.isAlive()) {
                playerDefeated = true;
                break;
            }
        }

        if (playerDefeated) {
            return combatLog.append("\nPlayer defeated. Returning to main menu...").toString();
        }

        return combatLog.toString();
    }

    //Get all necessary stats from NPC to display
    private String getNPCStats() {
        StringBuilder stats = new StringBuilder();
        Weapon equippedWeapon = npc.getEquippedWeapon();

        stats.append(npc.getName()).append(" Combat Stats:\n");
        stats.append("Health: ").append(npc.getHealth());
        stats.append(", Strength: ").append(npc.getStrength());
        stats.append(", Defense: ").append(npc.getDefense());
        stats.append(", Equipped Weapon: ").append(equippedWeapon.getName() != null ? equippedWeapon.getName() : "None").append("\n");

        if (equippedWeapon.getName() != null) {
            stats.append("Weapon Damage: ").append(equippedWeapon.getDamage());
            stats.append(", Weapon Range: ").append(equippedWeapon.getRange());
            stats.append(", Weapon Durability: ").append(equippedWeapon.getDurability()).append("\n");
        }
        return stats.toString();
    }

    private String performAttack(Entity attacker, Entity defender) {
        //If attack was randomly dodged, exit and send String
        if (dodgeAttack()) {
            return defender.getName() + " dodged the attack by " + attacker.getName() + ".";
        }

        int baseDamage = calculateDamage(attacker, defender);
        baseDamage = applyRandomVariation(baseDamage); //Randomize damage a bit

        //Determine randomly if hit was critical
        if (isCriticalHit()) {
            baseDamage *= CRITICAL_HIT_MULTIPLIER;
            //Critical hit string
            return attacker.getName() + " landed a critical hit on " + defender.getName() + " for " + baseDamage + " damage.\n" + takeDamage(defender, baseDamage);
        }
        //Regular hit string
        return attacker.getName() + " attacked " + defender.getName() + " for " + baseDamage + " damage.\n" + takeDamage(defender, baseDamage);
    }

    //strength + weapon - enemy defense, or 1 min
    private int calculateDamage(Entity attacker, Entity defender) {
        Weapon weapon = attacker.getEquippedWeapon();
        int weaponDamage = weapon != null ? weapon.getDamage() : 0;
        int totalAttackValue = attacker.getStrength() + weaponDamage;
        int damage = totalAttackValue - defender.getDefense();

        int minimumDamage = 1;
        return Math.max(damage, minimumDamage);
    }

    private String takeDamage(Entity entity, int damage) {
        //Deal the damage
        entity.setHealth(entity.getHealth() - damage);

        //If the one taking damage is now under 0
        if (entity.getHealth() <= 0) {
            entity.setHealth(0); //Set their health to 0, not neg
            entity.setAlive(false); //Make then unalive
            if (entity instanceof NPC) { //If it's not the player
                ((NPC) entity).dropItems(); //Drop their items
                ((NPC) entity).removeFromLocation(); //Remove NPC from location
            }
            return entity.getName() + " has been defeated."; //Defeated String
        }
        return entity.getName() + " took " + damage + " damage."; //Damage String
    }

    private int applyRandomVariation(int baseDamage) {
        double variation = DAMAGE_VARIATION_FACTOR * baseDamage * (random.nextDouble() - 0.5);
        return Math.max((int) (baseDamage + variation), 0);
    }

    private boolean isCriticalHit() {
        return random.nextDouble() < CRITICAL_HIT_CHANCE;
    }

    private boolean dodgeAttack() {
        return random.nextDouble() < DODGE_CHANCE;
    }
}
