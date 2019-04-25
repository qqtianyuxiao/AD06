package net.hunau.mysqlitedemo;

public class User {
    public int ID = -1;
    public String Name;
    public String pwd;
    public String sexy;
    public boolean isused;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSexy() {
        return sexy;
    }

    public void setSexy(String sexy) {
        this.sexy = sexy;
    }

    public boolean isIsused() {
        return isused;
    }

    public void setIsused(boolean isused) {
        this.isused = isused;
    }

    @Override
    public String toString(){
        String result = "";
        result += "ID：" + this.ID + "，";
        result += "用户名：" + this.Name + "，";
        result += "密码：" + this.pwd + "， ";
        result += "性别：" + this.sexy + "，";
        if(this.isused){
            result += "是否有效：是";
        }else{
            result += "是否有效：否";
        }
        return result;
    }
}
