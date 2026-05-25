import java.util.*;
import java.lang.*;
import java.io.*;

abstract class Character {
    String name, 
    postposition_1,    //을/를
    postposition_2,    //이/가
    postposition_3;    //은/는
    int maxHp, currentHp, damage, armour;
    
    Character(String name, int maxHp, int damage, int armour) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
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

    void melee(int damage) {
        this.currentHp -= damage;
    }
}

class Player extends Character {
    Player(String name) {
        super(name, 100, 10, 10);
    }
}

class Monster extends Character {
    Monster(String name, int maxHp, int damage, int armour) {
        super(name, maxHp, damage, armour);
    }
}


class BattleManager {
    //BattleManager() {}
    private int intInput = 0;
    
    void actionSelection(Character actor) {
        System.out.println("-------------------");
        System.out.println("뭘할까?\n1. 공격\n2. 방어\n3. 회피\n0. 종료\n");
    }
    
    void battleInfrom(Character actor1, Character actor2) {
        
        int realDamage = (actor1.damage * 100) / (100 + (int)Math.pow(actor2.armour, 2));
        System.out.println("-------------------");
        System.out.print(actor1.name + actor1.postposition_2);
        System.out.println(actor2.name + actor2.postposition_1 + "공격!");
        System.out.print(actor1.name + actor1.postposition_2);
        System.out.println("입힌 피해량: " + realDamage);
        System.out.print(actor2.name + "의 체력 변동치: " + actor2.currentHp + " -> ");
        actor2.melee(realDamage);
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
            actor2.melee(actor1.damage);
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
        
        Character adventurer = new Player("모험가");
        Character goblin = new Monster("고블린", 50, 10, 5);
        BattleManager BattleManager = new BattleManager();
        
        do {
            BattleManager.actionJunction(intInput, adventurer, goblin);
            BattleManager.actionSelection(adventurer);
            intInput = input.nextInt();
        } while (intInput != 0);

        
    }
}