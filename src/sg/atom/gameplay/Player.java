/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author CuongNguyen
 */
public class Player {

    int id;
    String uid;
    String name;
    int point;
    int range;
    int score;
    int gold;
    int coin;
    int money;
    int status;
    GameCharacter character;
    ArrayList<GameCharacter> team;
    
    //Infomations & Social------------------------------------------------------
    String realName;
    int sex;
    String fbAccount;
    String fbPassword;
    String fbUID;
    String gpAccount;
    String gpPassword;
    String gpUID;
    String email;
    String email2;
    int age;
    Date birthDate;
    int country;
    String address;
    String phoneNumber;
    

    public Player(String name,String uid){
        this.name = name;
        this.uid = uid;
    }
    public Player(String name) {
        this.name = name;
        this.team = new ArrayList<GameCharacter>();
        this.uid = "";
    }

    public GameCharacter getMainCharacter() {
        return character;
    }

    public void setCharacter(GameCharacter character) {
        this.character = character;
        character.isNpc = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void buySkill(Skill skill) {
        money -= skill.cost;
    }

    public void buyItem(Item item) {
        money -= item.cost;
    }
    // Collections support -----------------------------------------------------

    // GETTER & SETTER ---------------------------------------------------------
    public ArrayList<GameCharacter> getTeam() {
        return team;
    }

    public List<GameCharacter> getCombatCharacters() {
        return team;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
