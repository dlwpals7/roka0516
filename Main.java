import java.util.*;
import java.lang.*;
import java.io.*;

abstract class Item {
    String itemName;
    int price;
    
    Item(String itemName, int price) {
        this.itemName = itemName;
        this.price = price;
    }


    // setter

    
    // getter
    String getItemName() {
        return this.itemName;
    }
}

interface weaponary {
    void setItemDamage(int value);
    int getItemDamage();
}

interface armoury {
    void setItemProtect(int value);
    int getItemProtect();
}
    
class Weapon extends Item implements weaponary {
    int damage;

    Weapon(String name, int price, int damage) {
        super(name, price);
        this.damage = damage;
    }

    void setItemDamage(int value) {
        
    }
    
    int getItemDamage() {
        
    }
}

class ItemPool {
    private ItemPool() {}

    // name, gold, damage
    public static Item getLongsword() {
        return new Weapon("롱소드", 5, 20);
    }
}

abstract class Character {
    String name, 
    postposition_1,           // 을/를
    postposition_2,           // 이/가
    postposition_3;           // 은/는
    
    int maxHp, currentHp,     // 체력
    maxEnergy, currentEnergy,  // 활력
    damage,                   // 공격
    armour;                   // 방어
    
    Character(String name, int maxHp, int maxEnergy, int damage, int armour) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.maxEnergy = maxEnergy;
        this.currentEnergy = maxEnergy;
        this.damage = damage;
        this.armour = armour;

        boolean postposition = true;        //모음으로 끝나는 경우
        if (this.name.charAt(this.name.length()-1) >= '가' && this.name.charAt(this.name.length()-1) <= '힣') {  //자음으로 끝나는 경우
            if ((this.name.charAt(this.name.length()-1) - '가') % 28 != 0) {
                postposition = false;
            }
        }
        this.postposition_1 = postposition ? "를 " : "을 ";
        this.postposition_2 = postposition ? "가 " : "이 ";
        this.postposition_3 = postposition ? "는 " : "은 ";
    }

    boolean isAlive() {
        return this.currentHp != 0;
    }

    boolean isExceedMaxHp() {
        return this.currentHp > this.maxHp;
    }

    boolean isExceedMaxEnergy() {
        return this.currentEnergy > this.maxEnergy;
    }


    // setter
    void setMaxHpNeg(int damage) {
        this.maxHp -= damage;
        this.maxHp = this.maxHp > 1 ? this.maxHp : 1;
        if (isExceedMaxHp()) { setCurrentHpNeg(this.currentHp - this.maxHp); }
    }
    
    void setCurrentHpNeg(int damage) {
        this.currentHp -= damage;
        this.currentHp = this.currentHp > this.maxHp ? this.maxHp : (this.currentHp < 0 ? 0 : this.currentHp);
    }
    
    void setMaxEnergyNeg(int fatigue) {
        this.maxEnergy -= fatigue;
        this.maxEnergy = this.maxEnergy > 1 ? this.maxEnergy : 1;
        if (isExceedMaxEnergy()) { setCurrentEnergyNeg(this.currentEnergy - this.maxEnergy); }
    }
    
    void setCurrentEnergyNeg(int fatigue) {
        this.currentEnergy -= fatigue;
        this.currentEnergy = this.currentEnergy > this.maxEnergy ? this.maxEnergy : (this.currentEnergy < 0 ? 0 : this.currentEnergy);
    }

    
    // getter
    String getName() {
        return this.name;
    }

    String getPostposition(int index) {
        return switch (index) {
            case 1 -> this.postposition_1;
            case 2 -> this.postposition_2;
            case 3 -> this.postposition_3;
            default -> "Invalid Index";
        };
    }

    int getMaxHp() {
        return this.maxHp;
    }

    int getCurrentHp() {
        return this.currentHp;
    }

    int getMaxEnergy() {
        return this.maxEnergy;
    }

    int getCurrentEnergy() {
        return this.currentEnergy;
    }
}

class Player extends Character {
    int level, exp, gold;
    
    Player(String name, int maxHp, int maxEnergy, int damage, int armour, int level, int exp, int gold) {
        super(name, maxHp, maxEnergy, damage, armour);
        this.level = level;
        this.exp = exp;
        this.gold = gold;
    }

    void setExpPos(int value) {
        this.exp += value;
    }
    
    void setLevelPos(int value) {
        this.level += value;
    }

    void setGoldPos(int value) {
        this.gold += value;
    }

    int getLevel() {
        return this.level;
    }
    
    int getExp() {
        return this.exp;
    }
    
    int getGold() {
        return this.gold;
    }
}

class PlayerPool {
    private PlayerPool() {}

    // name, maxHp, maxEnergy, damage, armour, level, exp, gold
    public static Player getAdventurer() {
        return new Player("모험가", 100, 100, 20, 10, 0, 0, 0);
    }
}

class Monster extends Character {
    int rewardExp,         // 보상 경험치
    rewardGold;            // 보상 금화
    
    Monster(String name, int maxHp, int maxEnergy, int damage, int armour, int rewardExp, int rewardGold) {
        super(name, maxHp, maxEnergy, damage, armour);
        this.rewardExp = rewardExp;
        this.rewardGold = rewardGold;
    }

    int getRewardExp() {
        return this.rewardExp;
    }
    
    int getRewardGold() {
        return this.rewardGold;
    }
}

class MonsterPool {
    private MonsterPool() {}
    // name, maxHp, maxEnergy, damage, armour, rewardExp, rewardGold
    public static Monster getGoblin() {
        return new Monster("고블린", 50, 50, 10, 5, 1, 1);
    }
    public static Monster getOrc() {
        return new Monster("오크", 150, 75, 60, 30, 6, 3);
    }
}

class LevelManager {
    //LevelManager() {}
    int max_level = 6;
    // x-1 + x*x
    int[] expTable = { 1, 5, 11, 19, 29, 41 };

    boolean isMaxLevel(Player player) {
        return player.getLevel() >= this.max_level;
    }
    
    void gainExpInform(Player player, int value) {
        if (isMaxLevel(player)) {
            player.setExpPos((-1)*player.getExp());
            return;
        } 
        
        player.setExpPos(value);
        while (player.getExp() >= this.expTable[player.getLevel()]) {
            player.setExpPos((-1)*this.expTable[player.getLevel()]);
            player.setLevelPos(1);
            System.out.println("-------------------\n" + player.getName() + player.getPostposition(3) + "Lv." + player.getLevel() + "이 되었다!");
            
            if (isMaxLevel(player)) { 
                player.setExpPos((-1)*player.getExp());
                break;
            }
            
            System.out.println("잔여 경험치: " + player.getExp() + "xp");
            if (player.getExp() > this.expTable[player.getLevel()]) {
                System.out.println("레벨 업까지: " + (this.expTable[player.getLevel()] % player.getExp()) + "xp\n");
            }
             else {
                System.out.println("레벨 업까지: " + (this.expTable[player.getLevel()] - player.getExp()) + "xp\n");
            }
        }
    }
}

class GoldManager {
    //GoldManager() {}
    
    void gainGoldInform(Player player, int value) {
        player.setGoldPos(value);
        System.out.println("-------------------\n" + player.getName() + player.getPostposition(3) + value + "G를 얻었다!\n현재 골드: " + player.getGold() + "G\n");
    }
}

class BattleManager {
    //BattleManager() {}
    private int intInput = 0;
    
    void actionSelection(Character actor) {
        System.out.println("-------------------\n뭘할까?\n1. 공격\n2. 방어\n3. 회피\n0. 종료\n");
    }
    
    void battleInfrom(Character actor1, Character actor2) {
        
        int realDamage = (actor1.damage * 100) / (100 + (int)Math.pow(actor2.armour, 2));
        System.out.println("-------------------");
        System.out.print(actor1.name + actor1.postposition_2);
        System.out.println(actor2.name + actor2.postposition_1 + "공격!");
        System.out.print(actor1.name + actor1.postposition_2);
        System.out.println("입힌 피해량: " + realDamage);
        System.out.print(actor2.name + "의 체력 변동치: " + actor2.currentHp + " -> ");
        actor2.setCurrentHpNeg(realDamage);
        System.out.println(actor2.currentHp + "\n");
    }

    void dodgeInfrom(Character actor1, Character actor2) {
        
        if ((int)(Math.random() * 100) >= 50) {
            System.out.println("-------------------");
            System.out.print(actor1.name + actor1.postposition_2);
            System.out.println(actor2.name + actor2.postposition_1 + "공격!");
            System.out.println(actor2.name + actor2.postposition_3 + "피하려 했지만 실패했다!");
            System.out.print(actor1.name + actor1.postposition_2);
            System.out.println("입힌 피해량: " + actor1.damage);
            System.out.print(actor2.name + "의 체력 변동치: " + actor2.currentHp + " -> ");
            actor2.setCurrentHpNeg(actor1.damage);
            System.out.println(actor2.currentHp + "\n");
        } else {
            System.out.println("-------------------");
            System.out.print(actor1.name + actor1.postposition_2);
            System.out.println(actor2.name + actor2.postposition_1 + "공격!");
            System.out.println("하지만 " + actor2.name + actor2.postposition_2 + "간단히 피해버렸다!\n");
        }
    }

    void actionJunction(int intInput, Character actor1, Character actor2) {
        switch (intInput) {
            case 1 -> battleInfrom(actor1, actor2);
            case 2 -> battleInfrom(actor2, actor1);
            case 3 -> dodgeInfrom(actor2, actor1);
            default -> System.out.println("전투 개시!");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int intInput = 0;
        
        Player adventurer_1 = PlayerPool.getAdventurer();
        Monster goblin_1 = MonsterPool.getGoblin();
        Monster orc_1 = MonsterPool.getOrc();
        BattleManager battleManager = new BattleManager();
        LevelManager levelManager = new LevelManager();
        GoldManager goldManager = new GoldManager();
        
        while (true) {
            battleManager.actionSelection(adventurer_1);
            intInput = input.nextInt();
            if (intInput == 0) { break; }
            battleManager.actionJunction(intInput, adventurer_1, goblin_1);
            
            if (!goblin_1.isAlive()) {
                System.out.println(goblin_1.getName() + goblin_1.getPostposition(3) + "쓰러졌다!\n");
                levelManager.gainExpInform(adventurer_1, goblin_1.getRewardExp());
                goldManager.gainGoldInform(adventurer_1, goblin_1.getRewardGold());
                break;
            }
        } 

        while (true) {
            battleManager.actionSelection(adventurer_1);
            intInput = input.nextInt();
            if (intInput == 0) { break; }
            battleManager.actionJunction(intInput, adventurer_1, orc_1);
            
            if (!orc_1.isAlive()) {
                System.out.println(orc_1.getName() + orc_1.getPostposition(3) + "쓰러졌다!\n");
                levelManager.gainExpInform(adventurer_1, orc_1.getRewardExp());
                goldManager.gainGoldInform(adventurer_1, orc_1.getRewardGold());
                break;
            }
        }
        System.out.println("-------------------\n종료");
    }
}
