import java.util.*;

public class Member{

private int id;
private String givenName;
private String familyName;
private String email;
private int gender;
private String birth;
private String memberSince;
private int active;

ArrayList<Member> list = new ArrayList<Member>();

public Member(){

}

public Member(int id,String givenName,String familyName,String email,int gender,String birth,
  String memberSince,int active){
  this.id=id;
  this.givenName=givenName;
  this.familyName=familyName;
  this.email=email;
  this.gender=gender;
  this.birth=birth;
  this.memberSince=memberSince;
  this.active=active;
}

public ArrayList<Member> getList(){
  return list;
}
public String getGivenName(){
  return givenName;
}
public void setGivenName(String s){
  this.givenName=s;
}
public String getFamilyName(){
  return familyName;
}
public void setFamilyName(String s){
  this.familyName=s;
}
public String getEmail(){
return email;
}
public String birth(){
  return birth;
}
public String memberSince(){
  return memberSince;
}
/*public String setToString(){

}*/

public String toString(){
  
  return String.format("%-4d %-10s %-11s %-10s %-2d %-10s %-10s %-2d",id,givenName,familyName,email,gender,
    birth,memberSince,active); 
}
}
